/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.controle;

import com.diga.servidor.modelo.beans.Tag;
import com.diga.servidor.modelo.persistencia.TagDAO;
import java.util.List;

/**
 *
 * @author Guilherme
 */
public class ControleTag {
    public static List<Tag> listarTags () {
        return TagDAO.listarTags();
    }

    public static String insereOcorrenciaPossuiTags(List<Tag> tags) {
        return TagDAO.insereOcorrenciaPossuiTags(tags);
    }
}
