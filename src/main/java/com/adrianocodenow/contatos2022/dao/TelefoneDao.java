package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Telefone;

import java.sql.Connection;
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
public class TelefoneDao {

    public static boolean insere(Telefone objTelefone) {
        String sql
                = "INSERT INTO "
                + "telefones(telefone, idTipoTelefone, idContato) "
                + "VALUES(?, ?, ?)";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTelefone.getTelefone());
                stmt.setInt(2, objTelefone.getIdTipoTelefone());
                stmt.setInt(3, objTelefone.getIdContato());
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
        return true;
    }

    public static boolean altera(Telefone objTelefone) {
        String sql
                = "UPDATE telefones SET "
                + "telefone = ? , idTipoTelefone =? , idContato = ? "
                + "WHERE idTelefone = ? ";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTelefone.getTelefone());
                stmt.setInt(2, objTelefone.getIdTipoTelefone());
                stmt.setInt(3, objTelefone.getIdContato());
                stmt.setInt(4, objTelefone.getIdTelefone());
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
        return true;
    }

    public static boolean deleta(int id) {
        String sql
                = "DELETE FROM telefones WHERE idTelefone = ? ";
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
        return true;
    }

    public static boolean deletaIDContato(int id) {
        String sql
                = "DELETE FROM telefones WHERE idContato = ? ";
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
        return true;
    }

    public static List<Telefone> pesquisa(String busca) {

        String sql
                = "SELECT * FROM telefones WHERE telefone LIKE ? ";
        List<Telefone> telefones = new ArrayList<Telefone>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, "%" + busca + "%");
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Telefone rstTelefone = new Telefone();
                    rstTelefone.setIdTelefone(resultado.getInt("idTelefone"));
                    rstTelefone.setTelefone(resultado.getString("telefone"));
                    rstTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTelefone.setIdContato(resultado.getInt("idContato"));
                    telefones.add(rstTelefone);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return telefones;
    }

    public static List<Telefone> pesquisaID(int id) {

        String sql
                = "SELECT * FROM telefones WHERE idTelefone = ? ";
        List<Telefone> telefones = new ArrayList<Telefone>();

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
                    Telefone rstTelefone = new Telefone();
                    rstTelefone.setIdTelefone(resultado.getInt("idTelefone"));
                    rstTelefone.setTelefone(resultado.getString("telefone"));
                    rstTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTelefone.setIdContato(resultado.getInt("idContato"));
                    telefones.add(rstTelefone);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return telefones;
    }

    public static Telefone buscaID(int id) {

        String sql
                = "SELECT * FROM telefones WHERE idTelefone = ? ";
        Telefone retorno = null;

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setInt(1, id);
                resultado = stmt.executeQuery();
                if (resultado.next()) {
                    Telefone rstTelefone = new Telefone();
                    rstTelefone.setIdTelefone(resultado.getInt("idTelefone"));
                    rstTelefone.setTelefone(resultado.getString("telefone"));
                    rstTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTelefone.setIdContato(resultado.getInt("idContato"));
                    retorno = rstTelefone;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean buscaIDTipoTipo(int id) {

        String sql
                = "SELECT * FROM telefones WHERE idTipoTelefone = ? ";
        boolean retorno = false;

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setInt(1, id);
                resultado = stmt.executeQuery();
                if (resultado.next()) {
                    retorno = true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<Telefone> listaIDContato(int id) {

        String sql
                = "SELECT * FROM telefones WHERE idContato = ? ";
        List<Telefone> telefones = new ArrayList<Telefone>();

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
                    Telefone rstTelefone = new Telefone();
                    rstTelefone.setIdTelefone(resultado.getInt("idTelefone"));
                    rstTelefone.setTelefone(resultado.getString("telefone"));
                    rstTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTelefone.setIdContato(resultado.getInt("idContato"));
                    telefones.add(rstTelefone);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContatosDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return telefones;
    }

    public static void main(String[] args) {
        Telefone telefone = new Telefone();
        telefone.setTelefone("+55 (13) 4441 4000 ");
        telefone.setIdTipoTelefone(2);
        telefone.setIdContato(12);
        if (insere(telefone)) {
            System.out.println("Telefone Incluído com Sucesso");
        } else {
            System.out.println("Erro na Inclusão do Telefone");
        }
        for (Telefone retorno : pesquisaID(12)) {
            Telefone telefone1 = retorno;
            telefone1.setTelefone("+55 11 3758 7777");
            if (altera(telefone1)) {
                System.out.println("Telefone Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Telefone");
            }
        }
        Telefone telefone2 = buscaID(2);
        if (telefone2 != null) {
            telefone2.setTelefone("+81 70 7777 7070");
            if (altera(telefone2)) {
                System.out.println("Telefone Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Telefone");
            }
        } else {
            System.out.println("Telefone não disponível");
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Lista ICContato");
        System.out.println("---------------------------------------------------");
        for (Telefone retorno : listaIDContato(8)) {
            System.out.println("idTelefone    : " + retorno.getIdTelefone());
            System.out.println("Telefone      : " + retorno.getTelefone());
            System.out.println("idTipoTelefone: " + retorno.getIdTipoTelefone());
            System.out.println("idContato     : " + retorno.getIdContato());
            System.out.println("---------------------------------------------------");
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Pesquisa Telefone");
        System.out.println("---------------------------------------------------");
        for (Telefone retorno : pesquisa("7777")) {
            System.out.println("idTelefone    : " + retorno.getIdTelefone());
            System.out.println("Telefone      : " + retorno.getTelefone());
            System.out.println("idTipoTelefone: " + retorno.getIdTipoTelefone());
            System.out.println("idContato     : " + retorno.getIdContato());
            System.out.println("---------------------------------------------------");
        }
        if (deleta(13)) {
            System.out.println("Telefone Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Telefone");
        }
        if (deletaIDContato(12)) {
            System.out.println("Telefone Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Telefone");
        }

    }
}
