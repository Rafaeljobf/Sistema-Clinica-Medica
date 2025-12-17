package br.com.clinicamedica.entities;

import java.time.LocalDate;

public class Agendamento {
    private String loginMedico;
    private String loginPaciente;
    private LocalDate data;
    private boolean realizado;

    public Agendamento(String loginMedico, String loginPaciente,
                       LocalDate data, boolean realizado) {
        this.loginMedico = loginMedico;
        this.loginPaciente = loginPaciente;
        this.data = data;
        this.realizado = realizado;
    }

    public String toTXT() {
        return loginMedico + ";" + loginPaciente + ";" +
                data + ";" + realizado;
    }

    public static Agendamento fromTXT(String linha) {
        String[] dados = linha.split(";");
        return new Agendamento(
                dados[0], dados[1],
                LocalDate.parse(dados[2]),
                Boolean.parseBoolean(dados[3])
        );
    }

    public String getLoginMedico() {
        return loginMedico;
    }

    public void setLoginMedico(String loginMedico) {
        this.loginMedico = loginMedico;
    }

    public String getLoginPaciente() {
        return loginPaciente;
    }

    public void setLoginPaciente(String loginPaciente) {
        this.loginPaciente = loginPaciente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }
}