package br.com.projedata.pratico.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.projedata.pratico.dto.FuncionarioResponse;
import br.com.projedata.pratico.entity.Funcionario;
import br.com.projedata.pratico.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/salvar")
    public ResponseEntity<FuncionarioResponse> salvarFuncionario(@RequestBody Funcionario funcionario) {
        FuncionarioResponse response = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> obterFuncionarioPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionarioOptional = funcionarioService.buscarPorId(id);

        return funcionarioOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/remover/{nome}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable String nome) {
        funcionarioService.removerFuncionarioPorNome(nome);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

     @PutMapping("/aumento")
    public ResponseEntity<Void> aplicarAumento() {
        funcionarioService.aplicarAumento();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agrupados")
    public ResponseEntity<Map<String, List<Funcionario>>> agruparPorFuncao() {
        return ResponseEntity.ok(funcionarioService.agruparPorFuncao());
    }

    @GetMapping("/aniversariantes")
    public ResponseEntity<List<Funcionario>> listarAniversariantes(@RequestParam int mes) {
        List<Funcionario> aniversariantes = funcionarioService.listarAniversariantes(mes);
        return ResponseEntity.ok(aniversariantes);
    }

    @GetMapping("/mais-velho")
    public ResponseEntity<String> obterFuncionarioMaisVelho() {
        Optional<FuncionarioResponse> funcionarioMaisVelho = funcionarioService.obterFuncionarioMaisVelho();
        
        // Verifica se encontrou o funcionário mais velho e retorna a mensagem
        if (funcionarioMaisVelho.isPresent()) {
            return ResponseEntity.ok(funcionarioMaisVelho.get().getMensagem());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Funcionario>> ordenarPorNome() {
        return ResponseEntity.ok(funcionarioService.ordenarPorNome());
    }

    @GetMapping("/total-salarios")
    public ResponseEntity<String> calcularTotalSalarios() {
        String totalSalarios = funcionarioService.calcularTotalSalarios();
        return ResponseEntity.ok(totalSalarios);
    }

    @GetMapping("/salarios-minimos")
    public ResponseEntity<List<String>> calcularSalariosMinimos(@RequestParam BigDecimal salarioMinimo) {
        return ResponseEntity.ok(funcionarioService.calcularSalariosMinimos(salarioMinimo));
    }

}
