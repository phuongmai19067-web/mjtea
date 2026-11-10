package fi.haagahelia.mjtea.web;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.mjtea.model.Ingredient;
import fi.haagahelia.mjtea.model.IngredientRepository;
import fi.haagahelia.mjtea.model.SupplierRepository;
import fi.haagahelia.mjtea.model.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class IngredientController {

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Trang danh sách nguyên liệu
    // Trang danh sách — hỗ trợ tìm kiếm (search) và lọc hàng sắp hết (lowstock)
    @GetMapping("/ingredientlist")
    public String ingredientList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String filter,
            Model model) {

        List<Ingredient> ingredients;

        if (search != null && !search.isEmpty()) {
            // Tìm theo tên nguyên liệu HOẶC tên category
            ingredients = repository.findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(search, search);
        } else {
            ingredients = (List<Ingredient>) repository.findAll();
        }

        // Nếu chọn lọc "lowstock" → chỉ giữ những món sắp hết kho
        if ("lowstock".equals(filter)) {
            ingredients = ingredients.stream()
                    .filter(Ingredient::isLowStock)
                    .toList();
        }

        model.addAttribute("ingredients", ingredients);
        return "ingredientlist";
    }

    // Mở form thêm nguyên liệu mới
    @GetMapping("/add")
    public String addIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "addingredient";
    }

    // Lưu nguyên liệu (dùng chung cho thêm mới và sửa)
    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute Ingredient ingredient) {
        repository.save(ingredient);
        return "redirect:ingredientlist";
    }

    // Xóa nguyên liệu theo id
    // Chỉ MANAGER mới xóa được
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable("id") Long ingredientId) {
        repository.deleteById(ingredientId);
        return "redirect:../ingredientlist";
    }

    // Mở nguyên liệu hiện tại trong trang sửa
    @GetMapping("/edit/{id}")
    public String editIngredient(@PathVariable("id") Long ingredientId, Model model) {
        model.addAttribute("ingredient", repository.findById(ingredientId).get());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "editingredient";
    }
}