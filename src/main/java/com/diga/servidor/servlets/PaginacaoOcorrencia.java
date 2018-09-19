/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleOcorrencia;
import com.diga.servidor.modelo.beans.Ocorrencia;
import com.diga.servidor.modelo.beans.Usuario;
import com.diga.servidor.modelo.persistencia.UsuarioDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "PaginacaoOcorrencia", urlPatterns = {"/diga_api/PaginacaoOcorrencia"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = "")
    , @WebInitParam(name = "senha", value = "")
    , @WebInitParam(name = "pagina", value = "")
    , @WebInitParam(name = "rowsPorPagina", value = "")})
public class PaginacaoOcorrencia extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (UsuarioDAO.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            Usuario u = UsuarioDAO.listarUsuarioPorNomeUsuarioESenha(request.getParameter("nomeUsuario"), request.getParameter("senha"));
            response.setHeader("auth", "1");

            List<Ocorrencia> ocorrencias = ControleOcorrencia.listarOcorrenciasPorPaginacao(u.getCodigo(),
                    Integer.parseInt(request.getParameter("pagina")), 
                    Integer.parseInt(request.getParameter("rowsPorPagina")));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(ocorrencias));
        } else {
            response.setHeader("auth", "0");

            List<Ocorrencia> o = null;

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(o));
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
