package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
import com.adrianocodenow.contatos2022.model.Telefone;
import com.adrianocodenow.contatos2022.model.TipoEndereco;
import com.adrianocodenow.contatos2022.model.TipoTelefone;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apereira
 *
 */
public class CriaTabelas {

    public static void criaTabela(String nomeTabela, String campos) {
        String sql
                = "CREATE TABLE IF NOT EXISTS " + nomeTabela + "(" + campos + ")";
        Connection db = null;
        Statement stmt = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriaTabelas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public static void criaTabelaContatos() {
        criaTabela("contatos", Contato.getTabela());
    }

    public static void criaTabelaEnderecos() {
        criaTabela("enderecos", Endereco.getTabela());
    }

    public static void criaTabelaTelefones() {
        criaTabela("telefones", Telefone.getTabela());
    }

    public static void criaTabelaTipoEnderecos() {
        criaTabela("tipoEnderecos", TipoEndereco.getTabela());
    }

    public static void criaTabelaTipoTelefones() {
        criaTabela("tipoTelefones", TipoTelefone.getTabela());
    }

    public static void main(String[] args) {
        criaTabelaContatos();
        criaTabelaEnderecos();
        criaTabelaTelefones();
        criaTabelaTipoEnderecos();
        criaTabelaTipoTelefones();

    }
}
