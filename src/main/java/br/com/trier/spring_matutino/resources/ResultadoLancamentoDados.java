package br.com.trier.spring_matutino.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResultadoLancamentoDados {
	    private List<Integer> valoresDados;
	    private int soma;
	    private double percentualAposta;
	    private double percentualSorteio;
	    private String mensagemErro;


	    public ResultadoLancamentoDados(List<Integer> valoresDados, int soma, double percentualAposta, double percentualSorteio) {
	        this.valoresDados = valoresDados;
	        this.soma = soma;
	        this.percentualAposta = percentualAposta;
	        this.percentualSorteio = percentualSorteio;
	    }

	    public ResultadoLancamentoDados(String mensagemErro) {
	        this.mensagemErro = mensagemErro;
	    }
	}

