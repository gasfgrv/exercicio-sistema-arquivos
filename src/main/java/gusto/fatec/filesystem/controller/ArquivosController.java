package gusto.fatec.filesystem.controller;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class ArquivosController implements IArquivosController {

    public static final File DIR = new File(System.getProperty("java.io.tmpdir"));
    public static final File FILE = new File(DIR + System.getProperty("file.separator") + "cadastro.csv");
    public static final Logger LOGGER = Logger.getLogger(ArquivosController.class.getName());

    @Override
    public boolean verificaDirTemp() throws IOException {
        return verificaSeDiretorioExiste() && verificaSeArquivoExiste();
    }

    private boolean verificaSeArquivoExiste() throws IOException {
        boolean retorno = false;

        if (!FILE.exists() && !FILE.isFile()) {
            retorno = FILE.createNewFile();
            FileWriter fileWriter = new FileWriter(FILE, FILE.exists());
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write("Código;Nome;E-Mail\n");
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        }

        return retorno;
    }

    private boolean verificaSeDiretorioExiste() {
        boolean retorno = false;

        if (!DIR.exists() && !DIR.isDirectory()) {
            retorno = DIR.mkdir();
        }

        return retorno;
    }

    @Override
    public boolean verificaRegistro(String arquivo, int codigo) throws IOException {
        if (verificaDirTemp()) {
            String textoArquivo = lerArquivo();
            return textoArquivo.contains(String.valueOf(codigo));
        }

        return false;
    }


    @Override
    public void imprimeCadastro(String arquivo, int codigo) throws IOException {
        if (verificaRegistro(arquivo, codigo)) {
            List<String> textoArquivo = List.of(lerArquivo().split("\n"));
            escreverLogs(codigo, textoArquivo);
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Cadastro não existe",
                "Cadastro não encontrado",
                WARNING_MESSAGE);
    }

    private void escreverLogs(int codigo, List<String> textoArquivo) {
        textoArquivo.forEach(linha -> {
            if (linha.contains(String.valueOf(codigo))) {
                String[] pessoa = linha.split(";");
                String logMessage = format("Código: {0}\nNome: {1}\nE-Mail: {2}", pessoa[0], pessoa[1], pessoa[2]);
                LOGGER.info(logMessage);
            }
        });
    }

    private String lerArquivo() throws IOException {
        StringBuilder textoArquivo = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(FILE);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String linha = bufferedReader.readLine();

            while (linha != null) {
                textoArquivo.append(linha).append("\n");
            }
        }

        return textoArquivo.toString();
    }

    @Override
    public void insereCadastro(String arquivo, int codigo, String nome, String email) throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE, FILE.exists());
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            escreverArquivo(arquivo, codigo, nome, email, printWriter);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

        JOptionPane.showMessageDialog(null,
                "Cadastro já existe",
                "Cadastro já existe",
                WARNING_MESSAGE);
        imprimeCadastro(arquivo, codigo);
    }

    private void escreverArquivo(String arquivo, int codigo, String nome, String email, PrintWriter printWriter) throws IOException {
        if (!verificaRegistro(arquivo, codigo)) {
            String linha = format("{0};{1};{2}\n", codigo, nome, email);
            printWriter.write(linha);
            printWriter.flush();
        }
    }
}
