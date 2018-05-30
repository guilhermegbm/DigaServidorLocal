/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.persistencia.UsuarioDAO;

/**
 *
 * @author Guilherme
 */
public class ControleUsuario {
    public static boolean autenticaUsuario (String nomeUsuario, String senha) {
        return UsuarioDAO.autenticaUsuario(nomeUsuario, senha);
    }
}
