/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleOcorrencia;
import com.diga.servidor.controle.ControleUsuario;
import com.google.gson.Gson;
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
@WebServlet(name = "PegaDadosMapaSecundario", urlPatterns = {"/diga_api/PegaDadosMapaSecundario"},
        initParams = {
            @WebInitParam(name = "nomeUsuario", value = "")
            , @WebInitParam(name = "senha", value = "")
            , @WebInitParam(name = "codOcorrencia", value = "")
            , @WebInitParam(name = "codUsuario", value = "")})
public class PegaDadosMapaSecundario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ControleUsuario.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            response.setHeader("auth", "1");
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(ControleOcorrencia.pegaDadosMapaSecundario(Integer.parseInt(request.getParameter("codOcorrencia")), Integer.parseInt(request.getParameter("codUsuario")))));
        } else {
            response.setHeader("auth", "0");
        }
    }
}
