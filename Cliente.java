import java.util.*;
import java.net.*;
import java.io.*;

public class Cliente {

	static final int DEFAULT_PORT = 2000;

	public static void main(String[] args) throws IOException {
		int porta = DEFAULT_PORT;
		Scanner sc = new Scanner(System.in);

		System.out.println("Escreve o ip a qual se pretende conectar: ");
		String servidor = sc.nextLine();

		InetAddress serverAddress = InetAddress.getByName(servidor);

		Socket ligacao;

		// Ver se é possivel chegar ao servidor na porta
		if (InetAddress.getByName(servidor).isReachable(porta)) {
			System.out.println("IP valido");
		} else {
			System.out.println("IP invalido!");
			System.exit(-1);
		}

		try {
			ligacao = new Socket(serverAddress, porta);

			// Buffers de leitura e envio de mensagens entre a socket
			BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));
			PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);
			int x1 = 0;

			// Ciclo de escolhas do cliente
			while (x1 == 0) {
				System.out.println("1- Ver inventario \n2- Modificar inventario \n3- Sair\n");
				int x = 6;
				try {
					x = sc.nextInt();
				} catch (java.util.InputMismatchException e) {
					System.out.println("Erro! Por favor escreva um numero inteiro!");
					x = 3;
				}

				sc.nextLine();

				switch (x) {
					case 1:
						out.println("STOCK_REQUEST");
						out.flush();
						break;

					case 2:
						System.out.println("Nome do produto: ");
						String nome = sc.nextLine();
						System.out.println("Tamanho maximo de stock: 50");
						System.out.println("Quantidade a alterar: ");
						String x2 = sc.nextLine();

						out.println("STOCK_UPDATE");
						out.println(nome);
						out.println(x2);
						out.flush();
						break;

					default:
						out.println("EXIT");
						out.flush();
						x1 = 1;
						break;
				}

				out.flush();

				while (true) {
					String msg = in.readLine();

					if (!msg.equals("END")) {
						if (!msg.isEmpty()) {
							System.out.println("Resposta: " + msg);
						}
					} else
						break;
				}

				System.out.println("\n");
			}

			sc.close();
			ligacao.close();
			System.out.println("Terminou a ligacao!");

		} catch (java.net.ConnectException e) {
			System.out.println("Servidor indisponivel!");
			System.exit(1);
		}
	}
}
