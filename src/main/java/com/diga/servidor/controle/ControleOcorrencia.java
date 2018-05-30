/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Ocorrencia;
import com.diga.servidor.modelo.persistencia.OcorrenciaDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleOcorrencia {
    public static String persistirOcorrencia (Ocorrencia o) {
        return OcorrenciaDAO.persistirOcorrencia(o);
    }
    
    public static List<Ocorrencia> pesquisaOcorrencia (String query) {
        return OcorrenciaDAO.listarOcorrencias(query);
    }
}
