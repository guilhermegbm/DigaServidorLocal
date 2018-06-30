/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleCategoria;
import com.diga.servidor.controle.ControleSituacao;
import com.diga.servidor.controle.ControleTag;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "PreenchimentoInicialBD", urlPatterns = {"/diga_api/PreenchimentoInicialBD"})
public class PreenchimentoInicialBD extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Object> valores = new ArrayList<>();

        valores.add(ControleTag.listarTags());
        valores.add(ControleSituacao.listarSituacoes());
        valores.add(ControleCategoria.listarCategorias());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(new Gson().toJson(valores));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
