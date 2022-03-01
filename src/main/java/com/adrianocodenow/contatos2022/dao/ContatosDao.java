package com.adrianocodenow.contatos2022.dao;

import com.adrianocodenow.contatos2022.controller.Config;
import com.adrianocodenow.contatos2022.controller.StrNormalize;
import com.adrianocodenow.contatos2022.factory.ConnectionFactory;
import com.adrianocodenow.contatos2022.model.Contato;
import com.adrianocodenow.contatos2022.model.Endereco;
import com.adrianocodenow.contatos2022.model.Telefone;
import com.adrianocodenow.contatos2022.model.TipoEndereco;
import com.adrianocodenow.contatos2022.utils.Msg;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author apereira
 */
public class ContatosDao {

    private Connection db;
    private PreparedStatement stm;
    private ResultSet rs;
    private String sql;

    private List<Contato> listContato;
    private List<Endereco> listEndereco;
    private List<Telefone> listTelefone;

    public ContatosDao() {
        sql = "CREATE TABLE IF NOT EXISTS contatos (idContato INTEGER NOT NULL PRIMARY KEY, "
                + "nome TEXT, "
                + "sobrenome TEXT, "
                + "nomeSobrenomeFonetico TEXT, "
                + "dataCriacao DATE, "
                + "dataUltimaAtualizacao DATE, "
                + "ativo BOOLEAN )";
        try{
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            stm.execute();
        }catch(SQLException e){
            Msg.ServerErro("Erro ao criar a tabela contatos!", e);
        }finally{
            ConnectionFactory.fecha(db);
        }
    }
    
    

