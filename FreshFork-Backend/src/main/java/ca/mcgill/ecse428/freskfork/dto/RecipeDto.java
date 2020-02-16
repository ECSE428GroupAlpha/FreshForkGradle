package ca.mcgill.ecse428.freskfork.dto;

public class RecipeDto {
	private String name;
	private String recipeSteps;
	private String rating;
	private int recipeID;
	
	public RecipeDto() {
	}

	public RecipeDto(String name, String recipeSteps, String rating, int recipeID) {
		this.name = name;
		this.recipeSteps = recipeSteps;
		this.rating = rating;
		this.recipeID = recipeID;
	}

	public String getName() {
		return name;
	}

	public String getRecipeSteps() {
		return recipeSteps;
	}

	public String getRating() {
		return rating;
	}

	public int getRecipeID() {
		return recipeID;
	}
}
