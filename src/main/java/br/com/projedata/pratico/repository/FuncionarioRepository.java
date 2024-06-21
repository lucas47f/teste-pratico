package br.com.projedata.pratico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projedata.pratico.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
