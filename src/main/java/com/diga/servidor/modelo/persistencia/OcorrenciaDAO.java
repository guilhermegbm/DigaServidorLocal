/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.diga.servidor.modelo.beans.Ocorrencia;
import com.diga.servidor.utils.ConnectionFactory;
import com.diga.servidor.utils.DBConnection;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 *
 * @author Guilherme
 */
public class OcorrenciaDAO {

    public static String persistirOcorrencia(Ocorrencia o) {
        Blob x = null;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into ocorrencia (ocoTitulo, ocoDescricao, ocoLatitude, ocoLongitude, ocoFotoOcorrencia, ocoDataPostagem, ocoNumCurtidas, ocoNumReports, oco_catCodigo, oco_sitCodigo, oco_usuCodigo) values (?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, o.getTitulo());
            stmt.setString(2, o.getDescricao());
            stmt.setDouble(3, o.getLatitude());
            stmt.setDouble(4, o.getLongitude());
            stmt.setBlob(5, x);
            stmt.setTimestamp(6, new Timestamp(o.getDataPostagem().getTime()));
            stmt.setInt(7, o.getNumCurtidas());
            stmt.setInt(8, o.getNumReports());
            stmt.setInt(9, o.getCategoria());
            stmt.setInt(10, o.getSituacao());
            stmt.setInt(11, o.getUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }

    public static List<Ocorrencia> listarOcorrencias(String query) {
        List<Ocorrencia> l = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt;
            if ((query == null) || (query.equals(""))){
                stmt = conn.prepareStatement("select * from ocorrencia");
            } else {
                stmt = conn.prepareStatement(query);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCodigo(rs.getInt(1));
                o.setTitulo(rs.getString(2));
                o.setDescricao(rs.getString(3));
                o.setLatitude(rs.getDouble(4));
                o.setLongitude(rs.getDouble(5));
                o.setFotoOcorrencia(rs.getBytes(6));
                o.setDataPostagem(rs.getDate(7));
                o.setDataResolvida(rs.getDate(8));
                o.setFotoResolvida(rs.getBytes(9));
                o.setNumCurtidas(rs.getInt(10));
                o.setNumReports(rs.getInt(11));
                o.setCategoria(rs.getInt(12));
                o.setSituacao(rs.getInt(13));
                o.setUsuario(rs.getInt(14));
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));

                l.add(o);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        }
        return l;
    }
}
