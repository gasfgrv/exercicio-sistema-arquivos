package gusto.fatec.filesystem;

import gusto.fatec.filesystem.controller.ArquivosController;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws IOException {
        ArquivosController controller = new ArquivosController();
        controller.verificaDirTemp();

        controller.insereCadastro(ArquivosController.FILE.toString(), 1, "Gustavo", "gsilva@teste.com");
        controller.insereCadastro(ArquivosController.FILE.toString(), 2, "Gustavo", "gsilva@teste.com");
        controller.insereCadastro(ArquivosController.FILE.toString(), 3, "Gustavo", "gsilva@teste.com");
        controller.insereCadastro(ArquivosController.FILE.toString(), 4, "Gustavo", "gsilva@teste.com");
        controller.insereCadastro(ArquivosController.FILE.toString(), 5, "Gustavo", "gsilva@teste.com");

    }
}
