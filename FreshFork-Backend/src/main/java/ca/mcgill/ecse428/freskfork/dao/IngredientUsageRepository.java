package ca.mcgill.ecse428.freskfork.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freskfork.model.IngredientUsage;

public interface IngredientUsageRepository extends CrudRepository<IngredientUsage, Integer> {

}
