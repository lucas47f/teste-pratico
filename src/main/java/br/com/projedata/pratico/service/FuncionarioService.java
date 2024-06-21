package br.com.projedata.pratico.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Optional;

import br.com.projedata.pratico.dto.FuncionarioResponse;
import br.com.projedata.pratico.entity.Funcionario;
import br.com.projedata.pratico.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioResponse salvarFuncionario(Funcionario funcionario) {
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        return new FuncionarioResponse("Funcionário salvo com sucesso!", savedFuncionario);
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public void removerFuncionarioPorNome(String nome) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.removeIf(f -> f.getNome().equals(nome));
        funcionarioRepository.deleteAll();
        funcionarioRepository.saveAll(funcionarios);
    }

public List<FuncionarioResponse> listarTodos() {
    return funcionarioRepository.findAll().stream()
            .map(funcionario -> new FuncionarioResponse("Informações: ", funcionario))
            .collect(Collectors.toList());
}

  public void aplicarAumento() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        for (Funcionario f : funcionarios) {
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }
        funcionarioRepository.saveAll(funcionarios);
    }

     public Map<String, List<Funcionario>> agruparPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }
    
    public List<Funcionario> listarAniversariantes(int mes) {
        return funcionarioRepository.findAll().stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == mes)
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioResponse> obterFuncionarioMaisVelho() {
        return funcionarioRepository.findAll().stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .map(funcionario -> new FuncionarioResponse(
                        funcionario.getNome(),
                        calcularIdade(funcionario.getDataNascimento())
                ));
    }

    // Método para calcular a idade com base na data de nascimento
    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public List<Funcionario> ordenarPorNome() {
        return funcionarioRepository.findAll().stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public String calcularTotalSalarios() {
        BigDecimal totalSalarios = funcionarioRepository.findAll().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return "O total de salários é: " + totalSalarios;
    }

    @SuppressWarnings("deprecation")
    public List<String> calcularSalariosMinimos(BigDecimal salarioMinimo) {
        return funcionarioRepository.findAll().stream()
                .map(f -> f.getNome() + " ganha " + f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP) + " salários mínimos")
                .collect(Collectors.toList());
    }
}
