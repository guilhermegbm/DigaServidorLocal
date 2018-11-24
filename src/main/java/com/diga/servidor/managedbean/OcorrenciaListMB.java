/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.managedbean;

import com.diga.servidor.controle.ControleOcorrencia;
import com.diga.servidor.modelo.beans.Ocorrencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Guilherme
 */
@ManagedBean(name = "ocorrenciaListMB")
@ViewScoped
public class OcorrenciaListMB implements Serializable {
    
    private List<Ocorrencia> ocorrencias = new ArrayList<>();

    public OcorrenciaListMB() {
        this.ocorrencias = ControleOcorrencia.pesquisaOcorrencia("select * from ocorrencia", 1);
    }

    public List<Ocorrencia> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
    
    
}
