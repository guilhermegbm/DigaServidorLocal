/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.UsuarioCurteOcorrencia;
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
public class UsuarioCurteOcorrenciaDAO {

    public static List<UsuarioCurteOcorrencia> listarUcos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<UsuarioCurteOcorrencia> ucos = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select * from usuario_curte_ocorrencia");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioCurteOcorrencia uco = new UsuarioCurteOcorrencia();
                uco.setUsuario(rs.getInt(1));
                uco.setOcorrencia(rs.getInt(2));
                uco.setLatitude(rs.getDouble(3));
                uco.setLongitude(rs.getDouble(4));

                ucos.add(uco);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return ucos;
    }
    
    public static String deletaUsuarioCurteOcorrenciaPorCodOcorrencia (int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("delete from usuario_curte_ocorrencia where uso_ocoCodigo = ?;");

            stmt.setInt(1, codigoOcorrencia);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }
}
