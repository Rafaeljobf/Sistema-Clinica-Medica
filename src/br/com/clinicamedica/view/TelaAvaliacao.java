package br.com.clinicamedica.view;

import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.exceptions.ValidacaoException;
import javax.swing.*;
import java.awt.*;

public class TelaAvaliacao extends JFrame {
    public TelaAvaliacao(SistemaService service, String loginPaciente) {
        setTitle("Avaliar Médico");
        setSize(350, 300);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel painelCampos = new JPanel(new GridLayout(4, 1));

        JTextField txtMedico = new JTextField();
        JComboBox<Integer> comboEstrelas = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JTextArea txtComentario = new JTextArea(5, 20);

        painelCampos.add(new JLabel(" Login do Médico:"));
        painelCampos.add(txtMedico);
        painelCampos.add(new JLabel(" Nota (1-5 estrelas):"));
        painelCampos.add(comboEstrelas);

        JButton btnSalvar = new JButton("Enviar Avaliação");

        add(painelCampos, BorderLayout.NORTH);
        add(new JScrollPane(txtComentario), BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {
            try {
                // Operação 6: Avaliar consulta
                service.avaliarConsulta(txtMedico.getText(), loginPaciente,
                        (int)comboEstrelas.getSelectedItem(), txtComentario.getText());
                JOptionPane.showMessageDialog(this, "Avaliação enviada com sucesso!");
                this.dispose();
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}