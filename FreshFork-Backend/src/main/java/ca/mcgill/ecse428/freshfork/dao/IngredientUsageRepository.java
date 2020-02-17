package ca.mcgill.ecse428.freshfork.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.freshfork.model.IngredientUsage;

public interface IngredientUsageRepository extends CrudRepository<IngredientUsage, Integer> {

}
