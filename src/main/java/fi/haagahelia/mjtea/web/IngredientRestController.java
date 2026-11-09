package fi.haagahelia.mjtea.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.mjtea.model.Ingredient;
import fi.haagahelia.mjtea.model.IngredientRepository;

// REST controller — trả về JSON thay vì trang HTML
@RestController
public class IngredientRestController {

    @Autowired
    private IngredientRepository repository;

    // Trả về tất cả nguyên liệu dưới dạng JSON
    @GetMapping("/ingredients")
    public Iterable<Ingredient> getIngredients() {
        return repository.findAll();
    }

    // Trả về 1 nguyên liệu theo id
    @GetMapping("/ingredients/{id}")
    public Optional<Ingredient> getIngredient(@PathVariable("id") Long id) {
        return repository.findById(id);
    }
}