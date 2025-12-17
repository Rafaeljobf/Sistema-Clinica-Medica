package br.com.clinicamedica.view;

import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.exceptions.ValidacaoException;
import javax.swing.*;
import java.awt.*;

public class TelaAvaliacao extends JFrame {
    public TelaAvaliacao(SistemaService service, String loginPaciente) {
        setTitle("Avaliar Consulta");
        setSize(400, 350);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Painel de inputs
        JPanel painelCampos = new JPanel(new GridLayout(4, 1, 5, 5));
        JTextField txtMedico = new JTextField();
        JComboBox<Integer> comboEstrelas = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});

        painelCampos.add(new JLabel(" Login do Médico:"));
        painelCampos.add(txtMedico);
        painelCampos.add(new JLabel(" Nota (1 a 5):"));
        painelCampos.add(comboEstrelas);

        JTextArea txtComentario = new JTextArea(5, 20);
        txtComentario.setBorder(BorderFactory.createTitledBorder("Comentário"));

        JButton btnSalvar = new JButton("Enviar Avaliação");
        btnSalvar.setBackground(new Color(34, 139, 34)); // Verde floresta
        btnSalvar.setForeground(Color.WHITE);

        add(painelCampos, BorderLayout.NORTH);
        add(new JScrollPane(txtComentario), BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);

        // AÇÃO DO BOTÃO
        btnSalvar.addActionListener(e -> {
            try {
                String loginMed = txtMedico.getText();
                int nota = (int) comboEstrelas.getSelectedItem();
                String coment = txtComentario.getText();

                // Chama o serviço para salvar na memória e no TXT
                service.avaliarConsulta(loginMed, loginPaciente, nota, coment);

                JOptionPane.showMessageDialog(this, "Avaliação gravada com sucesso!");
                this.dispose();
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}