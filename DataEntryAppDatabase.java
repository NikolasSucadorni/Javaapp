import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataEntryAppDatabase {

    public static void main(String[] args) {
        // Frame
        JFrame frame = new JFrame("Ponto");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2));

        // labels e campos de textos
        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        JLabel startLabel = new JLabel("Entrada:");
        JTextField startField = new JTextField();
        JLabel endLabel = new JLabel("Saída:");
        JTextField endField = new JTextField();

        // Create save button
        JButton saveButton = new JButton("Salvar");

        // Adicionando componente
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(startLabel);
        frame.add(startField);
        frame.add(endLabel);
        frame.add(endField);
        frame.add(new JLabel()); 
        frame.add(saveButton);

        // botão salvar
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Valor dos camos de texto
                String name = nameField.getText();
                String start = startField.getText();
                String end = endField.getText();

                // Salvando no SQlite
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db")) {
                    String sql = "INSERT INTO users (nome, entrada, saída) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setString(1, name);
                        pstmt.setInt(2, Integer.parseInt(start));
                        pstmt.setString(3, end);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Ponto Registrado com Sucesso!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid age format");
                }
            }
        });

        
        frame.setVisible(true);

        // Inicializando a database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, city TEXT)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.execute();
            }
        } catch (SQLException ex) {
            System.out.println("Error initializing database: " + ex.getMessage());
        }
    }
}