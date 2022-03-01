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
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;

/**
 *
 * @author apereira
 */
public class TipoEnderecoDao {

    private Connection db;
    private PreparedStatement stm;
    private ResultSet rs;
    private String sql;

    public static List<Endereco> listEndereco;
    public static List<TipoEndereco> listTipoEndereco;

    public TipoEnderecoDao() {
        sql = "CREATE TABLE IF NOT EXISTS tipoEnderecos (idTipoEndereco INTEGER NOT NULL PRIMARY KEY, "
                + "tipoEndereco TEXT NOT NULL UNIQUE)";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            stm.execute();
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao criar a tabela tipoEnderecos!", e);
        } finally {
            ConnectionFactory.fecha(db);
        }
        if (lista().isEmpty()) {
            TipoEndereco tipo = new TipoEndereco();
            tipo.setTipoEndereco("Residência");
            insere(tipo);
            tipo = new TipoEndereco();
            tipo.setTipoEndereco("Trabalho");
            insere(tipo);
        }
    }

    public long insere(TipoEndereco objTipoEndereco) {
        sql = "INSERT INTO tipoEnderecos (tipoEndereco) VALUES (?)";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            stm.setString(1, objTipoEndereco.getTipoEndereco());
            stm.execute();
            rs = stm.getGeneratedKeys();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao adicionar novo tipo de endereço!", "insere(TipoEndereco objTipoEndereco)", e);
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

    public boolean altera(TipoEndereco objTipoEndereco) {
        sql = "UPDATE tipoEnderecos SET tipoEndereco = ? "
                + "WHERE idTipoEndereco = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, objTipoEndereco.getTipoEndereco());
                stm.setInt(2, objTipoEndereco.getIdTipoEndereco());
                if (stm.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao alterar o tipo de endereço!", "altera(TipoEndereco objTipoEndereco)", e);
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
        String sql = "DELETE FROM tipoEnderecos WHERE idTipoEndereco = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                if (stm.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao deletar o tipo de contato!", "deleta(int id)", e);
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

    public List<TipoEndereco> pesquisaID(int id) {
        sql = "SELECT * FROM tipoEnderecos WHERE idTipoEndereco = ?";
        listTipoEndereco = new ArrayList<>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                while (rs.next()) {
                    TipoEndereco tipoEndereco = new TipoEndereco();
                    tipoEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    tipoEndereco.setTipoEndereco(rs.getString("tipoEndereco"));
                    listTipoEndereco.add(tipoEndereco);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar po id!", "pesquisaID(int id)", e);
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
        return listTipoEndereco;
    }

    public List<TipoEndereco> pesquisaContato(Contato contato) {
        sql
                = "SELECT * FROM contatos c "
                + "LEFT JOIN enderecos e ON c.idContato = e.idContato "
                + "LEFT JOIN tipoEnderecos t ON t.idTipoEndereco = e.idTipoEndereco "
                + "WHERE c.idContato = ?";

        listEndereco = new ArrayList<>();
        listTipoEndereco = new ArrayList<>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, contato.getIdContato());
                rs = stm.executeQuery();
                while (rs.next()) {

                    // ENDEREÇO
                    Endereco endereco = new Endereco();
                    endereco.setIdEndereco(rs.getInt("idEndereco"));
                    endereco.setEndereco(rs.getString("endereco"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEstado(rs.getString("estado"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setPais(rs.getString("pais"));
                    endereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    endereco.setIdContato(rs.getInt("idContato"));
                    if (endereco.getIdContato() > 0) {
                        listEndereco.add(endereco);
                    }

                    // TIPO DE ENDEREÇO
                    TipoEndereco tipoEndereco = new TipoEndereco();
                    tipoEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    tipoEndereco.setTipoEndereco(rs.getString("tipoEndereco"));
                    if (tipoEndereco.getIdTipoEndereco() > 0) {
                        listTipoEndereco.add(tipoEndereco);
                    }
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao selecionar o tipo de endereço pelo o contato!", "pesquisaContato(Contato contato)", e);
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
        return listTipoEndereco;
    }

    public List<TipoEndereco> pesquisaContato(Contato contato, TipoEndereco tipoEndereco) {
        String sql
                = "SELECT * FROM contatos c "
                + "LEFT JOIN enderecos e ON e.idContato = c.idContato "
                + "WHERE c.idContato = ? AND e.idTipoEndereco = ?";

        listTipoEndereco = new ArrayList<>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, contato.getIdContato());
                stm.setInt(2, tipoEndereco.getIdTipoEndereco());
                rs = stm.executeQuery();
                if (rs.next()) {
                    TipoEndereco tipo = new TipoEndereco();
                    tipo.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    tipo.setTipoEndereco(rs.getString("tipoEndereco"));
                    listTipoEndereco.add(tipo);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar por contato!", "pesquisaContato(Contato contato, TipoEndereco tipoEndereco)", e);
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
        return listTipoEndereco;
    }

    public TipoEndereco buscaID(int id) {
        sql = "SELECT * FROM tipoEnderecos WHERE idTipoEndereco = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    TipoEndereco tipoEndereco = new TipoEndereco();
                    tipoEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    tipoEndereco.setTipoEndereco(rs.getString("tipoEndereco"));
                    return tipoEndereco;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao busar por id!", "buscaID(int id)", e);
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
        return null;
    }

    public List<TipoEndereco> lista() {
        String sql = "SELECT * FROM tipoEnderecos ORDER BY tipoEndereco";
        List<TipoEndereco> listTipoEndereco = new ArrayList<>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                TipoEndereco tipoEndereco = new TipoEndereco();
                tipoEndereco.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                tipoEndereco.setTipoEndereco(rs.getString("tipoEndereco"));
                listTipoEndereco.add(tipoEndereco);
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao listar todas os tipo de endereços!", "lista", e);
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
        return listTipoEndereco;
    }

    /*
    public void main(String[] args) {
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

    }*/
    public ListModel<String> getModel() {

        if (listTipoEndereco == null) {
            listTipoEndereco = new ArrayList<>();
        }
        return new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return listTipoEndereco.size();
            }

            @Override
            public String getElementAt(int index) {
                return listTipoEndereco.get(index).getTipoEndereco();
            }
        };
    }

    public ComboBoxModel<String> getComboBoxModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (TipoEndereco tipo : lista()) {
            model.addElement(tipo.getTipoEndereco());
        }
        return model;
    }

}
