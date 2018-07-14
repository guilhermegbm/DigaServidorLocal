/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.servlets;

import com.diga.servidor.controle.ControleUsuario;
import com.diga.servidor.modelo.beans.Usuario;
import com.diga.servidor.modelo.persistencia.UsuarioDAO;
import com.google.gson.Gson;
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
@WebServlet(name = "FazSignin", urlPatterns = {"/diga_api/FazSignin"}, initParams = {
    @WebInitParam(name = "usuario", value = "")})
public class FazSignin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario u = new Gson().fromJson(request.getParameter("usuario"), Usuario.class);

        if (u != null) {
            String fotoUsuario = request.getParameter("fotoUsuario");

            u.setFoto(fotoUsuario);
            
            if (!UsuarioDAO.nomeUsuarioExiste(u.getNomeUsuario())) {
                response.setHeader("nUsuExiste", "0");

                response.setHeader("sucesso", ControleUsuario.insereUsuario(u));
            } else {
                response.setHeader("nUsuExiste", "1");
            }
        } else {
            response.setHeader("sucesso", "0");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
