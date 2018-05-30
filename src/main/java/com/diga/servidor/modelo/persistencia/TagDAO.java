/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

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
public class TagDAO {

    public static List<Tag> listarTagsPorOcorrencia(int codOcorrencia) {
        List<Tag> t = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select tag.tagCodigo, tag.tagNome, tag.tagImportancia "
                    + "from ocorrencia_possui_tags join tag "
                    + "where opt_tagCodigo = tagCodigo and "
                    + "opt_ocoCodigo = ?");
            stmt.setInt(1, codOcorrencia);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tag tag = new Tag();

                tag.setCodigo(rs.getInt(1));
                tag.setNome(rs.getString(2));
                tag.setImportancia(rs.getInt(3));
                t.add(tag);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        }
        return t;
    }
}
