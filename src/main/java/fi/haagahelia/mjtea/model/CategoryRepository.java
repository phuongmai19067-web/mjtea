package fi.haagahelia.mjtea.model;

import org.springframework.data.repository.CrudRepository;

// Repository cho Category — Spring tự sinh sẵn các hàm save, findAll, findById, delete...
public interface CategoryRepository extends CrudRepository<Category, Long> {
}