package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.TipoTelefone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author apereira
 */
public class TipoTelefoneDao extends DefaultListModel<String>{    
    
    private static List<TipoTelefone> listTipoTelefone = new ArrayList<>();
    private TipoTelefone tipoTelefone;

    public TipoTelefoneDao() {        
    }
    
    @Override
    public int getSize() {
        return listTipoTelefone.size();
    }

     @Override
    public String get(int index) {
        return listTipoTelefone.get(index).getTipoTelefone();
    }

    

    public static boolean insere(TipoTelefone objTipoTelefone) {
        String sql = "INSERT INTO tipoTelefones(tipoTelefone) VALUES(?)";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTipoTelefone.getTipoTelefone());
                stmt.execute();
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
        return retorno;
    }

    public static boolean altera(TipoTelefone objTipoTelefone) {
        String sql
                = "UPDATE tipoTelefones set tipoTelefone = ? "
                + "WHERE idTipoTelefone = ?";
        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objTipoTelefone.getTipoTelefone());
                stmt.setInt(2, objTipoTelefone.getIdTipoTelefone());
                stmt.execute();
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
        return retorno;
    }

    public static boolean deleta(int id) {
        String sql
                = "DELETE FROM tipoTelefones WHERE idTipoTelefone = ?";
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

    public List<TipoTelefone> pesquisa(String busca) {
        String sql
                = "SELECT * FROM tipoTelefones WHERE tipoTelefone LIKE ?";
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
                    TipoTelefone rstTipoTelefone = new TipoTelefone();
                    rstTipoTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTipoTelefone.setTipoTelefone(resultado.getString("tipoTelefone"));
                    listTipoTelefone.add(rstTipoTelefone);
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
        return listTipoTelefone;
    }

    public List<TipoTelefone> pesquisaID(int id) {
        String sql
                = "SELECT * FROM tipoTelefones WHERE idTipoTelefone = ?";
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
                    TipoTelefone rstTipoTelefone = new TipoTelefone();
                    rstTipoTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTipoTelefone.setTipoTelefone(resultado.getString("tipoTelefone"));
                    listTipoTelefone.add(rstTipoTelefone);
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
        return listTipoTelefone;
    }

    public static List<TipoTelefone> lista() {
        listTipoTelefone = new ArrayList<>();
        String sql
                = "SELECT * FROM tipoTelefones";

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    TipoTelefone rstTipoTelefone = new TipoTelefone();
                    rstTipoTelefone.setIdTipoTelefone(resultado.getInt("idTipoTelefone"));
                    rstTipoTelefone.setTipoTelefone(resultado.getString("tipoTelefone"));
                    listTipoTelefone.add(rstTipoTelefone);
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
        return listTipoTelefone;
    }

    /*
    public void main(String[] args) {
        TipoTelefone tipoTelefone = new TipoTelefone();
        tipoTelefone.setTipoTelefone("Star Tac");
        if (insere(tipoTelefone)) {
            System.out.println("Tipo Telefone Incluído com Sucesso");
        } else {
            System.out.println("Erro na Inclusão do Tipo Telefone");
        }
        for (TipoTelefone retorno : buscaID(14)) {
            TipoTelefone tipoTelefone1 = retorno;
            tipoTelefone1.setTipoTelefone("Motorola");
            if (altera(tipoTelefone1)) {
                System.out.println("Tipo Telefone Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteracoa do Tipo Telefone");
            }
        }
        TipoTelefone tipoTelefone2 = buscaID(15);
        if (tipoTelefone2 != null) {
            tipoTelefone2.setTipoTelefone("Samsung");
            if (altera(tipoTelefone2)) {
                System.out.println("Tipo Telefone Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteracoa do Tipo Telefone");
            }
        } else {
            System.out.println("Tipo Telefone não disponível");
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Pesquisa Tipo Telefone");
        System.out.println("----------------------------------------------------");
        for (TipoTelefone retorno : pesquisa("la")) {
            System.out.println("idTipoTelefone: " + retorno.getIdTipoTelefone());
            System.out.println("TipoTelefone  : " + retorno.getTipoTelefone());
            System.out.println("----------------------------------------------------");
        }
        if (deleta(13)) {
            System.out.println("Tipo Telefone Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Tipo Telefone");
        }

    }*/    

   
}
