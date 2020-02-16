package ca.mcgill.ecse428.freskfork.dto;

public class UserDto {
	private String name;
	private String email;
	private boolean isPro;
	
	public UserDto() {
	}

	public UserDto(String name, String email, boolean isPro) {
		this.name = name;
		this.email = email;
		this.isPro = isPro;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public boolean isPro() {
		return isPro;
	}
	
	
}
