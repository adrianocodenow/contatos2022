package com.adrianocodenow.contatos2022.model;

/**
 *
 * @author apereira
 */
public class TipoEndereco {

    private int idTipoEndereco;
    private String tipoEndereco;

    public static String getTabela() {
        return "idTipoEndereco INTEGER NOT NULL PRIMARY KEY, "
                + "tipoEndereco TEXT";
    }

    public int getIdTipoEndereco() {
        return idTipoEndereco;
    }

    public void setIdTipoEndereco(int idTipoEndereco) {
        this.idTipoEndereco = idTipoEndereco;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }
}
