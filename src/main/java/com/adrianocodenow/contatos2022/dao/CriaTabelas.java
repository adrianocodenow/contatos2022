package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
import com.adrianocodenow.contatos2022.model.Telefone;
import com.adrianocodenow.contatos2022.model.TipoEndereco;
import com.adrianocodenow.contatos2022.model.TipoTelefone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    public static void criaBancoDeDados() {

        List<String> tabelas = checkDB();

        if (tabelas.isEmpty() || tabelas.size() < 5) {
            criaTabelaContatos();
            criaTabelaEnderecos();
            criaTabelaTelefones();
            criaTabelaTipoEnderecos();
            criaTabelaTipoTelefones();
        }
        if (TipoEnderecoDao.lista().isEmpty()) {
            TipoEndereco tipoEndereco = new TipoEndereco();
            tipoEndereco.setTipoEndereco("Residência");
            new TipoEnderecoDao().insere(tipoEndereco);
            tipoEndereco.setTipoEndereco("Trabalho");
            new TipoEnderecoDao().insere(tipoEndereco);
        }
        if (new TipoTelefoneDao().lista().isEmpty()) {
            TipoTelefone tipoTelefone = new TipoTelefone();
            tipoTelefone.setTipoTelefone("Celular");
            TipoTelefoneDao.insere(tipoTelefone);
            tipoTelefone.setTipoTelefone("Residência");
            TipoTelefoneDao.insere(tipoTelefone);
            tipoTelefone.setTipoTelefone("Trabalho");
            TipoTelefoneDao.insere(tipoTelefone);
        }

    }

    public static List<String> checkDB() {
        String sql
                = "SELECT name FROM sqlite_master";
        List<String> tabelas = new ArrayList<String>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    tabelas.add(resultado.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return tabelas;
    }

    public static void main(String[] args) {

        criaBancoDeDados();
    }
}
