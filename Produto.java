import java.io.Serializable;

public class Produto implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private float quantidade;

    public Produto() {
    }

    public Produto(int id, String nome, String descricao, float quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean verificaCondicoes(String num) {
        try {
            if (quantidade + Integer.parseInt(num) > 50)
                return false;
            if (quantidade + Integer.parseInt(num) < 0)
                return false;
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Definir uma quantidade máxima = 50
    public void mudarQuantidade(String num) {
        this.quantidade += Integer.parseInt(num);
    }

    public String toString() {
        return this.nome + ":" + this.quantidade;
    }
}
