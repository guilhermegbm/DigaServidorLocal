/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.Situacao;
import com.diga.servidor.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class SituacaoDAO {

    public static List<Situacao> listarTodas() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Situacao> sit = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select * from situacao");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Situacao s = new Situacao();
                
                s.setCodigo(rs.getInt(1));
                s.setNome(rs.getString(2));
                
                sit.add(s);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return sit;
    }
}
