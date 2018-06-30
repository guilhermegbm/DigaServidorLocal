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
    public static List<UsuarioCurteOcorrencia> listarUcos () {
        List<UsuarioCurteOcorrencia> ucos = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from usuario_curte_ocorrencia");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioCurteOcorrencia uco = new UsuarioCurteOcorrencia();
                uco.setUsuario(rs.getInt(1));
                uco.setOcorrencia(rs.getInt(2));
                uco.setLatitude(rs.getDouble(3));
                uco.setLongitude(rs.getDouble(4));
                
                ucos.add(uco);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        }
        return ucos;
    }
}
