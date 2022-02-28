/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.controller;

import com.adrianocodenow.contatos2022.dao.TipoTelefoneDao;
import com.adrianocodenow.contatos2022.model.TipoTelefone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apereira
 */
public class TipoTelefoneCtrl {

    public static String[] listaTipoTelefones(List<Integer> listaID) {
        ArrayList<String> tipoTelefoneList = new ArrayList<String>();
        for (Integer idTipoTelefone : listaID) {
            TipoTelefone tipoTelefone = new TipoTelefone();
            tipoTelefone = TipoTelefoneDao.buscaID(idTipoTelefone);
            tipoTelefoneList.add(tipoTelefone.getTipoTelefone());
        }
        String[] retorno = tipoTelefoneList.toArray(new String[tipoTelefoneList.size()]);
        return retorno;
    }

    public static String[] lista() {
        ArrayList<String> tipoTelefoneList = new ArrayList<String>();
        for (TipoTelefone tipoTelefone : new TipoTelefoneDao().lista()) {
            tipoTelefoneList.add(tipoTelefone.getTipoTelefone());
        }
        String[] retorno = tipoTelefoneList.toArray(new String[tipoTelefoneList.size()]);
        return retorno;
    }
    public static ArrayList<Integer> indice() {
        ArrayList<Integer> tipoTelefoneList = new ArrayList<Integer>();
        for (TipoTelefone tipoTelefone : new TipoTelefoneDao().lista()) {
            tipoTelefoneList.add(tipoTelefone.getIdTipoTelefone());
        }
        
        return tipoTelefoneList;
    }

}
