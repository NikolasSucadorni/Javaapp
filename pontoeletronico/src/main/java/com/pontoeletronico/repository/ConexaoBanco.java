package com.pontoeletronico.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConexaoBanco {

    // URL de conexão ao banco de dados
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ponto_eletronico";
    private static final String USUARIO = "admin";
    private static final String SENHA = "pontoeletronico";

    public static void main(String[] args) {
        // Teste inserção e print de dados
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraAtual = agora.format(formatter);
        
        inserirPonto(1, dataHoraAtual, dataHoraAtual);
        consultarPontos();
    }

    // Método para inserir um ponto
    public static void inserirPonto(int funcionarioId, String horaEntrada, String horaSaida) {
        String sql = "INSERT INTO ponto (fk_funcionario_id, hora_entrada, hora_saida) VALUES (?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, funcionarioId);
            statement.setString(2, horaEntrada);
            statement.setString(3, horaSaida);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ponto inserido com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir ponto.");
            e.printStackTrace();
        }
    }

    // Método para consultar os pontos
    public static void consultarPontos() {
        String sql = "SELECT * FROM ponto";

        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                int pontoId = resultado.getInt("ponto_id");
                int funcionarioId = resultado.getInt("fk_funcionario_id");
                String horaEntrada = resultado.getString("hora_entrada");
                String horaSaida = resultado.getString("hora_saida");

                System.out.println("ID: " + pontoId + ", Funcionario ID: " + funcionarioId + ", Hora entrada: " + horaEntrada + ", Hora saída: " + horaSaida);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar pontos.");
            e.printStackTrace();
        }
    }

    
}
