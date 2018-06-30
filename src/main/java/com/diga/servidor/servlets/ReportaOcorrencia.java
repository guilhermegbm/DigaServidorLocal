/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleOcorrencia;
import com.diga.servidor.controle.ControleUsuario;
import com.diga.servidor.modelo.beans.UsuarioCurteOcorrencia;
import com.diga.servidor.modelo.beans.UsuarioReportaOcorrencia;
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
@WebServlet(name = "ReportaOcorrencia", urlPatterns = {"/diga_api/ReportaOcorrencia"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = "")
    , @WebInitParam(name = "senha", value = "")
    , @WebInitParam(name = "usuarioReportaOcorrencia", value = "")})
public class ReportaOcorrencia extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ControleUsuario.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            response.setHeader("auth", "1");

            UsuarioReportaOcorrencia uro = new Gson().fromJson(request.getParameter("usuarioReportaOcorrencia"), UsuarioReportaOcorrencia.class);
            
            response.setHeader("sucesso", ControleOcorrencia.reportaOcorrencia(uro));
        } else {
            response.setHeader("auth", "0");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
