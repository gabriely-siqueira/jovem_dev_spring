package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.PilotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
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
@Entity(name = "piloto")
public class Piloto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	private Integer id;

	@Column(name = "nome")
	private String name;

	@ManyToOne
	private Pais pais;

	@ManyToOne
	private Equipe equipe;

	public Piloto(PilotoDTO dto) {
		this(dto.getId(), dto.getNome(), new Pais(dto.getIdPais(), dto.getNamePais()),
				new Equipe(dto.getIdEquipe(), dto.getNameEquipe()));
	}

	public PilotoDTO toDTO() {
		return new PilotoDTO(id, name, pais.getId(), pais.getName(), equipe.getId(), equipe.getName());
	}

}