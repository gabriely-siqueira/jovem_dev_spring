package br.com.trier.spring_matutino.atividade_dado.domain;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dado {
	private List<Integer> dados;
	private int somaDados;
	private double percentualAposta;
}