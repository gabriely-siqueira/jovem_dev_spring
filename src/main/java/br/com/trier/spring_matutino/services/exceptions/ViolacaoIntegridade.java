package br.com.trier.spring_matutino.services.exceptions;

public class ViolacaoIntegridade extends RuntimeException{
	
	public ViolacaoIntegridade(String mensagem) {
		super(mensagem);
	}

}
