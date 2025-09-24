package model;

public class Pessoa {
    private String nome;
    private int idade;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade >= 0) {
            this.idade = idade;
        } else {
            System.out.println("Idade inválida: " + idade);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@") ) {
            this.email = email;
        } else {
            System.out.println("Email inválido: " + email);
        }
    }

    public Pessoa(String nome, int idade, String email) {
        setNome(nome);
        setIdade(idade);
        setEmail(email);
    }


}
