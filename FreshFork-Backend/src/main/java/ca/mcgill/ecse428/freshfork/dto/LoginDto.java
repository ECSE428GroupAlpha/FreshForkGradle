package ca.mcgill.ecse428.freshfork.dto;

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
