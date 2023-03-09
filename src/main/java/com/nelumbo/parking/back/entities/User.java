package com.nelumbo.parking.back.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="_user")
public class User implements Serializable, UserDetails{

	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iduser;
	
	
	@Column(name="name", nullable = true, length = 100, unique=true)
	@NotNull(message = "El nombre es requerido")
	@NotBlank(message = "El nombre es requerido")
	private String name;
	
	 @Enumerated(EnumType.STRING)
	 @Column(name="_role")
	 @NotNull(message = "El Rol es requerido")
	  private Role role;
	
	 @Column(name="email",unique=true)
	 @NotNull(message = "El email es requerido")
	 private String email;
	 
	 @Column(name="password")
	 @NotNull(message = "La contraseña es requerida")
	 private String password;

	 @OneToMany(mappedBy = "user")
	  private List<Token> tokens;
	 
	 //Relacion one to many con token
	 
	 public User(String name,
			 Role role,
			 String email,
			 String password) {
		this.name = name;
		this.role = role;
		this.email = email;
		this.password = password;
	}

	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	

	
	
	

}
