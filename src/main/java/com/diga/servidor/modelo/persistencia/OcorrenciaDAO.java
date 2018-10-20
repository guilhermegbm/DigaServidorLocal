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
import java.io.IOException;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
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
            
            stmt = conn.prepareStatement("insert into ocorrencia (ocoTitulo, ocoDescricao, ocoLatitude, ocoLongitude, ocoEndereco, ocoFotoOcorrencia, ocoDataPostagem, ocoNumCurtidas, ocoNumReports, oco_catCodigo, oco_sitCodigo, oco_usuCodigo, ocoResolvida) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
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
            stmt.setBoolean(13, o.isResolvida());
            
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

    @SuppressWarnings("empty-statement")
    public static List<Ocorrencia> listarOcorrencias(String query, int usuCodigo) {
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
                
                o.setResolvida(rs.getBoolean(11));
                o.setNumCurtidas(rs.getInt(12));
                o.setNumReports(rs.getInt(13));
                o.setCategoria(rs.getInt(14));
                o.setSituacao(rs.getInt(15));
                o.setUsuario(rs.getInt(16));
                
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));
                
                o.setUsuarioAtualCurtiu(OcorrenciaDAO.usuarioAtualCurtiu(rs.getInt(1), usuCodigo));
                o.setUsuarioAtualReportou(OcorrenciaDAO.usuarioAtualReportou(rs.getInt(1), usuCodigo));
                
                System.out.println("Valores: " + o.isUsuarioAtualReportou() + ", " + o.isUsuarioAtualCurtiu());

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
    
    public static List<Ocorrencia> listarOcorrenciasPorPaginacao(int usuCodigo, int pagina, int rowsPorPagina) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Ocorrencia> l = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement("select * " +
                    "from ocorrencia " +
                    "order by ocoDataPostagem DESC " +
                    "LIMIT ?,?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            // "Hints" para o statement:
            stmt.setFetchSize(rowsPorPagina);
            stmt.setMaxRows(rowsPorPagina);
            
            stmt.setInt(1, ((pagina-1) * rowsPorPagina));
            stmt.setInt(2, rowsPorPagina);
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
                
                o.setResolvida(rs.getBoolean(11));
                //o.setNumCurtidas(rs.getInt(12));
                //o.setNumReports(rs.getInt(13));
                o.setNumCurtidas(OcorrenciaDAO.pegaQtdeCurtidas(o.getCodigo()));
                o.setNumReports(OcorrenciaDAO.pegaQtdeReports(o.getCodigo()));
                o.setCategoria(rs.getInt(14));
                o.setSituacao(rs.getInt(15));
                o.setUsuario(rs.getInt(16));
                
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));
                
                o.setUsuarioAtualCurtiu(OcorrenciaDAO.usuarioAtualCurtiu(rs.getInt(1), usuCodigo));
                o.setUsuarioAtualReportou(OcorrenciaDAO.usuarioAtualReportou(rs.getInt(1), usuCodigo));
                
                l.add(o);
            }
            
        } catch (SQLException e) {
            System.out.println("listarOcorrenciasPorPaginacao: Erro ao conectar bd: " + e.getMessage());
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
    
    private static boolean usuarioAtualCurtiu (int ocoCodigo, int usuCodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select true from ocorrencia left join usuario_curte_ocorrencia on (uso_ocoCodigo = ocoCodigo) where uso_ocoCodigo = ? and uso_usuCodigo = ?");
            
            stmt.setInt(1, ocoCodigo);
            stmt.setInt(2, usuCodigo);
            
            rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return false;
    }
    
    private static boolean usuarioAtualReportou (int ocoCodigo, int usuCodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select true from ocorrencia left join usuario_reporta_ocorrencia on (uro_ocoCodigo = ocoCodigo) where uro_ocoCodigo = ? and uro_usuCodigo = ?");
            
            stmt.setInt(1, ocoCodigo);
            stmt.setInt(2, usuCodigo);
            
            rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return false;
    }
    
    public static int pegaQtdeCurtidas (int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select count(uso_ocoCodigo) from usuario_curte_ocorrencia where uso_ocoCodigo = ?");
            
            stmt.setInt(1, codigoOcorrencia);
            
            rs = stmt.executeQuery();

            int curtidas = 0;
            while (rs.next()){
                curtidas = rs.getInt(1);
            }
            return curtidas;
        } catch (SQLException e) {
            System.out.println("pegaQtdeCurtidas: Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return 0;
    }
    
    public static int pegaQtdeReports (int codigoOcorrencia) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select count(uro_ocoCodigo) from usuario_reporta_ocorrencia where uro_ocoCodigo = ?");
            
            stmt.setInt(1, codigoOcorrencia);
            
            rs = stmt.executeQuery();

            int reports = 0;
            
            while (rs.next()){
                reports = rs.getInt(1);
            }
            return reports;
        } catch (SQLException e) {
            System.out.println("pegaQtdeReports: Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return 0;
    }
    
    public static Ocorrencia pegaDadosPorCodigo (int codigoOcorrencia, int usuCodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Ocorrencia o = new Ocorrencia();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("select * from ocorrencia where ocoCodigo = ?");
            stmt.setInt(1, codigoOcorrencia);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
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
                
                o.setResolvida(rs.getBoolean(11));
                o.setNumCurtidas(rs.getInt(12));
                o.setNumReports(rs.getInt(13));
                o.setCategoria(rs.getInt(14));
                o.setSituacao(rs.getInt(15));
                o.setUsuario(rs.getInt(16));
                
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));
                
                o.setUsuarioAtualCurtiu(OcorrenciaDAO.usuarioAtualCurtiu(rs.getInt(1), usuCodigo));
                o.setUsuarioAtualReportou(OcorrenciaDAO.usuarioAtualReportou(rs.getInt(1), usuCodigo));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar bd: " + e.getLocalizedMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return o;
    }
    
    public static List<Ocorrencia> pegaDadosMapaPrimario () {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Ocorrencia> l = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement("select ocoCodigo, ocoLatitude, ocoLongitude, oco_catCodigo from ocorrencia");
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ocorrencia o = new Ocorrencia();
                o.setCodigo(rs.getInt(1));
                o.setLatitude(rs.getDouble(2));
                o.setLongitude(rs.getDouble(3));
                o.setCategoria(rs.getInt(4));
                
                l.add(o);
            }
            
        } catch (SQLException e) {
            System.out.println("pegaDadosMapaPrimario: Erro ao conectar bd: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return l;
    }
    
    public static Ocorrencia pegaDadosMapaSecundario (int codigoOcorrencia, int usuCodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Ocorrencia o = new Ocorrencia();
        o.setCodigo(codigoOcorrencia);

        try {
            conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement("select ocoResolvida, ocoFotoOcorrencia, ocoFotoResolvida from ocorrencia where ocoCodigo = ?");
            stmt.setInt(1, codigoOcorrencia);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                o.setResolvida(rs.getBoolean(1));
                
                Blob fotoOcorrencia = rs.getBlob(2);
                Blob fotoResolvida = rs.getBlob(3);
                
                if (o.isResolvida()){
                    if (fotoResolvida != null ){
                        o.setFotoResolvida(Base64.getEncoder().encodeToString(fotoResolvida.getBytes(1, (int) fotoResolvida.length())));
                    }
                } else {
                    o.setFotoOcorrencia(Base64.getEncoder().encodeToString(fotoOcorrencia.getBytes(1, (int) fotoOcorrencia.length())));
                }
                
                o.setUsuarioAtualCurtiu(OcorrenciaDAO.usuarioAtualCurtiu(rs.getInt(1), usuCodigo));
                o.setUsuarioAtualReportou(OcorrenciaDAO.usuarioAtualReportou(rs.getInt(1), usuCodigo));
                
                o.setNumCurtidas(OcorrenciaDAO.pegaQtdeCurtidas(codigoOcorrencia));
                o.setNumReports(OcorrenciaDAO.pegaQtdeReports(codigoOcorrencia));
            }
            
        } catch (SQLException e) {
            System.out.println("pegaDadosMapaSecundario: Erro ao conectar bd: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return o;
    }

    public static List<Ocorrencia> atualizaFeed(String dataInicial, int usuCodigo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Ocorrencia> l = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            
            stmt = conn.prepareStatement("select * from ocorrencia where ocoDataPostagem > ?");
            stmt.setString(1, dataInicial);
            
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
                
                o.setResolvida(rs.getBoolean(11));
                //o.setNumCurtidas(rs.getInt(12));
                //o.setNumReports(rs.getInt(13));
                o.setNumCurtidas(OcorrenciaDAO.pegaQtdeCurtidas(o.getCodigo()));
                o.setNumReports(OcorrenciaDAO.pegaQtdeReports(o.getCodigo()));
                o.setCategoria(rs.getInt(14));
                o.setSituacao(rs.getInt(15));
                o.setUsuario(rs.getInt(16));
                
                o.setTags(TagDAO.listarTagsPorOcorrencia(rs.getInt(1)));
                
                o.setUsuarioAtualCurtiu(OcorrenciaDAO.usuarioAtualCurtiu(rs.getInt(1), usuCodigo));
                o.setUsuarioAtualReportou(OcorrenciaDAO.usuarioAtualReportou(rs.getInt(1), usuCodigo));
                
                l.add(o);
            }
            
        } catch (SQLException e) {
            System.out.println("listarOcorrenciasPorPaginacao: Erro ao conectar bd: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {};
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
            try { if (conn != null) conn.close(); } catch (SQLException e) {};
        }
        return l;
    }
}
