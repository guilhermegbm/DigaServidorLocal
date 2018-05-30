/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.diga.servidor.modelo.beans.Ocorrencia;
import com.diga.servidor.modelo.persistencia.OcorrenciaDAO;
import com.diga.servidor.modelo.persistencia.UsuarioDAO;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "PesquisaOcorrencia", urlPatterns = {"/diga_api/PesquisaOcorrencia"}, initParams = {
    @WebInitParam(name = "query", value = "")
    , @WebInitParam(name = "nomeUsuario", value = "")
    , @WebInitParam(name = "senha", value = "")})
public class PesquisaOcorrencia extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (UsuarioDAO.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            response.setHeader("auth", "1");
            
            String query = request.getParameter("query");
            System.out.println("Query: (POST)" + query);

            List<Ocorrencia> ocorrencias = OcorrenciaDAO.listarOcorrencias(query);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(ocorrencias));
        } else {
            response.setHeader("auth", "0");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
