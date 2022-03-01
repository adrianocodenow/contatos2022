package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
import com.adrianocodenow.contatos2022.model.TipoEndereco;
import com.adrianocodenow.contatos2022.utils.Msg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apereira
 */
public class EnderecoDao {

    private Connection db;
    private PreparedStatement stm;
    private ResultSet rs;
    private String sql;
    
    public static List<Endereco> listEndereco;

    public EnderecoDao() {
        sql = "CREATE TABLE IF NOT EXISTS enderecos (idEndereco INTEGER NOT NULL PRIMARY KEY, "
                + "endereco TEXT, "
                + "bairro TEXT, "
                + "cidade TEXT, "
                + "estado TEXT, "
                + "cep TEXT, "
                + "pais TEXT, "
                + "idTipoEndereco INTEGER, "
                + "idContato INTEGER,"
                + "FOREIGN KEY (idContato) "
                + "REFERENCES contatos (idContato) ON DELETE CASCADE )";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm =db.prepareStatement(sql);
            stm.execute();
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao criar a tabela endereços!", e);
        }finally{
            ConnectionFactory.fecha(db);
        }
    }
    
    

    public long insere(Endereco objEndereco) {
        sql
                = "INSERT INTO "
                + "enderecos (endereco, bairro, cidade, estado, "
                + "cep, pais, idTipoEndereco, idContato) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, objEndereco.getEndereco());
                stm.setString(2, objEndereco.getBairro());
                stm.setString(3, objEndereco.getCidade());
                stm.setString(4, objEndereco.getEstado());
                stm.setString(5, objEndereco.getCep());
                stm.setString(6, objEndereco.getPais());
                stm.setInt(7, objEndereco.getIdTipoEndereco());
                stm.setInt(8, objEndereco.getIdContato());
                stm.executeUpdate();
                rs = stm.getGeneratedKeys();
                if(rs.next()){
                    return rs.getLong(1);
                }                
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao adicionar novo endereço!", "insere(Endereco objEndereco)", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return 0;
    }

    public boolean altera(Endereco objEndereco) {
        sql
                = "UPDATE enderecos SET "
                + "endereco = ? , bairro = ? , cidade = ?, estado = ?, "
                + "cep = ? , pais = ? , idTipoEndereco = ?, idContato = ? "
                + "WHERE idEndereco = ? ";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, objEndereco.getEndereco());
                stm.setString(2, objEndereco.getBairro());
                stm.setString(3, objEndereco.getCidade());
                stm.setString(4, objEndereco.getEstado());
                stm.setString(5, objEndereco.getCep());
                stm.setString(6, objEndereco.getPais());
                stm.setInt(7, objEndereco.getIdTipoEndereco());
                stm.setInt(8, objEndereco.getIdContato());
                stm.setInt(9, objEndereco.getIdEndereco());
                if( stm.executeUpdate()>0){
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao alterar o endereço!", "altera(Endereco objEndereco)", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }

    public boolean deleta(int id) {
        sql = "DELETE FROM enderecos WHERE idEndereco = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                if(stm.executeUpdate()>0){
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao deletar endereço!", "deleta(int id)", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }

    public boolean deletaIDContato(int id) {
        sql = "DELETE FROM enderecos WHERE idContato = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                if(stm.executeUpdate()>0){
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao deletar endereço pelo o contato!", "deletaIDContato(int id)", e);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }

    public static List<Endereco> pesquisa(String busca) {
        String sql
                = "SELECT * FROM enderecos "
                + "WHERE endereco LIKE ? OR bairro LIKE ? "
                + "OR cidade LIKE ? OR estado LIKE ? OR cep LIKE ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

        Connection db = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, "%" + busca + "%");
                stm.setString(2, "%" + busca + "%");
                stm.setString(3, "%" + busca + "%");
                stm.setString(4, "%" + busca + "%");
                stm.setString(5, "%" + busca + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(rs.getInt("idEndereco"));
                    rstEndereco.setEndereco(rs.getString("endereco"));
                    rstEndereco.setBairro(rs.getString("bairro"));
                    rstEndereco.setCidade(rs.getString("cidade"));
                    rstEndereco.setEstado(rs.getString("estado"));
                    rstEndereco.setCep(rs.getString("cep"));
                    rstEndereco.setPais(rs.getString("pais"));
                    rstEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(rs.getInt("idContato"));
                    enderecos.add(rstEndereco);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return enderecos;
    }

    public static List<Endereco> pesquisaID(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idEndereco = ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

        Connection db = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(rs.getInt("idEndereco"));
                    rstEndereco.setEndereco(rs.getString("endereco"));
                    rstEndereco.setBairro(rs.getString("bairro"));
                    rstEndereco.setCidade(rs.getString("cidade"));
                    rstEndereco.setEstado(rs.getString("estado"));
                    rstEndereco.setCep(rs.getString("cep"));
                    rstEndereco.setPais(rs.getString("pais"));
                    rstEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(rs.getInt("idContato"));
                    enderecos.add(rstEndereco);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return enderecos;
    }

    public static Endereco buscaID(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idEndereco = ?";
        Endereco retorno = null;
        Connection db = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(rs.getInt("idEndereco"));
                    rstEndereco.setEndereco(rs.getString("endereco"));
                    rstEndereco.setBairro(rs.getString("bairro"));
                    rstEndereco.setCidade(rs.getString("cidade"));
                    rstEndereco.setEstado(rs.getString("estado"));
                    rstEndereco.setCep(rs.getString("cep"));
                    rstEndereco.setPais(rs.getString("pais"));
                    rstEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(rs.getInt("idContato"));
                    retorno = rstEndereco;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return retorno;
    }

    public static boolean buscaIDTipoEndereco(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idTipoEndereco = ?";
        boolean retorno = false;
        Connection db = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    retorno = true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return retorno;
    }

    public List<Endereco> buscaContato(Contato contato, TipoEndereco tipoEndereco) {
        listEndereco = new ArrayList<>();
        sql
                = "SELECT e.idEndereco, e.endereco, e.bairro, e.cidade, e.estado, e.cep, e.pais, e.idTipoEndereco, e.idContato FROM contatos c "
                + "LEFT JOIN enderecos e ON e.idContato = c.idContato "
                + "LEFT JOIN tipoEnderecos t ON t.idTipoEndereco = e.idTipoEndereco "
                + "WHERE c.idContato = ? AND e.idTipoEndereco = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, contato.getIdContato());
                stm.setInt(2, tipoEndereco.getIdTipoEndereco());
                rs = stm.executeQuery();
                while (rs.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(rs.getInt("idEndereco"));
                    rstEndereco.setEndereco(rs.getString("endereco"));
                    rstEndereco.setBairro(rs.getString("bairro"));
                    rstEndereco.setCidade(rs.getString("cidade"));
                    rstEndereco.setEstado(rs.getString("estado"));
                    rstEndereco.setCep(rs.getString("cep"));
                    rstEndereco.setPais(rs.getString("pais"));
                    rstEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(rs.getInt("idContato"));
                    if (rstEndereco.getIdEndereco() > 0) {
                        listEndereco.add(rstEndereco);
                    }
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao buscar o endereço pelo id do contato", "buscaContato(Contato contato, TipoEndereco tipoEndereco)", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return listEndereco;
    }

    public static List<Endereco> listaIDContato(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idContato = ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

        Connection db = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(rs.getInt("idEndereco"));
                    rstEndereco.setEndereco(rs.getString("endereco"));
                    rstEndereco.setBairro(rs.getString("bairro"));
                    rstEndereco.setCidade(rs.getString("cidade"));
                    rstEndereco.setEstado(rs.getString("estado"));
                    rstEndereco.setCep(rs.getString("cep"));
                    rstEndereco.setPais(rs.getString("pais"));
                    rstEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(rs.getInt("idContato"));
                    enderecos.add(rstEndereco);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                ConnectionFactory.fecha(db);
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return enderecos;
    }   

}
