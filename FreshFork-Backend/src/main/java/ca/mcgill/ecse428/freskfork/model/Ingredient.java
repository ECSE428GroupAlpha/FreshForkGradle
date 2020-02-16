package ca.mcgill.ecse428.freskfork.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Ingredient{
private Set<IngredientUsage> ingredientUsage;

@OneToMany(mappedBy="ingredient")
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
@Id
public String getName() {
return this.name;
    }
private int calories;

public void setCalories(int value) {
this.calories = value;
    }
public int getCalories() {
return this.calories;
    }
private double carbs;

public void setCarbs(double value) {
this.carbs = value;
    }
public double getCarbs() {
return this.carbs;
    }
private int sugar;

public void setSugar(int value) {
this.sugar = value;
    }
public int getSugar() {
return this.sugar;
    }
private double fats;

public void setFats(double value) {
this.fats = value;
    }
public double getFats() {
return this.fats;
    }
private double iron;

public void setIron(double value) {
this.iron = value;
    }
public double getIron() {
return this.iron;
    }
private int quantity;

public void setQuantity(int value) {
this.quantity = value;
    }
public int getQuantity() {
return this.quantity;
    }
private String SIUnit;

public void setSIUnit(String value) {
this.SIUnit = value;
    }
public String getSIUnit() {
return this.SIUnit;
       }
   }
