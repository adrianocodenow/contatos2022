package com.adrianocodenow.contatos2022.model;

/**
 *
 * @author apereira
 *
 */
public class Endereco {

    private int idEndereco;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
    private int idTipoEndereco;
    private int idContato;

    public static String getTabela() {
        return "idEndereco INTEGER NOT NULL PRIMARY KEY, "
                + "endereco TEXT, "
                + "bairro TEXT, "
                + "cidade TEXT, "
                + "estado TEXT, "
                + "cep TEXT, "
                + "pais TEXT, "
                + "idTipoEndereco INTEGER, "
                + "idContato INTEGER,"
                + "FOREIGN KEY (idContato) "
                + "REFERENCES contatos (idContato) ON DELETE CASCADE";
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getIdTipoEndereco() {
        return idTipoEndereco;
    }

    public void setIdTipoEndereco(int idTipoEndereco) {
        this.idTipoEndereco = idTipoEndereco;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

}
