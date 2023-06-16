package br.com.trier.spring_matutino.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/lancamento")
public class LancamentoDados {

	@GetMapping("/lancardados")
	public ResponseEntity<ResultadoLancamentoDados> lancarDados(@RequestParam Integer quantidade,
			@RequestParam Integer valoraposta) {

		if (quantidade < 1 || quantidade > 4) {
			return ResponseEntity.badRequest().body(new ResultadoLancamentoDados("Quantidade de dados inválida."));
		}

		int valorMaximoPossivel = quantidade * 6;
		if (valoraposta < quantidade || valoraposta > valorMaximoPossivel) {
			return ResponseEntity.badRequest().body(new ResultadoLancamentoDados("Valor da aposta inválido."));
		}

		List<Integer> valoresDados = new ArrayList<>();
		Random random = new Random();
		int soma = 0;

		for (int i = 0; i < quantidade; i++) {
			int valorDado = random.nextInt(6) + 1;
			valoresDados.add(valorDado);
			soma += valorDado;
		}

		double percentualAposta = (double) valoraposta / soma * 100;
		double percentualSorteio = (double) soma / valoraposta * 100;

		ResultadoLancamentoDados resultado = new ResultadoLancamentoDados(valoresDados, soma, percentualAposta,
				percentualSorteio);
		return ResponseEntity.ok(resultado);
	}
	
}
