package br.com.clinicamedica.entities;

public class Medico {
    private String login;
    private String senha;
    private String nome;
    private String especialidade;
    private String planoAtendido;
    private double valorConsulta;

    public Medico(String login, String senha, String nome,
                  String especialidade, String planoAtendido, double valorConsulta) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.especialidade = especialidade;
        this.planoAtendido = planoAtendido;
        this.valorConsulta = valorConsulta;
    }

    public String toTXT() {
        return login + ";" + senha + ";" + nome + ";" +
                especialidade + ";" + planoAtendido + ";" + valorConsulta;
    }

    public static Medico fromTXT(String linha) {
        String[] dados = linha.split(";");
        return new Medico(
                dados[0], dados[1], dados[2],
                dados[3], dados[4], Double.parseDouble(dados[5])
        );
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getPlanoAtendido() {
        return planoAtendido;
    }

    public void setPlanoAtendido(String planoAtendido) {
        this.planoAtendido = planoAtendido;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
}