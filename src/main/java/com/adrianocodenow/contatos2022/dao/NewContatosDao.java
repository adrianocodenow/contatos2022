package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apereira
 */
public class NewContatosDao {

    public static List<Contato> lista() {
        String sql = "SELECT * FROM contatosold3";
        List<Contato> contatos = new ArrayList<Contato>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(resultado.getInt("idContato"));
                    rstContato.setNome(resultado.getString("nome"));
                    rstContato.setSobrenome(resultado.getString("sobrenome"));
                    rstContato.setDataCriacao(resultado.getTimestamp("dataCriacao"));
                    rstContato.setDataUltimaAtualizacao(resultado.getTimestamp("dataUltimaAtualizacao"));
                    rstContato.setAtivo(resultado.getBoolean("ativo"));
                    contatos.add(rstContato);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return contatos;
    }

    public static void main(String[] args) {
        CriaTabelas.criaTabelaContatos();
        for (Contato retorno : lista()) {
            System.out.println("Nome : " + retorno.getNome());
            System.out.println("Sobrenome : " + retorno.getSobrenome());
            System.out.println("Data Criação : " + retorno.getDataCriacao());
            System.out.println("Data Última Atualização: "
                    + retorno.getDataUltimaAtualizacao());
            System.out.println("Ativo : " + (retorno.isAtivo() ? "SIM" : "NÃO"));
            System.out.println("----------------------------------------------------");
            retorno.setDataCriacao(new java.util.Date());
            retorno.setDataUltimaAtualizacao(retorno.getDataCriacao());

            if (ContatosDao.insere(retorno)) {
                System.out.println("Incluido com Sucesso");
            } else {
                System.out.println("Falha na inclusão do novo contato");
            }

        }
    }
}
