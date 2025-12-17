package br.com.clinicamedica.entities;

public class Avaliacao {
    private String loginMedico;
    private String loginPaciente;
    private int estrelas;
    private String comentario;

    public Avaliacao(String loginMedico, String loginPaciente,
                     int estrelas, String comentario) {
        this.loginMedico = loginMedico;
        this.loginPaciente = loginPaciente;
        this.estrelas = estrelas;
        this.comentario = comentario;
    }

    public String toTXT() {
        return loginMedico + ";" + loginPaciente + ";" +
                estrelas + ";" + comentario;
    }

    public static Avaliacao fromTXT(String linha) {
        String[] dados = linha.split(";");
        return new Avaliacao(
                dados[0], dados[1],
                Integer.parseInt(dados[2]), dados[3]
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

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}