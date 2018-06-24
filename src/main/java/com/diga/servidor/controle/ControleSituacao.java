/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Situacao;
import com.diga.servidor.modelo.persistencia.SituacaoDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleSituacao {
    public static List<Situacao> listarTodas () {
        return SituacaoDAO.listarTodas();
    }
}
