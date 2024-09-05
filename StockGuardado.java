import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockGuardado {
    private final String filePath = "ficheiroProdutos.txt";
    private List<Produto> produtos;

    public StockGuardado() {
        produtos = new ArrayList<>();

        
          Produto prod1 = new Produto(1, "Arroz", "Arroz", 10);
          Produto prod2 = new Produto(1, "Peixe", "Arroz", 8);
          Produto prod3 = new Produto(1, "Carne", "Arroz", 20);
          Produto prod4 = new Produto(1, "Leite", "Arroz", 40);
          
          produtos.add(prod1);
          produtos.add(prod2);
          produtos.add(prod3);
          produtos.add(prod4);
         

    }

    public void guardarFicheiro() {

        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(produtos);

            out.close();
            file.close();

            System.out.println("Dados foram guardados no ficheiro");

        } catch (IOException e) {
            System.out.println("IOException is caught");
        }

    }

    public void enviarDadosFicheiro() {

        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("ficheiroProdutos.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            produtos = (ArrayList) in.readObject();

            in.close();
            file.close();

            System.out.println("Dados foram carregados ");
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

    }

    public boolean existeFicheiro(String f){
        File ficheiro = new File(f);
        return ficheiro.exists();
    }

    public boolean verProdutosNome(String nome) {

        for (Produto p : produtos) {
            if (p.getNome().equals(nome)) {
                return true;
            }
        }

        return false;
    }

    public boolean mudarQuantidadePorNome(String nome, String quantidade) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome) && p.verificaCondicoes(quantidade)) {
                p.mudarQuantidade(quantidade);
                return true;
            }
        }

        return false;
    }

    public String toString() {
        String x = "";

        for (Produto p : produtos) {
            x += p.toString() + "\n";
        }

        return x;
    }
}
