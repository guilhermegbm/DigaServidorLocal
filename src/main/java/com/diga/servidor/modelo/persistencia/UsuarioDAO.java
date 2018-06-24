/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.modelo.beans.Usuario;
import com.diga.servidor.utils.ConnectionFactory;
import com.diga.servidor.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Guilherme
 */
public class UsuarioDAO {

    public static boolean autenticaUsuario(String nomeUsuario, String senha) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select usuario.usuCodigo from usuario where usuNomeUsuario = ? and usuSenha = ?");
            
            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            return rs.first();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean nomeUsuarioExiste(String nomeUsuario) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select usuario.usuCodigo from usuario where usuNomeUsuario = ?");
            
            stmt.setString(1, nomeUsuario);

            ResultSet rs = stmt.executeQuery();

            return rs.first();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return false;
        }
    }

    public static String insereUsuario(Usuario u) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into usuario (usuNome, usuNomeUsuario, usuSenha, usuLatitudeResidencia, usuLongitudeResidencia, usuEnderecoCompleto, usuIsInBlacklist, usuNumStrikes, usu_tusCodigo) values (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getNomeUsuario());
            stmt.setString(3, u.getSenha());
            stmt.setDouble(4, u.getLatitudeResidencia());
            stmt.setDouble(5, u.getLongitudeResidencia());
            stmt.setString(6, u.getEnderecoCompleto());
            stmt.setBoolean(7, u.isIsInBlacklist());
            stmt.setInt(8, u.getNumStrikes());
            stmt.setInt(9, u.getTipoUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }
}
