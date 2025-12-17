package br.com.clinicamedica.entities;

public class Paciente {
    private String login;
    private String senha;
    private String nome;
    private int idade;
    private String planoSaude; // possui / não possui

    public Paciente(String login, String senha, String nome, int idade, String planoSaude) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.idade = idade;
        this.planoSaude = planoSaude;
    }

    public String toTXT() {
        return login + ";" + senha + ";" + nome + ";" +
                idade + ";" + planoSaude;
    }

    public static Paciente fromTXT(String linha) {
        String[] dados = linha.split(";");
        return new Paciente(
                dados[0], dados[1], dados[2],
                Integer.parseInt(dados[3]), dados[4]
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }
}