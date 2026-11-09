package fi.haagahelia.mjtea.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.mjtea.model.Ingredient;
import fi.haagahelia.mjtea.model.IngredientRepository;
import fi.haagahelia.mjtea.model.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class IngredientController {

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Trang danh sách nguyên liệu
    @GetMapping("/ingredientlist")
    public String ingredientList(Model model) {
        model.addAttribute("ingredients", repository.findAll());
        return "ingredientlist";
    }

    // Mở form thêm nguyên liệu mới
    @GetMapping("/add")
    public String addIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addingredient";
    }

    // Lưu nguyên liệu (dùng chung cho thêm mới và sửa)
    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute Ingredient ingredient) {
        repository.save(ingredient);
        return "redirect:ingredientlist";
    }

    // Xóa nguyên liệu theo id
    // Chỉ ADMIN mới xóa được
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
        return "editingredient";
    }
}