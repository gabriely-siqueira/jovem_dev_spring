package br.com.trier.spring_matutino.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoDTO {
	private Integer id;
	private String nome;
	private Integer idPais;
	private String namePais;
	private Integer idEquipe;
	private String nameEquipe;
}
