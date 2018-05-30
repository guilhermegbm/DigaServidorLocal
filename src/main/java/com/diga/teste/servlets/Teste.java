/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.teste.servlets;

import com.diga.servidor.modelo.beans.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.diga.teste.modelo.beans.Mensagem;
import com.diga.teste.modelo.beans.UsuarioHolder;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "Teste", urlPatterns = {"/api_testes/Teste"}, initParams = {
    @WebInitParam(name = "codigo", value = "0")
    ,
    @WebInitParam(name = "usuario", value = "n/a")
    ,
    @WebInitParam(name = "senha", value = "n/a")})
public class Teste extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("codigo");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        
        Usuario u = new Usuario();
        try {
            u.setCodigo(Integer.parseInt(codigo));
        } catch (NumberFormatException e) {
            u.setCodigo(1);
        }
        u.setNome(usuario);
        u.setNomeUsuario(usuario);
        u.setSenha(senha);
        u.setIsInBlacklist(false);
        u.setEnderecoCompleto("Rua x");
        u.setLatitudeResidencia(23.456);
        u.setLongitudeResidencia(22.343);
        u.setNumStrikes(0);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        
        out.println(new Gson().toJson(u));
        
        /*System.out.println("PASSOU GET");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        List<Usuario> usuarios = new ArrayList();

        Usuario u1 = new Usuario();
        u1.setCodigo(1);
        u1.setNomeUsuario("Teste1");
        u1.setSenha("*teste1*");

        Mensagem m1 = new Mensagem();
        m1.setCodigo(1);
        m1.setData(new Date());
        m1.setEnviado(true);
        m1.setTexto("Texto 1");
        m1.setUsuario(1);
        u1.getMensagens().add(m1);

        Mensagem m2 = new Mensagem();
        m2.setCodigo(2);
        m2.setData(new Date());
        m2.setEnviado(false);
        m2.setTexto("Texto 2");
        m2.setUsuario(1);
        u1.getMensagens().add(m2);

        Mensagem m3 = new Mensagem();
        m3.setCodigo(3);
        m3.setData(new Date());
        m3.setEnviado(false);
        m3.setTexto("Texto 3");
        m3.setUsuario(1);
        u1.getMensagens().add(m3);
        usuarios.add(u1);

        Usuario u2 = new Usuario();
        u2.setCodigo(2);
        u2.setUsuario("Teste2");
        u2.setSenha("*teste2*");
        usuarios.add(u2);

        Usuario u3 = new Usuario();
        u3.setCodigo(3);
        u3.setUsuario("Teste3");
        u3.setSenha("*teste3*");

        Mensagem m4 = new Mensagem();
        m4.setCodigo(4);
        m4.setData(new Date());
        m4.setEnviado(true);
        m4.setTexto("Texto 4");
        m4.setUsuario(3);
        u3.getMensagens().add(m4);

        Mensagem m5 = new Mensagem();
        m5.setCodigo(5);
        m5.setData(new Date());
        m5.setEnviado(true);
        m5.setTexto("Texto 5");
        m5.setUsuario(3);
        u3.getMensagens().add(m5);
        usuarios.add(u3);

        UsuarioHolder holder = new UsuarioHolder();
        holder.setUsuarios(usuarios);

        out.println(new Gson().toJson(holder));*/
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

        /*System.out.println("PASSOU POST:");
        String codigo = request.getParameter("codigo");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        System.out.println("Dados: " + codigo + ", " + usuario + ", " + senha);*/
        
        
        

        /*String codigo = request.getParameter("codigo");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        System.out.println("Dados: " + codigo + ", " + usuario + ", " + senha);
        
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");

        List<Usuario> usuarios = new ArrayList();
        
        Usuario u = new Usuario();
        u.setCodigo(Integer.parseInt(codigo));
        u.setUsuario(usuario);
        u.setSenha(senha);
        
        usuarios.add(u);
        UsuarioHolder holder = new UsuarioHolder();
        holder.setUsuarios(usuarios);
        
        out.println(new Gson().toJson(holder));*/
        
        
        
        /*response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        List<Usuario> usuarios = new ArrayList();

        Usuario u1 = new Usuario();
        u1.setCodigo(1);
        u1.setUsuario("Teste1");
        u1.setSenha("*teste1*");

        Mensagem m1 = new Mensagem();
        m1.setCodigo(1);
        m1.setData(new Date());
        m1.setEnviado(true);
        m1.setTexto("Texto 1");
        m1.setUsuario(1);
        u1.getMensagens().add(m1);

        Mensagem m2 = new Mensagem();
        m2.setCodigo(2);
        m2.setData(new Date());
        m2.setEnviado(false);
        m2.setTexto("Texto 2");
        m2.setUsuario(1);
        u1.getMensagens().add(m2);

        Mensagem m3 = new Mensagem();
        m3.setCodigo(3);
        m3.setData(new Date());
        m3.setEnviado(false);
        m3.setTexto("Texto 3");
        m3.setUsuario(1);
        u1.getMensagens().add(m3);
        usuarios.add(u1);

        Usuario u2 = new Usuario();
        u2.setCodigo(2);
        u2.setUsuario("Teste2");
        u2.setSenha("*teste2*");
        usuarios.add(u2);

        Usuario u3 = new Usuario();
        u3.setCodigo(3);
        u3.setUsuario("Teste3");
        u3.setSenha("*teste3*");

        Mensagem m4 = new Mensagem();
        m4.setCodigo(4);
        m4.setData(new Date());
        m4.setEnviado(true);
        m4.setTexto("Texto 4");
        m4.setUsuario(3);
        u3.getMensagens().add(m4);

        Mensagem m5 = new Mensagem();
        m5.setCodigo(5);
        m5.setData(new Date());
        m5.setEnviado(true);
        m5.setTexto("Texto 5");
        m5.setUsuario(3);
        u3.getMensagens().add(m5);
        usuarios.add(u3);

        UsuarioHolder holder = new UsuarioHolder();
        holder.setUsuarios(usuarios);

        out.println(new Gson().toJson(holder));*/
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
