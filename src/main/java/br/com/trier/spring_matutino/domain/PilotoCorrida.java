package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.spring_matutino.utils.DateUtils;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto_corrida")
public class PilotoCorrida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Piloto piloto;

	@ManyToOne
	private Corrida corrida;

	@Column
	private Integer colocacao;

	public PilotoCorrida(PilotoCorridaDTO dto) {
		this(dto.getId(), new Piloto(dto.getIdPiloto(), dto.getNamePiloto(), null, null),
				new Corrida(dto.getIdCorrida(), DateUtils.strToZonedDateTime(dto.getDataCorrida()), null, null),
				dto.getColocacao());
	}

	public PilotoCorridaDTO toDTO() {
		return new PilotoCorridaDTO(id, piloto.getId(), piloto.getName(), corrida.getId(),
				DateUtils.zonedDateTimeToStr(corrida.getData()), colocacao);
	}

	public PilotoCorrida(PilotoCorridaDTO dto, Piloto piloto, Corrida corrida) {
		this(dto.getId(), piloto, corrida, dto.getColocacao());
	}
}
