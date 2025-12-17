package br.com.clinicamedica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.clinicamedica.entities.*;
import br.com.clinicamedica.exceptions.ValidacaoException;
import br.com.clinicamedica.service.DadosService;

public class SistemaService {

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Agendamento> agendamentos;
    private List<Avaliacao> avaliacoes;
    private List<Agendamento> listaEspera;

    public SistemaService(List<Medico> medicos,
                          List<Paciente> pacientes,
                          List<Agendamento> agendamentos,
                          List<Avaliacao> avaliacoes) {
        this.medicos = medicos;
        this.pacientes = pacientes;
        this.agendamentos = agendamentos;
        this.avaliacoes = avaliacoes;
        this.listaEspera = new ArrayList<>();
    }

    public Medico loginMedico(String login, String senha) throws ValidacaoException {
        for (Medico m : medicos) {
            if (m.getLogin().equals(login) && m.getSenha().equals(senha)) {
                return m;
            }
        }
        throw new ValidacaoException("Login ou senha inválidos");
    }

    public Paciente loginPaciente(String login, String senha) throws ValidacaoException {
        for (Paciente p : pacientes) {
            if (p.getLogin().equals(login) && p.getSenha().equals(senha)) {
                return p;
            }
        }
        throw new ValidacaoException("Login ou senha inválidos");
    }

    public void alterarDadosMedico(Medico medico, String nome, String especialidade,
                                   String planoAtendido, double valorConsulta) {
        medico.setNome(nome);
        medico.setEspecialidade(especialidade);
        medico.setPlanoAtendido(planoAtendido);
        medico.setValorConsulta(valorConsulta);
    }

    public void alterarDadosPaciente(Paciente paciente, String nome, int idade, String planoSaude) {
        paciente.setNome(nome);
        paciente.setIdade(idade);
        paciente.setPlanoSaude(planoSaude);
    }

    public void agendarConsulta(String loginMedico, String loginPaciente, LocalDate data)
            throws ValidacaoException {

        int quantidade = 0;

        for (Agendamento a : agendamentos) {
            if (a.getLoginMedico().equals(loginMedico) && a.getData().equals(data)) {
                quantidade++;
            }
        }

        if (quantidade >= 3) {
            listaEspera.add(new Agendamento(loginMedico, loginPaciente, data, false));
            return;
        }

        agendamentos.add(new Agendamento(loginMedico, loginPaciente, data, false));
    }

    public void cancelarConsulta(String loginMedico, String loginPaciente, LocalDate data) {

        Agendamento removido = null;

        for (Agendamento a : agendamentos) {
            if (a.getLoginMedico().equals(loginMedico)
                    && a.getLoginPaciente().equals(loginPaciente)
                    && a.getData().equals(data)) {
                removido = a;
                break;
            }
        }

        if (removido != null) {
            agendamentos.remove(removido);

            for (Agendamento espera : listaEspera) {
                if (espera.getLoginMedico().equals(loginMedico)
                        && espera.getData().equals(data)) {
                    agendamentos.add(espera);
                    listaEspera.remove(espera);
                    break;
                }
            }
            DadosService.salvarTudo(medicos, pacientes, agendamentos, avaliacoes);
        }
    }

    public double realizarConsulta(String loginMedico, String loginPaciente, LocalDate data)
            throws ValidacaoException {

        Agendamento agendamento = null;

        for (Agendamento a : agendamentos) {
            if (a.getLoginMedico().equals(loginMedico)
                    && a.getLoginPaciente().equals(loginPaciente)
                    && a.getData().equals(data)) {
                agendamento = a;
                break;
            }
        }

        if (agendamento == null) {
            throw new ValidacaoException("Consulta não encontrada");
        }

        agendamento.setRealizado(true);

        DadosService.salvarTudo(medicos, pacientes, agendamentos, avaliacoes);

        Paciente paciente = buscarPaciente(loginPaciente);
        Medico medico = buscarMedico(loginMedico);

        if (paciente.getPlanoSaude().equalsIgnoreCase("-")) {
            return medico.getValorConsulta();
        }

        if (paciente.getPlanoSaude().equalsIgnoreCase(medico.getPlanoAtendido())) {
            return 0;
        }

        return medico.getValorConsulta();
    }

    public void avaliarConsulta(String loginMedico, String loginPaciente, int estrelas, String comentario)
            throws ValidacaoException {

        boolean consultaRealizada = false;
        for (Agendamento a : agendamentos) {
            if (a.getLoginMedico().equals(loginMedico) &&
                    a.getLoginPaciente().equals(loginPaciente) &&
                    a.isRealizado()) {
                consultaRealizada = true;
                break;
            }
        }

        if (!consultaRealizada) {
            throw new ValidacaoException("Erro: Só pode avaliar o médico após a consulta ser realizada!");
        }

        if (estrelas < 1 || estrelas > 5) {
            throw new ValidacaoException("Estrelas devem estar entre 1 e 5");
        }

        avaliacoes.add(new Avaliacao(loginMedico, loginPaciente, estrelas, comentario));

        // Persistência
        DadosService.salvarTudo(medicos, pacientes, agendamentos, avaliacoes);
    }

    private Medico buscarMedico(String login) throws ValidacaoException {
        for (Medico m : medicos) {
            if (m.getLogin().equals(login)) {
                return m;
            }
        }
        throw new ValidacaoException("Médico não encontrado");
    }

    private Paciente buscarPaciente(String login) throws ValidacaoException {
        for (Paciente p : pacientes) {
            if (p.getLogin().equals(login)) {
                return p;
            }
        }
        throw new ValidacaoException("Paciente não encontrado");
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Agendamento> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(List<Agendamento> listaEspera) {
        this.listaEspera = listaEspera;
    }
}