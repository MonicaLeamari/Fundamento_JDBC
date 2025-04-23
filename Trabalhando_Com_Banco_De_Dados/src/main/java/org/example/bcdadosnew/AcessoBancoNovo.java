package org.example.bcdadosnew;

import java.sql.*;

public class AcessoBancoNovo {

    private Connection connection;

    public void AcessoBancoNovo() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/banconovo?useSSL=false", "root", "Monica28");
            System.out.println("Conexão Estabelecida!");

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Erro Driver Jdbc!" + cnfe.getLocalizedMessage());
        } catch (SQLException sqle) {
            System.out.println("Conexão falhou!");
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    public void criarTabela() throws SQLException {
        Statement stat = (Statement) connection.createStatement();
        stat.executeUpdate("create table dinossauro (\n" +
                "codigo int primary key auto_increment,\n" +
                "genero varchar(50),\n" +
                "especie varchar(50)\n" +
                ");");
        DatabaseMetaData dbm = (DatabaseMetaData) connection.getMetaData();
        ResultSet tabela = dbm.getTables(null, null, "dinossauro", null);

        if (tabela.next()) {
            System.out.println("Tabela criada com sucesso!");
        } else {
            System.out.println("Tabela não existente!");
        }
        //  connection.close();
    }

    public void inserirRegTab(String genero, String especie) throws SQLException {

        PreparedStatement prep = (PreparedStatement) connection
                .prepareStatement("insert into dinossauro (genero, especie) values(?, ?);;");

        prep.setString(1, genero);
        prep.setString(2, especie);
        prep.addBatch();

        System.out.println("Registro feito: " + prep);

        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);

        connection.close();
    }

    public void alteraRegistro(int codigo, String especie, String genero) throws SQLException {
        String sqlConsultaPorCod = "Select codigo,genero,especie from cafes where codigo = ?";

        String sqlAlteracao = "update dinossauro set especie = ?, genero = ? where codigo = ?";

        if (especie == null) {
            PreparedStatement prep = (PreparedStatement) connection.prepareStatement(sqlConsultaPorCod);
            prep.setString(1, especie);
            prep.setString(2, genero);
            prep.setInt(3, codigo);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                System.out.println("========================================================");
                System.out.println("Dados do Café Para Alteração");
                System.out.println("Código..: " + rs.getString("codigo"));
                System.out.println("Espécie..: " + rs.getString("especie"));
                System.out.println("Gênero.: " + rs.getString("genero"));
                System.out.println("========================================================");
            } else {
                System.out.println("Registro não Encontrado!");
                System.exit(0);
            }
        } else {
            StringBuffer srtRet = new StringBuffer();
            try {
                PreparedStatement prep = (PreparedStatement) connection.prepareStatement(sqlAlteracao);
                prep.setString(1, especie);
                prep.setString(2, genero);
                prep.setInt(3, codigo);
                prep.execute();
                System.out.println("Registro alterado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        }
    }

    public void deletarRegistro(int codigo) throws SQLException {
        PreparedStatement prep = (PreparedStatement) connection.prepareStatement("DELETE FROM dinossauro where codigo = ? ");
        connection.setAutoCommit(true);
        prep.setInt(1, codigo);
        prep.executeUpdate();
    }





}
