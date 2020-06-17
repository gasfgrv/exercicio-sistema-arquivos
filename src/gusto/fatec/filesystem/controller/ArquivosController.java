package gusto.fatec.filesystem.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ArquivosController implements IArquivosController {
	public final File dir = new File("C:\\TEMP");
	public final File file = new File("C:\\TEMP\\cadastro.csv");

	@Override
	public void verificaDirTemp() throws IOException {
		if (!dir.exists() && !dir.isDirectory()) {
			dir.mkdir();
		}
		if (!file.exists() && !file.isFile()) {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file, file.exists());
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write("Código;Nome;E-Mail\n");
			printWriter.flush();
			printWriter.close();
			fileWriter.close();
		}
	}

	@Override
	public boolean verificaRegistro(String arquivo, int codigo) throws IOException {
		if (dir.exists() && dir.isDirectory()) {
			if (file.exists() && file.isFile()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String text = bufferedReader.readLine();
				while (text != null) {
					if (text.contains(String.valueOf(codigo))) {
						bufferedReader.close();
						inputStreamReader.close();
						fileInputStream.close();
						return true;
					} else {
						text = bufferedReader.readLine();
					}
				}
				bufferedReader.close();
				inputStreamReader.close();
				fileInputStream.close();
			}
		}
		return false;
	}

	@Override
	public void imprimeCadastro(String arquivo, int codigo) throws IOException {
		if (verificaRegistro(arquivo, codigo)) {
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = bufferedReader.readLine();
			while (line != null) {
				if (line.contains(String.valueOf(codigo))) {
					String[] person = line.split(";");
					System.out.println("Código: " + person[0] + "\nNome: " + person[1] + "\nE-Mail: " + person[2]);
				} else {
					line = bufferedReader.readLine();
				}
			}
			bufferedReader.close();
			inputStreamReader.close();
			fileInputStream.close();
		} else {
			JOptionPane.showMessageDialog(null, "Cadastro não existe", "Cadastro não encontrado",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void insereCadastro(String arquivo, int codigo, String nome, String email) throws IOException {
		if (verificaRegistro(arquivo, codigo)) {
			JOptionPane.showMessageDialog(null, "Cadastro já existe", "Cadastro já existe",
					JOptionPane.WARNING_MESSAGE);
			imprimeCadastro(arquivo, codigo);
		} else {
			String linha = codigo + ";" + nome + ";" + email + "\n";
			FileWriter fileWriter = new FileWriter(file, file.exists());
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(linha);
			printWriter.flush();
			printWriter.close();
			fileWriter.close();
		}
	}
}
