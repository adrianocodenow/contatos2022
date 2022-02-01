package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.TipoEndereco;

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
public class TipoEnderecoDao {

    public static boolean insere(TipoEndereco objTipoEndereco) {
        String sql = "INSERT INTO tipoEnderecos(tipoEndereco) VALUES (?)";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTipoEndereco.getTipoEndereco());
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

    public static boolean altera(TipoEndereco objTipoEndereco) {
        String sql
                = "UPDATE tipoEnderecos SET tipoEndereco = ? "
                + "WHERE idTipoEndereco = ?";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTipoEndereco.getTipoEndereco());
                stmt.setInt(2, objTipoEndereco.getIdTipoEndereco());
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
        String sql
                = "DELETE FROM tipoEnderecos WHERE idTipoEndereco = ?";
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

    public static List<TipoEndereco> pesquisa(String busca) {
        String sql
                = "SELECT * FROM tipoEnderecos WHERE tipoEndereco LIKE ?";
        List<TipoEndereco> tipoEnderecos = new ArrayList<TipoEndereco>();

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
                    TipoEndereco rstTipoEndereco = new TipoEndereco();
                    rstTipoEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstTipoEndereco.setTipoEndereco(resultado.getString("tipoEndereco"));
                    tipoEnderecos.add(rstTipoEndereco);
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
        return tipoEnderecos;
    }

    public static List<TipoEndereco> pesquisaID(int id) {
        String sql
                = "SELECT * FROM tipoEnderecos WHERE idTipoEndereco = ?";
        List<TipoEndereco> tipoEnderecos = new ArrayList<TipoEndereco>();

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
                    TipoEndereco rstTipoEndereco = new TipoEndereco();
                    rstTipoEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstTipoEndereco.setTipoEndereco(resultado.getString("tipoEndereco"));
                    tipoEnderecos.add(rstTipoEndereco);
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
        return tipoEnderecos;
    }

    public static TipoEndereco buscaID(int id) {
        String sql
                = "SELECT * FROM tipoEnderecos WHERE idTipoEndereco = ?";
        TipoEndereco retorno = null;

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
                    TipoEndereco rstTipoEndereco = new TipoEndereco();
                    rstTipoEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstTipoEndereco.setTipoEndereco(resultado.getString("tipoEndereco"));
                    retorno = rstTipoEndereco;
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

    public static List<TipoEndereco> lista() {
        String sql
                = "SELECT * FROM tipoEnderecos ";
        List<TipoEndereco> tipoEnderecos = new ArrayList<TipoEndereco>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    TipoEndereco rstTipoEndereco = new TipoEndereco();
                    rstTipoEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstTipoEndereco.setTipoEndereco(resultado.getString("tipoEndereco"));
                    tipoEnderecos.add(rstTipoEndereco);
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
        return tipoEnderecos;
    }



    public static void main(String[] args) {
        TipoEndereco tipoEndereco = new TipoEndereco();
        tipoEndereco.setTipoEndereco("Casa da Mãe Joana!!!");
        if (insere(tipoEndereco)) {
            System.out.println("Tipo Endereço Incluído com Sucesso");
        } else {
            System.out.println("Erro na Inclusão do Tipo Endereço");
        }
        for (TipoEndereco retorno : pesquisaID(13)) {
            TipoEndereco tipoEndereco1 = retorno;
            tipoEndereco1.setTipoEndereco("Onde Judas perdeu as botas!!!");
            if (altera(tipoEndereco1)) {
                System.out.println("Tipo Endereço Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Tipo Endereço");
            }
        }
        TipoEndereco tipoEndereco2 = buscaID(14);
        if (tipoEndereco2 != null) {
            tipoEndereco2.setTipoEndereco("Todos os caminhos leval a Roma");
            if (altera(tipoEndereco2)) {
                System.out.println("Tipo Endereço Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Tipo Endereço");
            }
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Pesquisa Tipo Endereço");
        System.out.println("----------------------------------------------------");
        for (TipoEndereco retorno : pesquisa("po")) {
            System.out.println("idTipoEndereço: " + retorno.getIdTipoEndereco());
            System.out.println("TipoEndereço  : " + retorno.getTipoEndereco());
            System.out.println("----------------------------------------------------");        
        }
        if (deleta(13)) {
            System.out.println("Endereço Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Endereço");
        }

    }

}
