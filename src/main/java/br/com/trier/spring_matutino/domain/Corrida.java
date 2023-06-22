package br.com.trier.spring_matutino.domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
@Entity(name = "corrida")
public class Corrida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	private Integer id;

	@Column(name = "data")
	private ZonedDateTime data;

	@ManyToOne
	private Pista pista;
	@ManyToOne
	@NotNull
	private Campeonato campeonato;

	public Corrida(CorridaDTO dto) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), new Pista(dto.getIdPista(), null, null),
				new Campeonato(dto.getId(), dto.getDescricaoCampeonato(), null));
	}

	public Corrida(CorridaDTO dto, Campeonato campeonato, Pista pista) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), pista, campeonato);
	}

	public CorridaDTO toDto() {
		return new CorridaDTO(id, DateUtils.zonedDateTimeToStr(data), pista.getId(), campeonato.getId(),
				campeonato.getDescricao());
	}

}