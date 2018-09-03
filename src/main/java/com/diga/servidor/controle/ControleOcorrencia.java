/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Ocorrencia;
import com.diga.servidor.modelo.beans.UsuarioCurteOcorrencia;
import com.diga.servidor.modelo.beans.UsuarioReportaOcorrencia;
import com.diga.servidor.modelo.persistencia.OcorrenciaDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleOcorrencia {

    public static String persistirOcorrencia(Ocorrencia o) {
        return OcorrenciaDAO.persistirOcorrencia(o);
    }

    public static String persistirOcorrencias(List<Ocorrencia> o) {
        boolean s = true;

        for (Ocorrencia ocorrencia : o) {
            String sucesso = OcorrenciaDAO.persistirOcorrencia(ocorrencia);

            if (sucesso.equals("0")) {
                s = false;
            }
        }

        return s == true ? "1" : "0";
    }

    public static List<Ocorrencia> pesquisaOcorrencia(String query, int usuCodigo) {
        return OcorrenciaDAO.listarOcorrencias(query, usuCodigo);
    }

    public static String curteOcorrencia(UsuarioCurteOcorrencia uso) {
        return OcorrenciaDAO.curteOcorrencia(uso);
    }

    public static String descurteOcorrencia(int usuario, int ocorrencia) {
        return OcorrenciaDAO.descurteOcorrencia(usuario, ocorrencia);
    }

    public static String reportaOcorrencia(UsuarioReportaOcorrencia uro) {
        return OcorrenciaDAO.reportaOcorrencia(uro);
    }

    public static String desreportaOcorrencia(int usuario, int ocorrencia) {
        return OcorrenciaDAO.desreportaOcorrencia(usuario, ocorrencia);
    }

    public static String deletarOcorrencia(int codigoOcorrencia) {
        return OcorrenciaDAO.deletarOcorrencia(codigoOcorrencia);
    }
}
