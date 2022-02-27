package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.controller.StrNormalize;
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
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author apereira
 */
public class ContatosDao {

    public static boolean insere(Contato objContato) {
        String sql
                = "INSERT INTO "
                + "contatos(nome, sobrenome, nomeSobrenomeFonetico, "
                + "dataCriacao, dataUltimaAtualizacao, ativo)"
                + "VALUES(?, ?, ?, ?, ?, ?)";

        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objContato.getNome());
                stmt.setString(2, objContato.getSobrenome());
                stmt.setString(3,
                        StrNormalize.removeAcentos1(
                                objContato.getNome() + " " + objContato.getSobrenome()
                        ).toLowerCase()
                );
                stmt.setDate(4, new Date(objContato.getDataCriacao().getTime()));
                stmt.setDate(5, new Date(objContato.getDataUltimaAtualizacao().getTime()));
                stmt.setBoolean(6, objContato.isAtivo());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            retorno = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
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
        return retorno;
    }

    public static boolean altera(Contato objContato) {
        String sql
                = "UPDATE contatos SET  "
                + "nome = ? , sobrenome = ? , nomeSobrenomeFonetico = ? , "
                + "dataUltimaAtualizacao = ? , ativo = ?"
                + "WHERE idContato = ?";

        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objContato.getNome());
                stmt.setString(2, objContato.getSobrenome());
                stmt.setString(3,
                        StrNormalize.removeAcentos1(
                                objContato.getNome() + " " + objContato.getSobrenome()
                        ).toLowerCase()
                );
                stmt.setDate(4, new Date(objContato.getDataUltimaAtualizacao().getTime()));
                stmt.setBoolean(5, objContato.isAtivo());
                stmt.setInt(6, objContato.getIdContato());
                stmt.execute();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            retorno = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
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
        return retorno;
    }

    public static boolean deleta(int id) {
        String sql = "DELETE FROM contatos WHERE idContato = ?";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.execute();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            retorno = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
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
        return retorno;
    }

    public static List<Contato> lista() {
        String sql
                = "SELECT * FROM contatos "
                + "ORDER BY nomeSobrenomeFonetico ASC";
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
                    rstContato.setNomeSobrenomeFonetico(resultado.getString("NomeSobrenomeFonetico"));
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

    public static List<Contato> pesquisa(String busca) {
        String sql
                = "SELECT * FROM contatos "
                + "WHERE nome LIKE ? OR sobrenome LIKE ?"
                + "ORDER BY nomeSobrenomeFonetico ASC";
        List<Contato> contatos = new ArrayList<Contato>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, "%" + busca + "%");
                stmt.setString(2, "%" + busca + "%");
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(resultado.getInt("idContato"));
                    rstContato.setNome(resultado.getString("nome"));
                    rstContato.setSobrenome(resultado.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(resultado.getString("NomeSobrenomeFonetico"));
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

    public static List<Contato> pesquisaID(int id) {
        String sql
                = "SELECT * FROM contatos WHERE idContato = ?";
        List<Contato> contatos = new ArrayList<Contato>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setInt(1, id);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(resultado.getInt("idContato"));
                    rstContato.setNome(resultado.getString("nome"));
                    rstContato.setSobrenome(resultado.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(resultado.getString("NomeSobrenomeFonetico"));
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

    public static List<Contato> pesquisaFonetica(String busca) {
        String sql
                = "SELECT * FROM contatos WHERE NomeSobrenomeFonetico = ?"
                + "ORDER BY nomeSobrenomeFonetico ASC";
        List<Contato> contatos = new ArrayList<Contato>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, busca);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(resultado.getInt("idContato"));
                    rstContato.setNome(resultado.getString("nome"));
                    rstContato.setSobrenome(resultado.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(resultado.getString("NomeSobrenomeFonetico"));
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
    
    public static ListModel<String> getModel(){
        return new DefaultListModel<String>() {
            @Override
            public int getSize() {
                return lista().size();
            }

            @Override
            public String getElementAt(int index) {
                return lista().get(index).getNomeSobrenomeFonetico();
            }
        };        
    }

    public static void main(String[] args) {

//        Contato contato = new Contato();
//        contato.setNome("Otavio");
//        contato.setSobrenome("Cintra");
//        contato.setDataCriacao(new java.util.Date());
//        contato.setDataUltimaAtualizacao(contato.getDataCriacao());
//        contato.setAtivo(true);
//        if (insere(contato)) {
//            System.out.println("Incluido com Sucesso");
//            System.out.println("Data Criação: " + contato.getDataCriacao());
//        } else {
//            System.out.println("Falha na inclusão do novo contato");
//        }
//
//        System.out.println("----------------------------------------------------");
//        System.out.println("LISTA");
//        System.out.println("----------------------------------------------------");
//        for (Contato retorno : lista()) {
//            System.out.println("Nome : " + retorno.getNome());
//            System.out.println("Sobrenome : " + retorno.getSobrenome());
//            System.out.println("Data Criação : " + retorno.getDataCriacao());
//            System.out.println("Data Última Atualização: "
//                    + retorno.getDataUltimaAtualizacao());
//            System.out.println("Ativo : " + (retorno.isAtivo() ? "SIM" : "NÃO"));
//            System.out.println("----------------------------------------------------");
//        }
//
//        System.out.println("----------------------------------------------------");
//        System.out.println("PESQUISA");
//        System.out.println("----------------------------------------------------");
//        for (Contato retorno : pesquisa("n")) {
//            System.out.println("Nome : " + retorno.getNome());
//            System.out.println("Sobrenome : " + retorno.getSobrenome());
//            System.out.println("Data Criação : " + retorno.getDataCriacao());
//            System.out.println("Data Última Atualização: "
//                    + retorno.getDataUltimaAtualizacao());
//            System.out.println("Ativo : " + (retorno.isAtivo() ? "SIM" : "NÃO"));
//            System.out.println("----------------------------------------------------");
//        }
//        System.out.println("----------------------------------------------------");
//        System.out.println("PESQUISA FONETICA");
//        System.out.println("----------------------------------------------------");
//        for (Contato retorno : pesquisaFonetica("Cristina Gama")) {
//            System.out.println("Nome : " + retorno.getNome());
//            System.out.println("Sobrenome : " + retorno.getSobrenome());
//            System.out.println("Data Criação : " + retorno.getDataCriacao());
//            System.out.println("Data Última Atualização: "
//                    + retorno.getDataUltimaAtualizacao());
//            System.out.println("Ativo : " + (retorno.isAtivo() ? "SIM" : "NÃO"));
//            System.out.println("----------------------------------------------------");
//        }
//
//
//        System.out.println("----------------------------------------------------");
//        System.out.println("PESQUISA ID");
//        System.out.println("----------------------------------------------------");
//        for (Contato retorno : pesquisaID(11)) {
//            System.out.println("Nome : " + retorno.getNome());
//            System.out.println("Sobrenome : " + retorno.getSobrenome());
//            System.out.println("Data Criação : " + retorno.getDataCriacao());
//            System.out.println("Data Última Atualização: "
//                    + retorno.getDataUltimaAtualizacao());
//            System.out.println("Ativo : " + (retorno.isAtivo() ? "SIM" : "NÃO"));
//            System.out.println("----------------------------------------------------");
//
//            Contato contatoAlt = retorno;
//            contatoAlt.setSobrenome("Porto Ferreira");
//            contatoAlt.setDataUltimaAtualizacao(new java.util.Date());;;
//            if (altera(contatoAlt)) {
//                System.out.println("Atualizado com Sucesso");
//            } else {
//                System.out.println("Erro na atualização");
//            }
//        }
//        if (deleta(1)) {
//            System.out.println("Excluido com Sucesso");
//        } else {
//            System.out.println("Erro na exclusão");
//        }
    }
}
