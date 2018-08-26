/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.beans;

/**
 *
 * @author Guilherme
 */
public class Usuario {
    private int codigo;
    private String nome;
    private String nomeUsuario;
    private String senha;
    private double latitudeResidencia;
    private double longitudeResidencia;
    private String enderecoCompleto;
    private boolean isInBlacklist;
    private int numStrikes;
    private boolean isAdm;
    private String foto;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getLatitudeResidencia() {
        return latitudeResidencia;
    }

    public void setLatitudeResidencia(double latitudeResidencia) {
        this.latitudeResidencia = latitudeResidencia;
    }

    public double getLongitudeResidencia() {
        return longitudeResidencia;
    }

    public void setLongitudeResidencia(double longitudeResidencia) {
        this.longitudeResidencia = longitudeResidencia;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public boolean isIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(boolean isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public int getNumStrikes() {
        return numStrikes;
    }

    public void setNumStrikes(int numStrikes) {
        this.numStrikes = numStrikes;
    }

    public boolean isAdm() {
        return isAdm;
    }

    public void setAdm(boolean isAdm) {
        this.isAdm = isAdm;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
