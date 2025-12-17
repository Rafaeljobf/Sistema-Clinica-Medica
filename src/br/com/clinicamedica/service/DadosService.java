package br.com.clinicamedica.database;

import br.com.clinicamedica.entities.*;
import br.com.clinicamedica.exceptions.ArquivoException;
import java.util.ArrayList;
import java.util.List;

public class DadosService {
    // Nomes dos ficheiros definidos na Main
    private static final String PATH_MEDICOS = "medicos.txt";
    private static final String PATH_PACIENTES = "pacientes.txt";
    private static final String PATH_AGENDAMENTOS = "agendamentos.txt";
    private static final String PATH_AVALIACOES = "avaliacoes.txt";

    /**
     * Guarda todas as listas nos respetivos ficheiros txt.
     * Deve ser chamado sempre que houver uma alteração (agendar, cancelar, avaliar, editar perfil).
     */
    public static void salvarTudo(List<Medico> medicos, List<Paciente> pacientes,
                                  List<Agendamento> agendamentos, List<Avaliacao> avaliacoes) {
        try {
            ArquivoUtil.escrever(PATH_MEDICOS, converterMedicos(medicos));
            ArquivoUtil.escrever(PATH_PACIENTES, converterPacientes(pacientes));
            ArquivoUtil.escrever(PATH_AGENDAMENTOS, converterAgendamentos(agendamentos));
            ArquivoUtil.escrever(PATH_AVALIACOES, converterAvaliacoes(avaliacoes));
        } catch (ArquivoException e) {
            System.err.println("Erro ao persistir dados: " + e.getMessage());
        }
    }

    // --- MÉTODOS DE CONVERSÃO (Utilizam o toTXT de cada entidade) ---

    private static List<String> converterPacientes(List<Paciente> lista) {
        List<String> linhas = new ArrayList<>();
        for (Paciente p : lista) {
            linhas.add(p.toTXT()); // Usa o formato: login;senha;nome;idade;plano [cite: 5]
        }
        return linhas;
    }

    private static List<String> converterMedicos(List<Medico> lista) {
        List<String> linhas = new ArrayList<>();
        for (Medico m : lista) {
            linhas.add(m.toTXT()); // Usa o formato: login;senha;nome;especialidade;plano;valor [cite: 6]
        }
        return linhas;
    }

    private static List<String> converterAgendamentos(List<Agendamento> lista) {
        List<String> linhas = new ArrayList<>();
        for (Agendamento a : lista) {
            linhas.add(a.toTXT()); // Usa o formato: loginMed;loginPac;data;realizado [cite: 8]
        }
        return linhas;
    }

    private static List<String> converterAvaliacoes(List<Avaliacao> lista) {
        List<String> linhas = new ArrayList<>();
        for (Avaliacao av : lista) {
            linhas.add(av.toTXT()); // Usa o formato: loginMed;loginPac;estrelas;comentario [cite: 3]
        }
        return linhas;
    }
}