    public int insere(Contato objContato) {
        sql
                = "INSERT INTO "
                + "contatos(nome, sobrenome, nomeSobrenomeFonetico, "
                + "dataCriacao, dataUltimaAtualizacao, ativo)"
                + "VALUES(?, ?, ?, ?, ?, ?)";

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            stm.setString(1, objContato.getNome());
            stm.setString(2, objContato.getSobrenome());
            stm.setString(3,
                    StrNormalize.removeAcentos1(
                            objContato.getNome() + " " + objContato.getSobrenome()
                    ).toLowerCase()
            );
            stm.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
            stm.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));
            stm.setBoolean(6, objContato.isAtivo());
            return stm.executeUpdate();
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao inserir novo contato!!!", "insere(Contato objContato)", e);
        } finally {
            ConnectionFactory.fecha(db);
        }
        return 0;
    }

    public boolean altera(Contato objContato) {
        sql = "UPDATE contatos SET  "
                + "nome = ? , sobrenome = ? , nomeSobrenomeFonetico = ? , "
                + "dataUltimaAtualizacao = ? , ativo = ?"
                + "WHERE idContato = ?";

        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, objContato.getNome());
                stm.setString(2, objContato.getSobrenome());
                stm.setString(3, objContato.getNome() + " " + objContato.getSobrenome());
                stm.setDate(4, new Date(objContato.getDataUltimaAtualizacao().getTime()));
                stm.setBoolean(5, objContato.isAtivo());
                stm.setInt(6, objContato.getIdContato());
                if (stm.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao alterar o contato!", "altera(Contato objContato)", e);
        } finally {
            ConnectionFactory.fecha(db);
        }
        return false;
    }

    public boolean deleta(int id) {
        sql = "DELETE FROM contatos WHERE idContato = ?";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            stm.setInt(1, id);
            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao apagar o contato!", "deleta(int id)", e);
        } finally {
            ConnectionFactory.fecha(db);
        }
        return false;
    }

    public List<Contato> lista() {

        listContato = new ArrayList<>();
        sql = "SELECT * FROM contatos "
                + "ORDER BY nomeSobrenomeFonetico ASC";
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            stm = db.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Contato rstContato = new Contato();
                rstContato.setIdContato(rs.getInt("idContato"));
                rstContato.setNome(rs.getString("nome"));
                rstContato.setSobrenome(rs.getString("sobrenome"));
                rstContato.setNomeSobrenomeFonetico(rs.getString("NomeSobrenomeFonetico"));
                rstContato.setDataCriacao(rs.getTimestamp("dataCriacao"));
                rstContato.setDataUltimaAtualizacao(rs.getTimestamp("dataUltimaAtualizacao"));
                rstContato.setAtivo(rs.getBoolean("ativo"));
                listContato.add(rstContato);
            }

        } catch (SQLException e) {
            Msg.ServerErro("Erro ao lista todos os contatos!", "lista()", e);
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
        return listContato;
    }

    public List<Contato> pesquisa(String busca) {
        sql
                = "SELECT * FROM contatos "
                + "WHERE nome LIKE ? OR sobrenome LIKE ?"
                + "ORDER BY nomeSobrenomeFonetico ASC";
        listContato = new ArrayList<>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, "%" + busca + "%");
                stm.setString(2, "%" + busca + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(rs.getInt("idContato"));
                    rstContato.setNome(rs.getString("nome"));
                    rstContato.setSobrenome(rs.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(rs.getString("NomeSobrenomeFonetico"));
                    rstContato.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    rstContato.setDataUltimaAtualizacao(rs.getTimestamp("dataUltimaAtualizacao"));
                    rstContato.setAtivo(rs.getBoolean("ativo"));
                    listContato.add(rstContato);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar por nome ou sobrenome!", "pesquisa(String busca)", e);
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
        return listContato;
    }

    public List<Contato> pesquisaTipoEnderco(Contato contato, TipoEndereco tipoEndereco) {

        listContato = new ArrayList<>();
        listEndereco = new ArrayList<>();

        sql = "SELECT * FROM contatos c "
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

                    // CONTATO
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(rs.getInt("idContato"));
                    rstContato.setNome(rs.getString("nome"));
                    rstContato.setSobrenome(rs.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(rs.getString("NomeSobrenomeFonetico"));
                    rstContato.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    rstContato.setDataUltimaAtualizacao(rs.getTimestamp("dataUltimaAtualizacao"));
                    rstContato.setAtivo(rs.getBoolean("ativo"));

                    // ENDEREÇOS                    
                    rstContato.setIdEndereco(rs.getInt("idEndereco"));
                    rstContato.setEndereco(rs.getString("endereco"));
                    rstContato.setBairro(rs.getString("bairro"));
                    rstContato.setCidade(rs.getString("cidade"));
                    rstContato.setEstado(rs.getString("estado"));
                    rstContato.setCep(rs.getString("cep"));
                    rstContato.setPais(rs.getString("pais"));
                    rstContato.setIdTipoEndereco(rs.getInt("idTipoEndereco"));
                    listEndereco.add(rstContato);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar por tipo de endereço!", "pesquisaTipoEnderco", e);
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
        return listContato;
    }

    public List<Contato> pesquisaID(int id) {
        String sql
                = "SELECT * FROM contatos WHERE idContato = ?";
        listContato = new ArrayList<Contato>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(rs.getInt("idContato"));
                    rstContato.setNome(rs.getString("nome"));
                    rstContato.setSobrenome(rs.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(rs.getString("NomeSobrenomeFonetico"));
                    rstContato.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    rstContato.setDataUltimaAtualizacao(rs.getTimestamp("dataUltimaAtualizacao"));
                    rstContato.setAtivo(rs.getBoolean("ativo"));
                    listContato.add(rstContato);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar por ID", "pesquisaID(int id)", e);
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
        return listContato;
    }

    public List<Contato> pesquisaFonetica(String busca) {
        sql
                = "SELECT * FROM contatos WHERE NomeSobrenomeFonetico = ?"
                + "ORDER BY nomeSobrenomeFonetico ASC";
        listContato = new ArrayList<Contato>();
        try {
            db = ConnectionFactory.abre(Config.BANCO_DE_DADOS);
            if (db != null) {
                stm = db.prepareStatement(sql);
                stm.setString(1, busca);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Contato rstContato = new Contato();
                    rstContato.setIdContato(rs.getInt("idContato"));
                    rstContato.setNome(rs.getString("nome"));
                    rstContato.setSobrenome(rs.getString("sobrenome"));
                    rstContato.setNomeSobrenomeFonetico(rs.getString("NomeSobrenomeFonetico"));
                    rstContato.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    rstContato.setDataUltimaAtualizacao(rs.getTimestamp("dataUltimaAtualizacao"));
                    rstContato.setAtivo(rs.getBoolean("ativo"));
                    listContato.add(rstContato);
                }
            }
        } catch (SQLException e) {
            Msg.ServerErro("Erro ao pesquisar por fonetica!", "pesquisaFonetica", e);
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
        return listContato;
    }

    public ListModel<String> getModel() {
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

    public void main(String[] args) {

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
