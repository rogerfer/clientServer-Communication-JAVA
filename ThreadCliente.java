import java.net.*;
import java.io.*;
import java.util.*;

public class ThreadCliente extends Thread {
	Socket ligacao;
	Presences presences;
	BufferedReader in;
	PrintWriter out;
	StockGuardado produtos;
	boolean threadPausada = true;

	public ThreadCliente(Socket ligacao, Presences presences, StockGuardado produtos) {
		this.ligacao = ligacao;
		this.presences = presences;
		this.produtos = produtos;
		try {
			this.in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

			this.out = new PrintWriter(ligacao.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("Aceitou ligacao de cliente no endereco " + ligacao.getInetAddress() + " na porta "
					+ ligacao.getPort());
			IPInfo info = new IPInfo(ligacao.getRemoteSocketAddress().toString(), 0);
			String msg;

			while ((msg = in.readLine()) != null && !msg.equals("EXIT")) {
				System.out.println("Request=" + msg + " " + info.getIP());

				StringTokenizer tokens = new StringTokenizer(msg);
				String metodo = tokens.nextToken();
				synchronized (produtos) {

					if (metodo.equals("STOCK_REQUEST")) {
						out.println("STOCK_RESPONSE");
						String saida = produtos.toString();
						out.println(saida);
						out.println("END");
						out.flush();

					} else if (metodo.equals("STOCK_UPDATE")) {
						String nome = in.readLine();
						String x2 = in.readLine();

						synchronized (this) {
							if (produtos.verProdutosNome(nome)) {
								if (produtos.mudarQuantidadePorNome(nome, x2)) {
									out.println("STOCK_UPDATED");
									out.println(produtos.toString());
									out.println("END");
									out.flush();

								} else {
									out.println("STOCK_ERROR");
									out.println("Quantidade introduzida invalida");
									out.println("END");
									out.flush();
									threadPausada = false;

								}
							} else {
								out.println("STOCK_ERROR");
								out.println("Nome invalido");
								out.println("END");
								out.flush();
								threadPausada = false;
							}

						}
					}
				}
				if (threadPausada){
					try {
					Thread.sleep(5000);
				} catch (Exception e) {
					System.out.println("Erro: " + e);
				}

				}
				threadPausada = true;
			}

			out.println("END");
			out.flush();
			ThreadCliente.interrupted();
			in.close();
			out.close();
			ligacao.close();
			produtos.guardarFicheiro();

		} catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
		}

	}
}
