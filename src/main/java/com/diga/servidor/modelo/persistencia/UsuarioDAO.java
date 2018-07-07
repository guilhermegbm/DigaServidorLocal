/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.Usuario;
import com.diga.servidor.utils.ConnectionFactory;
import com.diga.servidor.utils.DBConnection;
import java.sql.Blob;
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
public class UsuarioDAO {
    
    public static boolean autenticaUsuario(String nomeUsuario, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select usuario.usuCodigo from usuario where usuNomeUsuario = ? and usuSenha = ?");
            
            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            boolean auth = rs.first();
            
            return auth;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
    }
    
    public static boolean nomeUsuarioExiste(String nomeUsuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select usuario.usuCodigo from usuario where usuNomeUsuario = ?");
            
            stmt.setString(1, nomeUsuario);
            
            rs = stmt.executeQuery();
            
            boolean existe = rs.first();
            
            return existe;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
    }
    
    public static String insereUsuario(Usuario u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("insert into usuario (usuNome, usuNomeUsuario, usuSenha, usuLatitudeResidencia, usuLongitudeResidencia, usuEnderecoCompleto, usuIsInBlacklist, usuNumStrikes, usuFoto, usu_tusCodigo) values (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getNomeUsuario());
            stmt.setString(3, u.getSenha());
            stmt.setDouble(4, u.getLatitudeResidencia());
            stmt.setDouble(5, u.getLongitudeResidencia());
            stmt.setString(6, u.getEnderecoCompleto());
            stmt.setBoolean(7, u.isIsInBlacklist());
            stmt.setInt(8, u.getNumStrikes());
            stmt.setString(9, u.getFoto());
            stmt.setInt(10, u.getTipoUsuario());
            
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
    
    public static List<Usuario> listarUsuarios() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select * from usuario");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario u = new Usuario();
                
                u.setCodigo(rs.getInt(1));
                u.setNome(rs.getString(2));
                u.setNomeUsuario(rs.getString(3));
                u.setSenha(rs.getString(4));
                u.setLatitudeResidencia(rs.getDouble(5));
                u.setLongitudeResidencia(rs.getDouble(6));
                u.setEnderecoCompleto(rs.getString(7));
                u.setIsInBlacklist(rs.getBoolean(8));
                u.setNumStrikes(rs.getInt(9));
                u.setFoto(rs.getString(10));
                u.setTipoUsuario(rs.getInt(11));
                
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return usuarios;
    }
    
    public static Usuario listarUsuarioPorNomeUsuarioESenha(String nomeUsuario, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Usuario u = new Usuario();
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("select * from usuario where usuNomeUsuario = ? and usuSenha = ?");
            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                u.setCodigo(rs.getInt(1));
                u.setNome(rs.getString(2));
                u.setNomeUsuario(rs.getString(3));
                u.setSenha(rs.getString(4));
                u.setLatitudeResidencia(rs.getDouble(5));
                u.setLongitudeResidencia(rs.getDouble(6));
                u.setEnderecoCompleto(rs.getString(7));
                u.setIsInBlacklist(rs.getBoolean(8));
                u.setNumStrikes(rs.getInt(9));
                u.setFoto(rs.getString(10));
                u.setTipoUsuario(rs.getInt(11));
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return u;
    }
}
