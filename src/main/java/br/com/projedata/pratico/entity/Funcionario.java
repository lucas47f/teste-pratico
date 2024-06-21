package br.com.projedata.pratico.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Funcionario extends Pessoa{

@Column(name = "salario", nullable = false)    
private BigDecimal salario;

@Column(name = "funcao", nullable = false)
private String funcao;

}
