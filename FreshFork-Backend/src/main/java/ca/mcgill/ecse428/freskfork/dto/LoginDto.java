package ca.mcgill.ecse428.freskfork.dto;

public class LoginDto {
	private boolean loginSucessful;
	
	public LoginDto() {
		
	}

	public LoginDto(boolean loginSucessful) {
		this.loginSucessful = loginSucessful;
	}

	public boolean isLoginSucessful() {
		return loginSucessful;
	}
}
