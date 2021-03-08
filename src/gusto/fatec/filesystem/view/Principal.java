package gusto.fatec.filesystem.view;

import java.io.IOException;

import gusto.fatec.filesystem.controller.ArquivosController;

public class Principal {
	public static void main(String[] args) throws IOException {
		ArquivosController controller = new ArquivosController();
		controller.verificaDirTemp();
		controller.insereCadastro(controller.FILE.toString(), 1, "Gustavo", "gsilva@teste.com");
		controller.insereCadastro(controller.FILE.toString(), 2, "Gustavo", "gsilva@teste.com");
		controller.insereCadastro(controller.FILE.toString(), 3, "Gustavo", "gsilva@teste.com");
		controller.insereCadastro(controller.FILE.toString(), 4, "Gustavo", "gsilva@teste.com");
		controller.insereCadastro(controller.FILE.toString(), 5, "Gustavo", "gsilva@teste.com");
	}
}
