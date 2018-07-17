/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.modelo.persistencia;

import com.diga.servidor.controle.ControleTag;
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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Guilherme
 */
public class OcorrenciaDAO {

    public static String persistirOcorrencia(Ocorrencia o){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            //byte[] byteDecodificado = Base64.getDecoder().decode(o.getFotoOcorrencia().getBytes(StandardCharsets.UTF_8));
            byte[] byteDecodificado = decoder.decodeBuffer(o.getFotoOcorrencia());
            Blob b = new SerialBlob(byteDecodificado);
            
            conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement("insert into ocorrencia (ocoTitulo, ocoDescricao, ocoLatitude, ocoLongitude, ocoEndereco, ocoFotoOcorrencia, ocoDataPostagem, ocoNumCurtidas, ocoNumReports, oco_catCodigo, oco_sitCodigo, oco_usuCodigo) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            
            stmt.setString(1, o.getTitulo());
            stmt.setString(2, o.getDescricao());
            stmt.setDouble(3, o.getLatitude());
            stmt.setDouble(4, o.getLongitude());
            stmt.setString(5, o.getEndereco());
            stmt.setBlob(6, b);
            stmt.setTimestamp(7, new Timestamp(o.getDataPostagem().getTime()));
            stmt.setInt(8, o.getNumCurtidas());
            stmt.setInt(9, o.getNumReports());
            stmt.setInt(10, o.getCategoria());
            stmt.setInt(11, o.getSituacao());
            stmt.setInt(12, o.getUsuario());
            
            stmt.executeUpdate();
            
            String erro = ControleTag.insereOcorrenciaPossuiTags(o.getTags());

            if (erro.equals("0")) throw new SQLException("Erro na inserção das ocorrencia_possui_tags");
            

        } catch (IOException e) {
            System.out.println("Erro ao Formatar foto: " + e.getLocalizedMessage());
            return "0";
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }

    public static List<Ocorrencia> listarOcorrencias(String query) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Ocorrencia> l = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            if ((query == null) || (query.equals(""))) {
                stmt = conn.prepareStatement("select * from ocorrencia");
            } else {
                stmt = conn.prepareStatement(query);
            }
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCodigo(rs.getInt(1));
                o.setTitulo(rs.getString(2));
                o.setDescricao(rs.getString(3));
                o.setLatitude(rs.getDouble(4));
                o.setLongitude(rs.getDouble(5));
                o.setEndereco(rs.getString(6));
                
                Blob fotoOcorrencia = rs.getBlob(7);
                o.setFotoOcorrencia(Base64.getEncoder().encodeToString(fotoOcorrencia.getBytes(1, (int) fotoOcorrencia.length())));
                
                o.setDataPostagem(rs.getDate(8));
                o.setDataResolvida(rs.getDate(9));
                
                Blob fotoResolvida = rs.getBlob(10);
                if (fotoResolvida != null ){
                    o.setFotoResolvida(Base64.getEncoder().encodeToString(fotoResolvida.getBytes(1, (int) fotoResolvida.length())));
                }
                
                o.setNumCurtidas(rs.getInt(11));
                o.setNumReports(rs.getInt(12));
                o.setCategoria(rs.getInt(13));
                o.setSituacao(rs.getInt(14));
                o.setUsuario(rs.getInt(15));
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));

                l.add(o);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return l;
    }

    public static String curteOcorrencia(UsuarioCurteOcorrencia uso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("insert into usuario_curte_ocorrencia (uso_usuCodigo, uso_ocoCodigo, usoLatitude, usoLongitude) values (?,?,?,?)");
            stmt.setInt(1, uso.getUsuario());
            stmt.setInt(2, uso.getOcorrencia());
            stmt.setDouble(3, uso.getLatitude());
            stmt.setDouble(4, uso.getLongitude());

            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }

    public static String descurteOcorrencia(int usuario, int ocorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("delete from usuario_curte_ocorrencia where uso_usuCodigo = ? and uso_ocoCodigo = ?");
            stmt.setInt(1, usuario);
            stmt.setInt(2, ocorrencia);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }

    public static String reportaOcorrencia(UsuarioReportaOcorrencia uro) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("insert into usuario_reporta_ocorrencia (uro_usuCodigo, uro_ocoCodigo, uroLatitude, uroLongitude) values (?,?,?,?)");
            stmt.setInt(1, uro.getUsuario());
            stmt.setInt(2, uro.getOcorrencia());
            stmt.setDouble(3, uro.getLatitude());
            stmt.setDouble(4, uro.getLongitude());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }

    public static String desreportaOcorrencia(int usuario, int ocorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("delete from usuario_reporta_ocorrencia where uro_usuCodigo = ? and uro_ocoCodigo = ?");
            stmt.setInt(1, usuario);
            stmt.setInt(2, ocorrencia);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }

    public static String deletarOcorrencia(int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            
            if (TagDAO.deletaOcorrenciaPossuiTagsPorCodOcorrencia(codigoOcorrencia).equals("0")) throw new SQLException("Erro na deleção das Tags da ocorrência de código " + codigoOcorrencia);
            
            if (UsuarioCurteOcorrenciaDAO.deletaUsuarioCurteOcorrenciaPorCodOcorrencia(codigoOcorrencia).equals("0")) throw new SQLException("Erro na deleção das curtidas da ocorrência de código " + codigoOcorrencia);
            
            if (UsuarioReportaOcorrenciaDAO.deletaUsuarioReportaOcorrenciaPorCodOcorrencia(codigoOcorrencia).equals("0")) throw new SQLException("Erro na deleção dos Reports da ocorrência de código " + codigoOcorrencia);
            
            stmt = conn.prepareStatement("delete from ocorrencia where ocoCodigo = ?");
            stmt.setInt(1, codigoOcorrencia);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getMessage());
            return "0";
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return "1";
    }
}
