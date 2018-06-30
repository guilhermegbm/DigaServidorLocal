/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.UsuarioCurteOcorrencia;
import com.diga.servidor.modelo.beans.UsuarioReportaOcorrencia;
import com.diga.servidor.modelo.persistencia.UsuarioReportaOcorrenciaDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleUsuarioReportaOcorrencia {

    public static List<UsuarioReportaOcorrencia> listarUros() {
        return UsuarioReportaOcorrenciaDAO.listarUros();
    }
}
