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
import com.diga.servidor.modelo.beans.UsuarioCurteOcorrencia;
import com.diga.servidor.modelo.beans.UsuarioReportaOcorrencia;
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
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into ocorrencia (ocoTitulo, ocoDescricao, ocoLatitude, ocoLongitude, ocoEndereco, ocoFotoOcorrencia, ocoDataPostagem, ocoNumCurtidas, ocoNumReports, oco_catCodigo, oco_sitCodigo, oco_usuCodigo) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, o.getTitulo());
            stmt.setString(2, o.getDescricao());
            stmt.setDouble(3, o.getLatitude());
            stmt.setDouble(4, o.getLongitude());
            stmt.setString(5, o.getEndereco());
            stmt.setString(6, o.getFotoOcorrencia());
            stmt.setTimestamp(7, new Timestamp(o.getDataPostagem().getTime()));
            stmt.setInt(8, o.getNumCurtidas());
            stmt.setInt(9, o.getNumReports());
            stmt.setInt(10, o.getCategoria());
            stmt.setInt(11, o.getSituacao());
            stmt.setInt(12, o.getUsuario());

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
            if ((query == null) || (query.equals(""))) {
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
                o.setEndereco(rs.getString(6));
                o.setFotoOcorrencia(rs.getString(7));
                o.setDataPostagem(rs.getDate(8));
                o.setDataResolvida(rs.getDate(9));
                o.setFotoResolvida(rs.getString(10));
                o.setNumCurtidas(rs.getInt(11));
                o.setNumReports(rs.getInt(12));
                o.setCategoria(rs.getInt(13));
                o.setSituacao(rs.getInt(14));
                o.setUsuario(rs.getInt(15));
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));

                l.add(o);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        }
        return l;
    }

    public static String curteOcorrencia(UsuarioCurteOcorrencia uso) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into usuario_curte_ocorrencia (uso_usuCodigo, uso_ocoCodigo, usoLatitude, usoLongitude) values (?,?,?,?)");
            stmt.setInt(1, uso.getUsuario());
            stmt.setInt(2, uso.getOcorrencia());
            stmt.setDouble(3, uso.getLatitude());
            stmt.setDouble(4, uso.getLongitude());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }

    public static String descurteOcorrencia(int usuario, int ocorrencia) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from usuario_curte_ocorrencia where uso_usuCodigo = ? and uso_ocoCodigo = ?");
            stmt.setInt(1, usuario);
            stmt.setInt(2, ocorrencia);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }

    public static String reportaOcorrencia(UsuarioReportaOcorrencia uro) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into usuario_reporta_ocorrencia (uro_usuCodigo, uro_ocoCodigo, uroLatitude, uroLongitude) values (?,?,?,?)");
            stmt.setInt(1, uro.getUsuario());
            stmt.setInt(2, uro.getOcorrencia());
            stmt.setDouble(3, uro.getLatitude());
            stmt.setDouble(4, uro.getLongitude());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }

    public static String desreportaOcorrencia(int usuario, int ocorrencia) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from usuario_reporta_ocorrencia where uro_usuCodigo = ? and uro_ocoCodigo = ?");
            stmt.setInt(1, usuario);
            stmt.setInt(2, ocorrencia);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        }
        return "1";
    }
}
