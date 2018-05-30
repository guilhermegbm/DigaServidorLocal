/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.teste.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.diga.servidor.modelo.beans.Usuario;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "Teste2", urlPatterns = {"/api_testes/Teste2"})
public class Teste2 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        
        out.println("Teste2");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario u = new Gson().fromJson(request.getParameter("usuario"), Usuario.class);
        //Usuario u = (Usuario) request.getAttribute("usuario");
        
        response.setContentType("application/text");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        if (u != null) {
            System.out.println("Usuário: " + u);
            out.print("1");
        } else {
            System.out.println("É nulo");
            out.print("2");
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
