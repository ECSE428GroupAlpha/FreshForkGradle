package ca.mcgill.ecse428.freskfork.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Diet{
private Set<Users> Users;

@ManyToMany(mappedBy="diet", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
public Set<Users> getUsers() {
   return this.Users;
}

public void setUsers(Set<Users> Userss) {
   this.Users = Userss;
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
