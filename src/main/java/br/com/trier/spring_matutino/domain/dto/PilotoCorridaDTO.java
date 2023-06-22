package br.com.trier.spring_matutino.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PilotoCorridaDTO {
	private Integer id;
	private Integer idPiloto;
	private String namePiloto;
	private Integer idCorrida;
	private String dataCorrida;
	private String colocacao;
}
