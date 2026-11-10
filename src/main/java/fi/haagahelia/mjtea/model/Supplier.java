package fi.haagahelia.mjtea.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// Supplier = nhà cung cấp nguyên liệu (ví dụ: hãng trà, hãng cà phê)
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên nhà cung cấp
    private String name;

    // Email hoặc số điện thoại liên hệ để đặt hàng
    private String contact;

    // Một Supplier cung cấp NHIỀU Ingredient.
    // mappedBy = "supplier": phía Ingredient giữ cột khóa ngoại.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    @JsonIgnore
    private List<Ingredient> ingredients;

    public Supplier() {
    }

    public Supplier(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}