/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

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
}
