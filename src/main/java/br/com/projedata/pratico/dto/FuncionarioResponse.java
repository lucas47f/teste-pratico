package br.com.projedata.pratico.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

import br.com.projedata.pratico.entity.Funcionario;

public class FuncionarioResponse {
    private String mensagem;
    private String nome;
    private String dataNascimento;
    private BigDecimal salario;
    private String funcao;
    private int idade;

    public FuncionarioResponse(String mensagem, Funcionario funcionario) {
        this.mensagem = mensagem;
        this.nome = funcionario.getNome();
        this.dataNascimento = funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.salario = funcionario.getSalario();
        this.funcao = funcionario.getFuncao();
        this.idade = calcularIdade(funcionario.getDataNascimento());
    }

    public FuncionarioResponse(String nome, int idade) {
        this.nome = nome;
        this.idade = idade; 
        this.mensagem = "O Funcionário mais velho é: " + nome + " com " + idade + " anos";       
    }

     private int calcularIdade(LocalDate dataNascimento) {
       return Period.between(dataNascimento, LocalDate.now()).getYears();
     }

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

}
