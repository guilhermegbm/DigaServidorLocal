/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.TipoUsuario;
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
public class TipoUsuarioDAO {

    public static List<TipoUsuario> listarTipos() {
        List<TipoUsuario> t = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from tipousuario");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoUsuario tu = new TipoUsuario();
                tu.setCodigo(rs.getInt(1));
                tu.setNome(rs.getString(2));
                
                t.add(tu);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        }
        return t;
    }
}
