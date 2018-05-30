/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.teste.modelo.beans;

import com.diga.servidor.modelo.beans.Usuario;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class UsuarioHolder {

    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
