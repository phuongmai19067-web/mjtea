package fi.haagahelia.mjtea.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Ingredient = một nguyên liệu trong kho (ví dụ: sữa, trà đen, bột mì, cốc nhựa)
@Entity
public class Ingredient {

    // Khóa chính, tự tăng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên nguyên liệu, ví dụ "Oat Milk"
    private String name;

    // Số lượng đang tồn trong kho
    private double quantity;

    // Đơn vị tính, ví dụ "liter", "kg", "pcs"
    private String unit;

    // Giá mỗi đơn vị (euro)
    private double price;

    // Ngưỡng cảnh báo: nếu quantity thấp hơn số này thì coi là "sắp hết"
    private double lowStockThreshold;

    // Quan hệ NHIỀU Ingredient thuộc về MỘT Category.
    // @JoinColumn: tạo cột "categoryid" trong bảng ingredient để nối tới category.
    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    // Constructor rỗng — JPA bắt buộc
    public Ingredient() {
    }

    // Constructor đầy đủ để tạo nhanh dữ liệu mẫu
    public Ingredient(String name, double quantity, String unit, double price,
            double lowStockThreshold, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.lowStockThreshold = lowStockThreshold;
        this.category = category;
    }

    // Phương thức tiện ích: trả về true nếu nguyên liệu sắp hết.
    // Dùng để tô màu đỏ dòng cảnh báo trên giao diện.
    public boolean isLowStock() {
        return quantity < lowStockThreshold;
    }

    // Getter & setter
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(double lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}