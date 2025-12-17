package br.com.clinicamedica.database;

import br.com.clinicamedica.exceptions.ArquivoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtil {

    private static void garantirArquivo(String caminho) throws ArquivoException {
        try {
            File arquivo = new File(caminho);

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

        } catch (IOException e) {
            throw new ArquivoException("Não foi possível criar o arquivo: " + caminho);
        }
    }

    public static void escrever(String caminho, List<String> linhas)
            throws ArquivoException {

        garantirArquivo(caminho);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao escrever no arquivo: " + caminho);
        }
    }

    public static List<String> ler(String caminho)
            throws ArquivoException {

        garantirArquivo(caminho);

        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler o arquivo: " + caminho);
        }
        return linhas;
    }
}