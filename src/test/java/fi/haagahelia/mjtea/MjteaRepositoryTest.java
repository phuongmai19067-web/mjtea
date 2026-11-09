package fi.haagahelia.mjtea;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.mjtea.model.AppUser;
import fi.haagahelia.mjtea.model.AppUserRepository;
import fi.haagahelia.mjtea.model.Category;
import fi.haagahelia.mjtea.model.CategoryRepository;
import fi.haagahelia.mjtea.model.Ingredient;
import fi.haagahelia.mjtea.model.IngredientRepository;

@SpringBootTest
public class MjteaRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository userRepository;

    // ---------- INGREDIENT ----------

    // Create: lưu nguyên liệu mới, id khác null = đã lưu thành công
    @Test
    public void createNewIngredient() {
        Category cat = new Category("TestCat");
        categoryRepository.save(cat);
        Ingredient ing = new Ingredient("Test Tea", 10.0, "kg", 5.0, 2.0, cat);
        ingredientRepository.save(ing);
        assertThat(ing.getId()).isNotNull();
    }

    // Search: tìm tất cả, phải có dữ liệu mẫu
    @Test
    public void findAllIngredientsShouldReturnList() {
        List<Ingredient> list = (List<Ingredient>) ingredientRepository.findAll();
        assertThat(list).isNotEmpty();
    }

    // Delete: lưu rồi xóa, tìm lại không thấy
    @Test
    public void deleteIngredient() {
        Category cat = new Category("TempCat");
        categoryRepository.save(cat);
        Ingredient ing = new Ingredient("To Delete", 1.0, "kg", 1.0, 1.0, cat);
        ingredientRepository.save(ing);
        Long id = ing.getId();
        ingredientRepository.deleteById(id);
        Optional<Ingredient> deleted = ingredientRepository.findById(id);
        assertThat(deleted).isEmpty();
    }

    // ---------- CATEGORY ----------

    @Test
    public void createNewCategory() {
        Category cat = new Category("Spices");
        categoryRepository.save(cat);
        assertThat(cat.getId()).isNotNull();
    }

    @Test
    public void deleteCategory() {
        Category cat = new Category("TempCategory");
        categoryRepository.save(cat);
        Long id = cat.getId();
        categoryRepository.deleteById(id);
        assertThat(categoryRepository.findById(id)).isEmpty();
    }

    // ---------- APPUSER ----------

    // Create + Search theo username
    @Test
    public void createAndFindUser() {
        AppUser u = new AppUser("testuser", "pass", "test@mjtea.fi", "STAFF");
        userRepository.save(u);
        Optional<AppUser> found = userRepository.findByUsername("testuser");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@mjtea.fi");
    }

    @Test
    public void deleteUser() {
        AppUser u = new AppUser("deluser", "pass", "del@mjtea.fi", "STAFF");
        userRepository.save(u);
        Long id = u.getId();
        userRepository.deleteById(id);
        assertThat(userRepository.findById(id)).isEmpty();
    }
}