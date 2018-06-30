/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Usuario;
import com.diga.servidor.modelo.persistencia.UsuarioDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleUsuario {
    
    public static List<Usuario> listarUsuarios () {
        return UsuarioDAO.listarUsuarios();
    }
    
    public static boolean autenticaUsuario (String nomeUsuario, String senha) {
        return UsuarioDAO.autenticaUsuario(nomeUsuario, senha);
    }
    
    public static boolean nomeUsuarioExiste (String nomeUsuario) {
        return UsuarioDAO.nomeUsuarioExiste(nomeUsuario);
    }
    
    public static String insereUsuario (Usuario u){
        return UsuarioDAO.insereUsuario(u);
    }
}
