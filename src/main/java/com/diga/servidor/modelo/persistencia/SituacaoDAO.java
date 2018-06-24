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
        List<Situacao> sit = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from situacao");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Situacao s = new Situacao();
                
                s.setCodigo(rs.getInt(1));
                s.setNome(rs.getString(2));
                
                sit.add(s);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        }
        return sit;
    }
}
