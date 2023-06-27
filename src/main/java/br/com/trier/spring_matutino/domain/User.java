package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "usuario") 
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	@Setter
	private Integer id;
	
	@Column(name = "nome_usuario")
	private String name;
	
	@Column(name = "email_usuario", unique = true)
	private String email;
	
	@Column(name = "senha_usuario")
	private String password;
	@Column(name = "roles")
	private String roles;
	
	public User(UserDTO dto) {
		this(dto.getId(),dto.getName(),dto.getEmail(),dto.getPassword(),dto.getRoles());
		
	}
	public UserDTO toDto() {
		return new UserDTO(this.id,this.name,this.email,this.password,this.roles);
		}
	}
