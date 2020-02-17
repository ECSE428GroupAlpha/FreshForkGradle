package ca.mcgill.ecse428.freskfork.dto;

public class RecipeDto {
	private String recipeName;
	private String authorName;
	private String recipeSteps;
	private String rating;
	private int recipeID;

	public RecipeDto(String recipeName, String authorName, String recipeSteps, String rating, int recipeID) {
		this.recipeName = recipeName;
		this.recipeSteps = recipeSteps;
		this.rating = rating;
		this.recipeID = recipeID;
		this.authorName = authorName;
	}

	public String getrecipeName() {
		return recipeName;
	}
	
	public String getauthorName() {
		return authorName;
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
