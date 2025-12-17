package br.com.clinicamedica.view;

import br.com.clinicamedica.service.SistemaService;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaCancelarConsulta extends JFrame {
    public TelaCancelarConsulta(SistemaService service, String loginPaciente) {
        setTitle("Cancelar Agendamento");
        setSize(400, 200);
        setLayout(new GridLayout(3, 1, 10, 10));
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new FlowLayout());
        painel.add(new JLabel("Login do Médico:"));
        JTextField txtMed = new JTextField(15);
        painel.add(txtMed);

        JButton btnConfirmar = new JButton("Confirmar Cancelamento");

        add(new JLabel("Informe o médico da consulta de hoje para cancelar:", SwingConstants.CENTER));
        add(painel);
        add(btnConfirmar);

        btnConfirmar.addActionListener(e -> {
            // Operação 4: Cancelar e promover lista de espera
            service.cancelarConsulta(txtMed.getText(), loginPaciente, LocalDate.now());
            JOptionPane.showMessageDialog(this, "Se havia consulta, ela foi cancelada e a vaga liberada!");
            this.dispose();
        });
    }
}