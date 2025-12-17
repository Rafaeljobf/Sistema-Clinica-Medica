package br.com.clinicamedica.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.entities.Medico;
import br.com.clinicamedica.entities.Paciente;
import br.com.clinicamedica.exceptions.ValidacaoException;

public class TelaLogin extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JComboBox<String> cbTipo;
    private SistemaService service;

    public TelaLogin(SistemaService service) {
        this.service = service;
        setTitle("Sistema de Clínica Médica - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes da Tela
        add(new JLabel(" Login:"));
        txtLogin = new JTextField();
        add(txtLogin);

        add(new JLabel(" Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel(" Tipo de Usuário:"));
        cbTipo = new JComboBox<>(new String[]{"Paciente", "Médico"});
        add(cbTipo);

        JButton btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        // Ação do Botão
        btnEntrar.addActionListener(this::efetuarLogin);
    }

    private void efetuarLogin(ActionEvent e) {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());
        String tipo = (String) cbTipo.getSelectedItem();

        try {
            if (tipo.equals("Médico")) {
                Medico medico = service.loginMedico(login, senha);
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + medico.getNome());

                TelaMedico telaMed = new TelaMedico(medico, service);
                telaMed.setVisible(true);
            } else {
                Paciente paciente = service.loginPaciente(login, senha);
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + paciente.getNome());

                TelaPrincipalPaciente telaPac = new TelaPrincipalPaciente(paciente, service, service.getMedicos());
                telaPac.setVisible(true);
            }
            this.dispose(); // Fecha a tela de login
        } catch (ValidacaoException ex) {
            // Aqui você usa a Exception que seu colega criou! [cite: 9]
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}