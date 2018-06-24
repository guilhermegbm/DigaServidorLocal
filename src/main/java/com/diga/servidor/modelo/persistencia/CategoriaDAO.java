/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.Categoria;
import com.diga.servidor.modelo.beans.Tag;
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
public class CategoriaDAO {

    public static List<Categoria> listarTodas() {
        List<Categoria> cat = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from categoria");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();

                c.setCodigo(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setCor(rs.getInt(3));
                cat.add(c);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        }
        return cat;
    }
}
