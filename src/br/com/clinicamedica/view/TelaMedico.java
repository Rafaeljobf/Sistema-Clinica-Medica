package br.com.clinicamedica.view;

import br.com.clinicamedica.entities.Medico;
import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.exceptions.ValidacaoException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class TelaMedico extends JFrame {
    private SistemaService service;
    private Medico medicoLogado;

    public TelaMedico(Medico medico, SistemaService service) {
        this.medicoLogado = medico;
        this.service = service;

        setTitle("Consultório - Dr(a). " + medico.getNome());
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com GridBagLayout para controle total
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os itens
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Botão Editar Perfil (Operação 2 do trabalho)
        JButton btnPerfil = new JButton("Editar Meu Perfil");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.NORTHEAST; // Alinha no topo à direita
        painelPrincipal.add(btnPerfil, gbc);

        // 2. Título do Atendimento
        JLabel lblTitulo = new JLabel("Realizar Atendimento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 1; gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(lblTitulo, gbc);

        // 3. Login do Paciente
        gbc.gridwidth = 1; gbc.gridy = 2;
        painelPrincipal.add(new JLabel("Login do Paciente:"), gbc);

        JTextField txtPaciente = new JTextField(20);
        gbc.gridx = 1;
        painelPrincipal.add(txtPaciente, gbc);

        // 4. Sintomas e Tratamento (Operação 5)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painelPrincipal.add(new JLabel("Sintomas e Tratamento:"), gbc);

        JTextArea txtDescricao = new JTextArea(10, 30);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescricao);
        gbc.gridy = 4;
        painelPrincipal.add(scroll, gbc);

        // 5. Botão Finalizar
        JButton btnFinalizar = new JButton("Finalizar Consulta e Gerar Cobrança");
        btnFinalizar.setBackground(new Color(51, 153, 255));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 5;
        painelPrincipal.add(btnFinalizar, gbc);

        add(painelPrincipal);

        // --- LÓGICA DOS BOTÕES ---

        btnPerfil.addActionListener(e -> new TelaPerfil(medicoLogado, service).setVisible(true));

        btnFinalizar.addActionListener(e -> {
            try {
                // Chama a regra de cobrança e persiste no arquivo
                double valor = service.realizarConsulta(medicoLogado.getLogin(), txtPaciente.getText(), LocalDate.now());

                String msg = (valor > 0) ? "Consulta Finalizada!\nCobrança: R$ " + valor : "Consulta Finalizada (Plano de Saúde)!";
                JOptionPane.showMessageDialog(this, msg);

                txtPaciente.setText("");
                txtDescricao.setText("");
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}