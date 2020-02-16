package ca.mcgill.ecse428.freskfork.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Diet{
private Set<User> user;

@ManyToMany(mappedBy="diet")
public Set<User> getUser() {
   return this.user;
}

public void setUser(Set<User> users) {
   this.user = users;
}

private Set<Recipe> recipe;

@ManyToMany
public Set<Recipe> getRecipe() {
   return this.recipe;
}

public void setRecipe(Set<Recipe> recipes) {
   this.recipe = recipes;
}

private String name;

public void setName(String value) {
this.name = value;
    }
@Id
public String getName() {
return this.name;
       }
   }
