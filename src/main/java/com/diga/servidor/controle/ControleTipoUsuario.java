/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.TipoUsuario;
import com.diga.servidor.modelo.persistencia.TipoUsuarioDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleTipoUsuario {
    public static List<TipoUsuario> listarTipos () {
        return TipoUsuarioDAO.listarTipos();
    }
}
