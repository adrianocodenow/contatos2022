/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.controller;

import com.adrianocodenow.contatos2022.dao.TelefoneDao;
import com.adrianocodenow.contatos2022.model.Telefone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apereira
 */
public class TelefoneCtrl {

    public static String[] listaTelefones(List<Integer> listaID) {
        ArrayList<String> telefoneList = new ArrayList<String>();
        for (Integer idTelefone : listaID) {
            Telefone telefone = new Telefone();
            telefone = TelefoneDao.buscaID(idTelefone);
            telefoneList.add(telefone.getTelefone());
        }
        String[] retorno = telefoneList.toArray(new String[telefoneList.size()]);
        return retorno;
    }

}
