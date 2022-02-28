package com.adrianocodenow.contatos2022.model;

import java.util.Date;

/**
 *
 * @author apereira
 */
public class Contato extends Endereco{

    private int idContato;
    private String nome;
    private String sobrenome;
    private String nomeSobrenomeFonetico;
    private Date dataCriacao;
    private Date dataUltimaAtualizacao;
    private boolean ativo;

    public static String getTabela() {
        return "idContato INTEGER NOT NULL PRIMARY KEY, "
                + "nome TEXT, "
                + "sobrenome TEXT, "
                + "nomeSobrenomeFonetico TEXT, "
                + "dataCriacao DATE, "
                + "dataUltimaAtualizacao DATE, "
                + "ativo BOOLEAN";
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeSobrenomeFonetico() {
        return nomeSobrenomeFonetico;
    }

    public void setNomeSobrenomeFonetico(String nomeSobrenomeFonetico) {
        this.nomeSobrenomeFonetico = nomeSobrenomeFonetico;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return nome+" "+sobrenome;
    }

    
   
}
