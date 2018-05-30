/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.beans;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class Ocorrencia {
    private int codigo;
    private String titulo;
    private String descricao;
    private double latitude;
    private double longitude;
    private byte[] fotoOcorrencia;
    private Date dataPostagem;
    private byte[] fotoResolvida;
    private Date dataResolvida;
    private int numCurtidas;
    private int numReports;
    
    private int categoria;
    private int situacao;
    private int usuario;
    private List<Tag> tags;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getFotoOcorrencia() {
        return fotoOcorrencia;
    }

    public void setFotoOcorrencia(byte[] fotoOcorrencia) {
        this.fotoOcorrencia = fotoOcorrencia;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public byte[] getFotoResolvida() {
        return fotoResolvida;
    }

    public void setFotoResolvida(byte[] fotoResolvida) {
        this.fotoResolvida = fotoResolvida;
    }

    public Date getDataResolvida() {
        return dataResolvida;
    }

    public void setDataResolvida(Date dataResolvida) {
        this.dataResolvida = dataResolvida;
    }

    public int getNumCurtidas() {
        return numCurtidas;
    }

    public void setNumCurtidas(int numCurtidas) {
        this.numCurtidas = numCurtidas;
    }

    public int getNumReports() {
        return numReports;
    }

    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
