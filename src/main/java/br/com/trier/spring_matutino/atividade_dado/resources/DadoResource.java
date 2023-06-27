package br.com.trier.spring_matutino.atividade_dado.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.atividade_dado.domain.Dado;

@RestController
@RequestMapping("/dados")
public class DadoResource {

    private static final int MIN_VALOR_DADO = 1;
    private static final int MAX_VALOR_DADO = 6;
    private static final int MIN_QUANTIDADE_DADO = 1;
    private static final int MAX_QUANTIDADE_DADO = 4;

    @GetMapping
    public ResponseEntity<String> sortear(
            @RequestParam("quantidadeDado") Integer quantidadeDado,
            @RequestParam("aposta") Double aposta) {

        if (quantidadeDado < MIN_QUANTIDADE_DADO || quantidadeDado > MAX_QUANTIDADE_DADO) {
            return ResponseEntity.badRequest()
                    .body("A quantidade de dados precisa ser entre " + MIN_QUANTIDADE_DADO + " e " + MAX_QUANTIDADE_DADO);
        }

        double minAposta = MIN_QUANTIDADE_DADO * MIN_VALOR_DADO;
        double maxAposta = MAX_QUANTIDADE_DADO * MAX_VALOR_DADO;
        if (aposta < minAposta || aposta > maxAposta) {
            return ResponseEntity.badRequest()
                    .body("Valor da aposta inválido. A aposta deve ser entre " + minAposta + " e " + maxAposta);
        }

        List<Integer> dados = new ArrayList<>();
        Random sorteio = new Random();

        for (int i = 0; i < quantidadeDado; i++) {
            int valorDado = sorteio.nextInt(MAX_VALOR_DADO - MIN_VALOR_DADO + 1) + MIN_VALOR_DADO;
            dados.add(valorDado);
        }

        int somaValorDados = dados.stream().mapToInt(Integer::intValue).sum();
        double percentualAposta = calcularPorcentagem(somaValorDados, aposta);
        Dado result = new Dado(dados, somaValorDados, percentualAposta);
        String resultado = "Resultado dos números dos dados: " + result.getDados() + "\n"
                + "Valor da soma dos dados: " + result.getSomaDados() + "\n"
                + "Percentual aposta: " + String.format("%.2f", percentualAposta) + "%";

        return ResponseEntity.ok(resultado);
    }

    private double calcularPorcentagem(int soma, double aposta) {
        double diferenca = Math.abs(soma - aposta);
        double valorMaior = Math.max(soma, aposta);
        return (diferenca / valorMaior) * 100;
    }

}
