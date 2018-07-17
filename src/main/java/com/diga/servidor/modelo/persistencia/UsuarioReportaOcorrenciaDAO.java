/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.UsuarioReportaOcorrencia;
import com.diga.servidor.utils.ConnectionFactory;
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
public class UsuarioReportaOcorrenciaDAO {

    public static List<UsuarioReportaOcorrencia> listarUros() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<UsuarioReportaOcorrencia> uros = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select * from usuario_reporta_ocorrencia");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioReportaOcorrencia uro = new UsuarioReportaOcorrencia();
                uro.setUsuario(rs.getInt(1));
                uro.setOcorrencia(rs.getInt(2));
                uro.setLatitude(rs.getDouble(3));
                uro.setLongitude(rs.getDouble(4));

                uros.add(uro);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return uros;
    }
    
    public static String deletaUsuarioReportaOcorrenciaPorCodOcorrencia (int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("delete from usuario_reporta_ocorrencia where uro_ocoCodigo = ?;");

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
