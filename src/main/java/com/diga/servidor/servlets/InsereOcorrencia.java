/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleOcorrencia;
import com.diga.servidor.controle.ControleUsuario;
import com.diga.servidor.modelo.beans.Ocorrencia;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "InsereOcorrencia", urlPatterns = {"/diga_api/InsereOcorrencia"},
        initParams = {
            @WebInitParam(name = "ocorrencia", value = "")
            , @WebInitParam(name = "nomeUsuario", value = "")
            , @WebInitParam(name = "senha", value = "")})
public class InsereOcorrencia extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ControleUsuario.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            response.setHeader("auth", "1");

            Ocorrencia o = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(request.getParameter("ocorrencia"), Ocorrencia.class);
    
            String fotoOcorrencia = request.getParameter("fotoOcorrencia");
            
            o.setFotoOcorrencia(fotoOcorrencia);

            response.setHeader("sucesso", ControleOcorrencia.persistirOcorrencia(o));
        } else {
            response.setHeader("auth", "0");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
