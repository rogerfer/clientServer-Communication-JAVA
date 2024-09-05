import java.net.*;
import java.io.*;

public class Servidor {
	static int DEFAULT_PORT = 2000;

	public static void main(String[] args) throws IOException {
		int port = DEFAULT_PORT;
		Presences presences = new Presences();
		StockGuardado stockGuardado = new StockGuardado();

		// Create a server socket, bound to the specified port: API
		// java.net.ServerSocket
		ServerSocket servidor = new ServerSocket(port);

		//Ir buscar o stock ao ficheiro
		if(stockGuardado.existeFicheiro("ficheiroProdutos.txt")){
			stockGuardado.enviarDadosFicheiro();
			System.out.println("Ficheiro carregado");
		}

		//Thread
		while (true) {
			try {

				Socket ligacao = servidor.accept();

				ThreadCliente requesthandler = new ThreadCliente(ligacao, presences, stockGuardado);
				requesthandler.start();

			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: " + e);
				System.exit(1);
			}
		}
	}
}
