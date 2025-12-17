package br.com.clinicamedica;

import br.com.clinicamedica.database.ArquivoUtil;
import br.com.clinicamedica.entities.*;
import br.com.clinicamedica.service.SistemaService;
import br.com.clinicamedica.view.TelaLogin;
import br.com.clinicamedica.exceptions.ArquivoException;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Caminhos dos arquivos (armazenamento csv/txt exigido)
            String pathMedicos = "medicos.txt";
            String pathPacientes = "pacientes.txt";
            String pathAgendamentos = "agendamentos.txt";
            String pathAvaliacoes = "avaliacoes.txt";

            // 2. Carregar dados usando a classe ArquivoUtil
            List<Medico> medicos = carregarMedicos(pathMedicos);
            List<Paciente> pacientes = carregarPacientes(pathPacientes);
            List<Agendamento> agendamentos = carregarAgendamentos(pathAgendamentos);
            List<Avaliacao> avaliacoes = carregarAvaliacoes(pathAvaliacoes);

            // 3. Inicializar o Serviço com as listas carregadas
            SistemaService service = new SistemaService(medicos, pacientes, agendamentos, avaliacoes);

            // 4. Iniciar a Interface Gráfica (Swing) na Thread correta
            SwingUtilities.invokeLater(() -> {
                TelaLogin login = new TelaLogin(service);
                login.setVisible(true);
            });

        } catch (ArquivoException e) {
            System.err.println("Erro crítico ao carregar banco de dados: " + e.getMessage());
        }
    }

    // Métodos auxiliares para converter as linhas de texto em objetos (Entidades)
    private static List<Medico> carregarMedicos(String path) {
        List<Medico> lista = new ArrayList<>();
        for (String linha : ArquivoUtil.ler(path)) {
            lista.add(Medico.fromTXT(linha)); // Usa o método fromTXT que seu colega criou
        }
        return lista;
    }

    private static List<Paciente> carregarPacientes(String path) {
        List<Paciente> lista = new ArrayList<>();
        for (String linha : ArquivoUtil.ler(path)) {
            lista.add(Paciente.fromTXT(linha)); //
        }
        return lista;
    }

    private static List<Agendamento> carregarAgendamentos(String path) {
        List<Agendamento> lista = new ArrayList<>();
        for (String linha : ArquivoUtil.ler(path)) {
            lista.add(Agendamento.fromTXT(linha)); //
        }
        return lista;
    }

    private static List<Avaliacao> carregarAvaliacoes(String path) {
        List<Avaliacao> lista = new ArrayList<>();
        for (String linha : ArquivoUtil.ler(path)) {
            lista.add(Avaliacao.fromTXT(linha)); //
        }
        return lista;
    }
}