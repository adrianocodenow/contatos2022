package com.adrianocodenow.contatos2022.model;

/**
 *
 * @author apereira
 *
 */
public class Telefone {

    private int idTelefone;
    private String telefone;
    private int idTipoTelefone;
    private int idContato;

    public static String getTabela() {
        return "idTelefone INTEGER NOT NULL PRIMARY KEY, "
                + "telefone TEXT, "
                + "idTipoTelefone INTEGER, "
                + "idContato INTEGER,"
                + "FOREIGN KEY (idContato) "
                + "REFERENCES contatos (idContato) ON DELETE CASCADE";
    }

    public int getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(int idTelefone) {
        this.idTelefone = idTelefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdTipoTelefone() {
        return idTipoTelefone;
    }

    public void setIdTipoTelefone(int idTipoTelefone) {
        this.idTipoTelefone = idTipoTelefone;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

}
