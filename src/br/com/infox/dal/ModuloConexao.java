/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author luizz
 */
public class ModuloConexao {

    //método responsavel por estabelecer a conexão com o banco
    public static Connection conector() {
        //Criando variavel especial para armazenar os dados 
        java.sql.Connection conexao = null;
        //A linha abaixo chama a execução do driver do banco
        String driver = "com.mysql.jdbc.Driver";
        // Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "";
        // Estabelecendo a conexão com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password); //Obtem a conexao com o banco
            return conexao;
        } catch (Exception e) {
            //A linha abaxio ajuda o usuario a exclarecer o problema 
            
           // System.out.println(e);
            return null;
            
        }
    }
}
