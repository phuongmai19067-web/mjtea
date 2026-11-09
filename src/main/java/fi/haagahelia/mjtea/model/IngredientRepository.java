package fi.haagahelia.mjtea.model;

import org.springframework.data.repository.CrudRepository;

// Repository cho Ingredient — kế thừa CrudRepository nên có sẵn CRUD đầy đủ
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}