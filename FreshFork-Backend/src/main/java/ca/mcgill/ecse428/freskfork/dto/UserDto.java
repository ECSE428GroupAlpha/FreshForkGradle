package ca.mcgill.ecse428.freskfork.dto;

public class UserDto {
	private int uId;
	private String name;
	private String email;
	private boolean isPro;
	
	public UserDto() {
	}

	public UserDto(int uId, String name, String email, boolean isPro) {
		this.uId = uId;
		this.name = name;
		this.email = email;
		this.isPro = isPro;
	}
	public int getUId() {
		return uId;
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
