package gusto.fatec.filesystem.controller;

import java.io.*;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class ArquivosController implements IArquivosController {

    public static final File DIR = new File("C:\\TEMP");
    public static final File FILE = new File("C:\\TEMP\\cadastro.csv");

    public static final Logger LOGGER = Logger.getLogger(ArquivosController.class.getName());

    @Override
    public void verificaDirTemp() throws IOException {
        if (!DIR.exists() && !DIR.isDirectory()) {
            DIR.mkdir();
        }
        if (!FILE.exists() && !FILE.isFile()) {
            FILE.createNewFile();
            FileWriter fileWriter = new FileWriter(FILE, FILE.exists());
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write("Código;Nome;E-Mail\n");
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        }
    }

    @Override
    public boolean verificaRegistro(String arquivo, int codigo) throws IOException {
        if ((DIR.exists() && DIR.isDirectory()) && (FILE.exists() && FILE.isFile())) {
            try (FileInputStream fileInputStream = new FileInputStream(FILE);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String text = bufferedReader.readLine();

                while (text != null) {
                    if (text.contains(String.valueOf(codigo))) {
                        return true;
                    } else {
                        text = bufferedReader.readLine();
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void imprimeCadastro(String arquivo, int codigo) throws IOException {
        if (verificaRegistro(arquivo, codigo)) {

            try (FileInputStream fileInputStream = new FileInputStream(FILE);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line = bufferedReader.readLine();
                while (line != null) {
                    if (line.contains(String.valueOf(codigo))) {
                        String[] person = line.split(";");
                        String logMessage = format("Código: {0}\nNome: {1}\nE-Mail: {2}", person[0], person[1], person[2]);
                        LOGGER.info(logMessage);
                    } else {
                        line = bufferedReader.readLine();
                    }
                }

            }
        } else {
            showMessageDialog(null, "Cadastro não existe", "Cadastro não encontrado",
                    WARNING_MESSAGE);
        }
    }

    @Override
    public void insereCadastro(String arquivo, int codigo, String nome, String email) throws IOException {
        if (verificaRegistro(arquivo, codigo)) {
            showMessageDialog(null,
                    "Cadastro já existe",
                    "Cadastro já existe",
                    WARNING_MESSAGE);
            imprimeCadastro(arquivo, codigo);
        } else {
            String linha = format("{0};{1};{2}\n", codigo, nome, email);
            FileWriter fileWriter = new FileWriter(FILE, FILE.exists());
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write(linha);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        }
    }
}
