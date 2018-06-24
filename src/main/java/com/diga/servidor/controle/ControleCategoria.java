/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Categoria;
import com.diga.servidor.modelo.persistencia.CategoriaDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleCategoria {
    public static List<Categoria> listarTodas () {
        return CategoriaDAO.listarTodas();
    }
}
