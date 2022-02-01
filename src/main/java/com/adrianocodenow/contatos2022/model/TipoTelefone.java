package com.adrianocodenow.contatos2022.model;

/**
 *
 * @author apereira
 */
public class TipoTelefone {

    private int idTipoTelefone;
    private String tipoTelefone;

    public static String getTabela() {
        return "idTipoTelefone INTEGER NOT NULL PRIMARY KEY, "
                + "tipoTelefone TEXT";
    }

    public int getIdTipoTelefone() {
        return idTipoTelefone;
    }

    public void setIdTipoTelefone(int idTipoTelefone) {
        this.idTipoTelefone = idTipoTelefone;
    }

    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

}
