package br.com.clinicamedica.view;

import br.com.clinicamedica.entities.*;
import br.com.clinicamedica.service.DadosService;
import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.exceptions.ValidacaoException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaPrincipalPaciente extends JFrame {
    private Paciente pacienteLogado;
    private SistemaService service;
    private JTable tabelaMedicos;
    private DefaultTableModel modelo;
    private JFormattedTextField txtData;

    public TelaPrincipalPaciente(Paciente paciente, SistemaService service, List<Medico> todosMedicos) {
        this.pacienteLogado = paciente;
        this.service = service;

        setTitle("Portal do Paciente - UECE Clinic");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PAINEL NORTE (Boas-vindas e Busca) ---
        JPanel containerNorte = new JPanel(new GridLayout(2, 1));

        // Linha 1: Boas-vindas e Perfil
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lblUser = new JLabel("Usuário: " + paciente.getNome() + " | Plano: " + paciente.getPlanoSaude());
        JButton btnPerfil = new JButton("Meu Perfil"); // Operação 2: Alterar dados
        painelTopo.add(lblUser, BorderLayout.WEST);
        painelTopo.add(btnPerfil, BorderLayout.EAST);

        // Linha 2: Filtros de Busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtBusca = new JTextField(20);
        JButton btnFiltrar = new JButton("Pesquisar Nome/Especialidade"); // Operação: Verificar médicos
        painelBusca.add(new JLabel("Busca:"));
        painelBusca.add(txtBusca);
        painelBusca.add(btnFiltrar);

        containerNorte.add(painelTopo);
        containerNorte.add(painelBusca);
        add(containerNorte, BorderLayout.NORTH);

        // --- PAINEL CENTRAL (Tabela de Médicos) ---
        // Exibe: Nome, Especialidade, Estrelas e Valor
        String[] colunas = {"Nome", "Especialidade", "Plano Atendido", "Avaliação", "Valor Consulta"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaMedicos = new JTable(modelo);
        add(new JScrollPane(tabelaMedicos), BorderLayout.CENTER);

        // --- PAINEL SUL (Ações de Agendamento) ---
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAgendar = new JButton("Agendar Consulta"); // Operação 3
        JButton btnCancelar = new JButton("Cancelar Agendamento"); // Operação 4
        JButton btnAvaliar = new JButton("Avaliar Médico"); // Operação 6

        // SELECIONAR DATA
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');

            txtData = new JFormattedTextField(mascara);
            txtData.setPreferredSize(new Dimension(100, 25));

            JPanel painelData = new JPanel(new FlowLayout());
            painelData.add(new JLabel("Data (DD/MM/AAAA):"));
            painelData.add(txtData);

            // Adicione ao seu painel de ações
            painelAcoes.add(painelData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        painelAcoes.add(btnAgendar);
        painelAcoes.add(btnCancelar);
        painelAcoes.add(btnAvaliar);
        add(painelAcoes, BorderLayout.SOUTH);

        // --- EVENTOS ---

        btnPerfil.addActionListener(e -> {
            new TelaPerfil(pacienteLogado, service).setVisible(true);
        });

        btnFiltrar.addActionListener(e -> carregarTabela(todosMedicos, txtBusca.getText()));



        btnAgendar.addActionListener(e -> {
            try {
                // Pega o texto do campo (ex: 17/12/2025)
                String dataTexto = txtData.getText();

                // Converte para LocalDate
                java.time.format.DateTimeFormatter formatador = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.LocalDate dataLocal = java.time.LocalDate.parse(dataTexto, formatador);

                // Pega o médico selecionado na tabela
                int linha = tabelaMedicos.getSelectedRow();
                if (linha == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione um médico!");
                    return;
                }
                String loginMedico = (String) modelo.getValueAt(linha, 0);

                // Chama o serviço
                service.agendarConsulta(loginMedico, pacienteLogado.getLogin(), dataLocal);

                // Salva tudo nos arquivos
                DadosService.salvarTudo(service.getMedicos(), service.getPacientes(),
                        service.getAgendamentos(), service.getAvaliacoes());

                JOptionPane.showMessageDialog(this, "Consulta agendada!");

            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida! Digite no formato dia/mês/ano.");
            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> {
            // Operação 4: Cancelar e promover lista de espera
            String loginMed = JOptionPane.showInputDialog(this, "Digite o login do médico para cancelar hoje:");
            if (loginMed != null) {
                service.cancelarConsulta(loginMed, pacienteLogado.getLogin(), LocalDate.now());
                JOptionPane.showMessageDialog(this, "Consulta cancelada. Vaga liberada para lista de espera!");
            }
        });

        btnAvaliar.addActionListener(e -> {
            // Abre a tela de avaliação passando o serviço e o login do paciente atual
            TelaAvaliacao telaAv = new TelaAvaliacao(service, pacienteLogado.getLogin());
            telaAv.setVisible(true);
        });

        // Inicialização
        carregarTabela(todosMedicos, "");
    }

    private void carregarTabela(List<Medico> medicos, String filtro) {
        modelo.setRowCount(0);
        for (Medico m : medicos) {
            // Regra 41 e 42: Filtro por plano de saúde
            boolean atendePlano = pacienteLogado.getPlanoSaude().equalsIgnoreCase("-") ||
                    m.getPlanoAtendido().equalsIgnoreCase(pacienteLogado.getPlanoSaude());

            boolean filtroTexto = m.getNome().toLowerCase().contains(filtro.toLowerCase()) ||
                    m.getEspecialidade().toLowerCase().contains(filtro.toLowerCase());

            if (atendePlano && filtroTexto) {
                modelo.addRow(new Object[]{
                        m.getNome(),
                        m.getEspecialidade(),
                        m.getPlanoAtendido(),
                        "⭐⭐⭐⭐", // Aqui pode-se calcular a média real das avaliações
                        "R$ " + m.getValorConsulta()
                });
            }
        }
    }
}