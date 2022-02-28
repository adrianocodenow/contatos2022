package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
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
public class EnderecoDao {

    public static List<Endereco> listEndereco;

    public static boolean insere(Endereco objEndereco) {
        String sql
                = "INSERT INTO "
                + "enderecos(endereco, bairro, cidade, estado, "
                + "cep, pais, idTipoEndereco, idContato)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objEndereco.getEndereco());
                stmt.setString(2, objEndereco.getBairro());
                stmt.setString(3, objEndereco.getCidade());
                stmt.setString(4, objEndereco.getEstado());
                stmt.setString(5, objEndereco.getCep());
                stmt.setString(6, objEndereco.getPais());
                stmt.setInt(7, objEndereco.getIdTipoEndereco());
                stmt.setInt(8, objEndereco.getIdContato());
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

    public static boolean altera(Endereco objEndereco) {
        String sql
                = "UPDATE enderecos SET "
                + "endereco = ? , bairro = ? , cidade = ?, estado = ?, "
                + "cep = ? , pais = ? , idTipoEndereco = ?, idContato = ? "
                + "WHERE idEndereco = ? ";

        Connection db = null;
        PreparedStatement stmt = null;
        Boolean retorno = true;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, objEndereco.getEndereco());
                stmt.setString(2, objEndereco.getBairro());
                stmt.setString(3, objEndereco.getCidade());
                stmt.setString(4, objEndereco.getEstado());
                stmt.setString(5, objEndereco.getCep());
                stmt.setString(6, objEndereco.getPais());
                stmt.setInt(7, objEndereco.getIdTipoEndereco());
                stmt.setInt(8, objEndereco.getIdContato());
                stmt.setInt(9, objEndereco.getIdEndereco());
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
        String sql = "DELETE FROM enderecos WHERE idEndereco = ?";
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

    public static boolean deletaIDContato(int id) {
        String sql = "DELETE FROM enderecos WHERE idContato = ?";
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

    public static List<Endereco> pesquisa(String busca) {
        String sql
                = "SELECT * FROM enderecos "
                + "WHERE endereco LIKE ? OR bairro LIKE ? "
                + "OR cidade LIKE ? OR estado LIKE ? OR cep LIKE ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setString(1, "%" + busca + "%");
                stmt.setString(2, "%" + busca + "%");
                stmt.setString(3, "%" + busca + "%");
                stmt.setString(4, "%" + busca + "%");
                stmt.setString(5, "%" + busca + "%");
                resultado = stmt.executeQuery();
                while (resultado.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(resultado.getInt("idEndereco"));
                    rstEndereco.setEndereco(resultado.getString("endereco"));
                    rstEndereco.setBairro(resultado.getString("bairro"));
                    rstEndereco.setCidade(resultado.getString("cidade"));
                    rstEndereco.setEstado(resultado.getString("estado"));
                    rstEndereco.setCep(resultado.getString("cep"));
                    rstEndereco.setPais(resultado.getString("pais"));
                    rstEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(resultado.getInt("idContato"));
                    enderecos.add(rstEndereco);
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
        return enderecos;
    }

    public static List<Endereco> pesquisaID(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idEndereco = ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

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
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(resultado.getInt("idEndereco"));
                    rstEndereco.setEndereco(resultado.getString("endereco"));
                    rstEndereco.setBairro(resultado.getString("bairro"));
                    rstEndereco.setCidade(resultado.getString("cidade"));
                    rstEndereco.setEstado(resultado.getString("estado"));
                    rstEndereco.setCep(resultado.getString("cep"));
                    rstEndereco.setPais(resultado.getString("pais"));
                    rstEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(resultado.getInt("idContato"));
                    enderecos.add(rstEndereco);
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
        return enderecos;
    }

    public static Endereco buscaID(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idEndereco = ?";
        Endereco retorno = null;
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
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(resultado.getInt("idEndereco"));
                    rstEndereco.setEndereco(resultado.getString("endereco"));
                    rstEndereco.setBairro(resultado.getString("bairro"));
                    rstEndereco.setCidade(resultado.getString("cidade"));
                    rstEndereco.setEstado(resultado.getString("estado"));
                    rstEndereco.setCep(resultado.getString("cep"));
                    rstEndereco.setPais(resultado.getString("pais"));
                    rstEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(resultado.getInt("idContato"));
                    retorno = rstEndereco;
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

    public static boolean buscaIDTipoEndereco(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idTipoEndereco = ?";
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
        return retorno;
    }

    public static List<Endereco> buscaContato(Contato contato, TipoEndereco tipoEndereco) {
        listEndereco = new ArrayList<>();
        String sql
                = "SELECT * FROM contatos c "
                + "LEFT JOIN enderecos e ON e.idContato = c.idContato "
                + "LEFT JOIN tipoEnderecos t ON t.idTipoEndereco = e.idTipoEndereco "
                + "WHERE c.idContato = ? AND e.idTipoEndereco = ?";
        Connection db = null;
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stmt = db.prepareStatement(sql);
                stmt.setInt(1, contato.getIdContato());
                stmt.setInt(2, tipoEndereco.getIdTipoEndereco());
                resultado = stmt.executeQuery();
                if (resultado.next()) {
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(resultado.getInt("idEndereco"));
                    rstEndereco.setEndereco(resultado.getString("endereco"));
                    rstEndereco.setBairro(resultado.getString("bairro"));
                    rstEndereco.setCidade(resultado.getString("cidade"));
                    rstEndereco.setEstado(resultado.getString("estado"));
                    rstEndereco.setCep(resultado.getString("cep"));
                    rstEndereco.setPais(resultado.getString("pais"));
                    rstEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(resultado.getInt("idContato"));
                    if (rstEndereco.getIdEndereco() > 0) {
                        listEndereco.add(rstEndereco);
                    }
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
        return listEndereco;
    }

    public static List<Endereco> listaIDContato(int id) {
        String sql
                = "SELECT * FROM enderecos WHERE idContato = ?";
        List<Endereco> enderecos = new ArrayList<Endereco>();

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
                    Endereco rstEndereco = new Endereco();
                    rstEndereco.setIdEndereco(resultado.getInt("idEndereco"));
                    rstEndereco.setEndereco(resultado.getString("endereco"));
                    rstEndereco.setBairro(resultado.getString("bairro"));
                    rstEndereco.setCidade(resultado.getString("cidade"));
                    rstEndereco.setEstado(resultado.getString("estado"));
                    rstEndereco.setCep(resultado.getString("cep"));
                    rstEndereco.setPais(resultado.getString("pais"));
                    rstEndereco.setIdTipoEndereco(resultado.getInt("idTipoEndereco"));
                    rstEndereco.setIdContato(resultado.getInt("idContato"));
                    enderecos.add(rstEndereco);
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
        return enderecos;
    }

    public static void main(String[] args) {
        Endereco endereco = new Endereco();
        endereco.setEndereco("Alamenda Santos 1100");
        endereco.setBairro("Cerqueira Cesar");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01010-020");
        endereco.setPais("Brasil");
        endereco.setIdTipoEndereco(9);
        endereco.setIdContato(12);
        if (insere(endereco)) {
            System.out.println("Endereço Incluído com Sucesso");
        } else {
            System.out.println("Erro na Inclusão do Endereço");
        }
        for (Endereco retorno : pesquisaID(2)) {

            Endereco endereco1 = retorno;
            endereco1.setEndereco("Alamenda Santos 1100");
            endereco1.setBairro("Cerqueira Cesar");
            endereco1.setCep("01010-020");
            endereco1.setIdTipoEndereco(9);
            if (altera(endereco1)) {
                System.out.println("Endereço Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Endereço");
            }
        }

        Endereco endereco2 = buscaID(10);
        if (endereco2 != null) {
            endereco2.setEndereco("Rua Bom Pastor 20");
            endereco2.setBairro("Centro");
            endereco2.setCep("01000-020");
            endereco2.setIdTipoEndereco(2);
            if (altera(endereco2)) {
                System.out.println("Endereço Alterado com Sucesso");
            } else {
                System.out.println("Erro na Alteração do Endereço");
            }

        } else {
            System.out.println("Endereço não disponível!");
        }
        System.out.println("------------------------------------------------");
        System.out.println("Lista IDContato");
        System.out.println("------------------------------------------------");
        for (Endereco retorno : listaIDContato(1)) {
            System.out.println("idEndereco    : " + retorno.getIdEndereco());
            System.out.println("Endereco      : " + retorno.getEndereco());
            System.out.println("Bairro        : " + retorno.getBairro());
            System.out.println("Cidade        : " + retorno.getCidade());
            System.out.println("Estado        : " + retorno.getEstado());
            System.out.println("CEP           : " + retorno.getCep());
            System.out.println("País          : " + retorno.getPais());
            System.out.println("idTipoEndereco: " + retorno.getIdTipoEndereco());
            System.out.println("idContato     : " + retorno.getIdContato());
            System.out.println("------------------------------------------------");
        }
        System.out.println("------------------------------------------------");
        System.out.println("Pesquisa Endereco");
        System.out.println("------------------------------------------------");
        for (Endereco retorno : pesquisa("tokyo")) {
            System.out.println("idEndereco    : " + retorno.getIdEndereco());
            System.out.println("Endereco      : " + retorno.getEndereco());
            System.out.println("Bairro        : " + retorno.getBairro());
            System.out.println("Cidade        : " + retorno.getCidade());
            System.out.println("Estado        : " + retorno.getEstado());
            System.out.println("CEP           : " + retorno.getCep());
            System.out.println("País          : " + retorno.getPais());
            System.out.println("idTipoEndereco: " + retorno.getIdTipoEndereco());
            System.out.println("idContato     : " + retorno.getIdContato());
            System.out.println("------------------------------------------------");
        }
        if (deleta(13)) {
            System.out.println("Endereço Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Endereço");
        }
        if (deletaIDContato(12)) {
            System.out.println("Endereço Excluído com Sucesso");
        } else {
            System.out.println("Erro na Exclusão do Endereço");
        }

    }

}
