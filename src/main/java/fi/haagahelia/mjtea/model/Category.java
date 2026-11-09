package fi.haagahelia.mjtea.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// Category = loại nguyên liệu (Tea, Coffee, Bakery, Dairy, Packaging...)
// @Entity đánh dấu đây là một bảng trong database
@Entity
public class Category {
    // Khóa chính, database tự tăng id (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên loại, ví dụ "Tea"
    private String name;

    // Quan hệ MỘT Category có NHIỀU Ingredient.
    // mappedBy = "category": phía Ingredient giữ cột khóa ngoại.
    // @JsonIgnore: khi xuất JSON thì bỏ qua danh sách này để tránh lặp vô hạn.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnore
    private List<Ingredient> ingredients;

    // Constructor rỗng - JPA bắt buộc phải có
    public Category() {
    }

    // Constructor tiện tạo nhanh: new Category("Tea")
    public Category(String name) {
        this.name = name;
    }

    // Getter & setter để đọc/ghi dữ liệu
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
