package model;

public class Pessoa {
    private int id;
    private String nome;
    private int idade;
    private String email;
    private String cpf;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Pessoa(String nome, int idade, String email, String cpf) {
        setNome(nome);
        setIdade(idade);
        setEmail(email);
        setCpf(cpf);
    }

    public Pessoa(int id, String nome, int idade, String email, String cpf) {
        setId(id);
        setNome(nome);
        setIdade(idade);
        setEmail(email);
        setCpf(cpf);
    }

}
