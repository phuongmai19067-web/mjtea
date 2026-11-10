package fi.haagahelia.mjtea.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

// Repository cho Ingredient — kế thừa CrudRepository nên có sẵn CRUD đầy đủ
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    // Tìm theo tên nguyên liệu HOẶC tên category (không phân biệt hoa thường, khớp
    // một phần)
    List<Ingredient> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(
            String name, String categoryName);
}