package ca.mcgill.ecse428.freshfork.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Recipe{
private Set<Diet> diet;

@ManyToMany(mappedBy="recipe", fetch = FetchType.EAGER)
public Set<Diet> getDiet() {
   return this.diet;
}

public void setDiet(Set<Diet> diets) {
   this.diet = diets;
}

private Users author;

@ManyToOne(optional=false)
public Users getAuthor() {
   return this.author;
}

public void setAuthor(Users author) {
   this.author = author;
}

private Set<Users> favoritedBy;

@ManyToMany(mappedBy="favoriteRecipes")
public Set<Users> getFavoritedBy() {
   return this.favoritedBy;
}

public void setFavoritedBy(Set<Users> favoritedBys) {
   this.favoritedBy = favoritedBys;
}

private Set<IngredientUsage> ingredientUsage;

@OneToMany(mappedBy="recipe")
public Set<IngredientUsage> getIngredientUsage() {
   return this.ingredientUsage;
}

public void setIngredientUsage(Set<IngredientUsage> ingredientUsages) {
   this.ingredientUsage = ingredientUsages;
}

private String name;

public void setName(String value) {
this.name = value;
    }
public String getName() {
return this.name;
    }
private String recipeSteps;

public void setRecipeSteps(String value) {
this.recipeSteps = value;
    }
public String getRecipeSteps() {
return this.recipeSteps;
    }
private String rating;

public void setRating(String value) {
this.rating = value;
    }
public String getRating() {
return this.rating;
    }
private int recipeID;

public void setRecipeID(int value) {
this.recipeID = value;
    }
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public int getRecipeID() {
return this.recipeID;
       }
   }
