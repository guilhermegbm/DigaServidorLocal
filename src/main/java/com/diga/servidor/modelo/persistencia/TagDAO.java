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

    public static List<Tag> listarTags() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Tag> t = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select tag.tagCodigo, tag.tagNome, tag.tagImportancia "
                    + "from tag");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Tag tag = new Tag();

                tag.setCodigo(rs.getInt(1));
                tag.setNome(rs.getString(2));
                tag.setImportancia(rs.getInt(3));
                t.add(tag);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return t;
    }

    public static List<Tag> listarTagsPorOcorrencia(int codOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Tag> t = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select tag.tagCodigo, tag.tagNome, tag.tagImportancia "
                    + "from ocorrencia_possui_tags join tag "
                    + "where opt_tagCodigo = tagCodigo and "
                    + "opt_ocoCodigo = ?");
            stmt.setInt(1, codOcorrencia);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Tag tag = new Tag();

                tag.setCodigo(rs.getInt(1));
                tag.setNome(rs.getString(2));
                tag.setImportancia(rs.getInt(3));
                t.add(tag);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar bd: " + ex.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return t;
    }

    public static String insereOcorrenciaPossuiTags(List<Tag> tags) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("insert into ocorrencia_possui_tags (opt_ocoCodigo, opt_tagCodigo) values ((select max(ocoCodigo) from ocorrencia),?)");

            for (Tag tag : tags) {
                stmt.setInt(1, tag.getCodigo());
                
                stmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }
    
    public static String deletaOcorrenciaPossuiTagsPorCodOcorrencia (int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("delete from ocorrencia_possui_tags where opt_ocoCodigo = ?;");

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
