/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.adrianocodenow.contatos2022.controller;

import com.adrianocodenow.contatos2022.dao.ContatosDao;
import com.adrianocodenow.contatos2022.dao.EnderecoDao;
import com.adrianocodenow.contatos2022.dao.TipoEnderecoDao;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
import com.adrianocodenow.contatos2022.model.TipoEndereco;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apereira
 */
public class ContatoCtrl {
    
    private static ContatosDao contatoDao = new ContatosDao();

    public static long insere(String nome, String sobrenome) {
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setSobrenome(sobrenome);
        contato.setDataCriacao(new java.util.Date());
        contato.setDataUltimaAtualizacao(contato.getDataCriacao());
        contato.setAtivo(true);
        return contatoDao.insere(contato);
    }

    public static boolean altera(int id, String nome, String sobrenome) {
        for (Contato retorno : contatoDao.pesquisaID(id)) {
            retorno.setNome(nome);
            retorno.setSobrenome(sobrenome);
            retorno.setDataUltimaAtualizacao(new java.util.Date());;;
            contatoDao.altera(retorno);
        }
        return true;
    }

    public static boolean alteraGeral(String busca, String nome, String sobrenome) {
        for (Contato retorno : contatoDao.pesquisaFonetica(
                StrNormalize.removeAcentos1(
                        StrNormalize.removeAcentos1(busca).toLowerCase()
                ))) {
            retorno.setNome(nome);
            retorno.setSobrenome(sobrenome);
            retorno.setDataUltimaAtualizacao(new java.util.Date());;;
            contatoDao.altera(retorno);
        }
        return true;
    }

    public static boolean deleta(int id) {
        contatoDao.deleta(id);
        return true;
    }

    public static String[] pesquisa() {
        ArrayList<String> nomesSobrenomes = new ArrayList<String>();
        for (Contato contato : contatoDao.lista()) {
            nomesSobrenomes.add(contato.getNome() + " " + contato.getSobrenome());
        }
        String[] retorno = nomesSobrenomes.toArray(new String[nomesSobrenomes.size()]);
        return retorno;
    }

    public static List<Contato> pesquisaObj() {
        return contatoDao.lista();
    }

    public static List<Contato> carregaContato(String nomesSobrenomes) {
//        ArrayList<Contato> contatos = new ArrayList<Contato>();
//        for (Contato contato : ContatosDao.pesquisaFonetica(nomesSobrenomes)) {
//
//        }
        return contatoDao.pesquisaFonetica(nomesSobrenomes);
    }

    public static List<Endereco> pesquisaEnderecoIdContato(int id) {
        return EnderecoDao.listaIDContato(id);
    }

    public static String[] pesquisaTipoEndereco(List<Integer> idTipoEnderecos) {
        ArrayList<String> tipoEnderecoList = new ArrayList<String>();

        for (int idTipoEndereco : idTipoEnderecos) {
            TipoEndereco tipoEndereco = new TipoEndereco();
            tipoEndereco = new TipoEnderecoDao().buscaID(idTipoEndereco);
            if (!tipoEndereco.equals(null)) {
                tipoEnderecoList.add(tipoEndereco.getTipoEndereco());
            }
        }
        String[] retorno = tipoEnderecoList.toArray(new String[tipoEnderecoList.size()]);
        return retorno;
    }

}
