package br.com.clinicamedica.view;

import br.com.clinicamedica.entities.*;
import br.com.clinicamedica.service.SistemaService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaPerfil extends JFrame {
    private SistemaService service;
    private Object usuarioLogado;

    public TelaPerfil(Object usuario, SistemaService service) {
        this.usuarioLogado = usuario;
        this.service = service;

        setTitle("Meu Perfil - Alterar Dados");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos comuns
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        JTextField txtNome = new JTextField(20);
        gbc.gridx = 1;
        painel.add(txtNome, gbc);

        // Campos específicos
        JLabel lblExtra = new JLabel();
        JTextField txtExtra = new JTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(lblExtra, gbc);
        gbc.gridx = 1;
        painel.add(txtExtra, gbc);

        // Preencher dados atuais e configurar labels
        if (usuarioLogado instanceof Paciente) {
            Paciente p = (Paciente) usuarioLogado;
            txtNome.setText(p.getNome());
            lblExtra.setText("Idade:");
            txtExtra.setText(String.valueOf(p.getIdade()));
        } else if (usuarioLogado instanceof Medico) {
            Medico m = (Medico) usuarioLogado;
            txtNome.setText(m.getNome());
            lblExtra.setText("Especialidade:");
            txtExtra.setText(m.getEspecialidade());
        }

        JButton btnSalvar = new JButton("Salvar Alterações");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        painel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            if (usuarioLogado instanceof Paciente) {
                // Chama o método do seu colega
                service.alterarDadosPaciente((Paciente)usuarioLogado, txtNome.getText(),
                        Integer.parseInt(txtExtra.getText()), ((Paciente)usuarioLogado).getPlanoSaude());
            } else {
                service.alterarDadosMedico((Medico)usuarioLogado, txtNome.getText(),
                        txtExtra.getText(), ((Medico)usuarioLogado).getPlanoAtendido(), ((Medico)usuarioLogado).getValorConsulta());
            }
            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
            this.dispose();
        });

        add(painel);
    }
}