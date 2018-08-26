/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.teste.servlets;

import com.diga.servidor.controle.*;
import com.diga.servidor.modelo.beans.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "MostraTUDO", urlPatterns = {"/api_testes/MostraTUDO"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = "")
    , @WebInitParam(name = "senha", value = "")})
public class MostraTUDO extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //if (UsuarioDAO.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"))) {
            response.setHeader("auth", "1");

            List<Object> valores = new ArrayList<>();

            List<Categoria> categorias = ControleCategoria.listarCategorias();
            List<Ocorrencia> ocorrencias = ControleOcorrencia.pesquisaOcorrencia("", 1);
            List<Situacao> situacoes = ControleSituacao.listarSituacoes();
            List<Tag> tags = ControleTag.listarTags();
            List<Usuario> usuarios = ControleUsuario.listarUsuarios();
            List<UsuarioCurteOcorrencia> uco = ControleUsuarioCurteOcorrencia.listarUcos();
            List<UsuarioReportaOcorrencia> uro = ControleUsuarioReportaOcorrencia.listarUros();

            valores.add(categorias);
            valores.add(ocorrencias);
            valores.add(situacoes);
            valores.add(tags);
            valores.add(usuarios);
            valores.add(uco);
            valores.add(uro);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(valores));
        //} else {
        //    response.setHeader("auth", "0");
        //}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
