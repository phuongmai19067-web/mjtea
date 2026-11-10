package fi.haagahelia.mjtea;

import java.time.LocalDate;
import fi.haagahelia.mjtea.model.AppUser;
import fi.haagahelia.mjtea.model.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.mjtea.model.Category;
import fi.haagahelia.mjtea.model.CategoryRepository;
import fi.haagahelia.mjtea.model.Ingredient;
import fi.haagahelia.mjtea.model.IngredientRepository;
import fi.haagahelia.mjtea.model.Supplier;
import fi.haagahelia.mjtea.model.SupplierRepository;

@SpringBootApplication
public class MjteaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MjteaApplication.class, args);
	}

	// CommandLineRunner chạy 1 lần khi app khởi động - dùng để nạp dữ liệu mẫu
	@Bean
	public CommandLineRunner mjteaRunner(IngredientRepository ingredientRepository,
			CategoryRepository categoryRepository,
			SupplierRepository supplierRepository,
			AppUserRepository userRepository) {
		return (args) -> {
			if (ingredientRepository.count() == 0) {

				// Tạo category
				Category tea = new Category("Tea");
				Category coffee = new Category("Coffee");
				Category bakery = new Category("Bakery");
				Category packaging = new Category("Packaging");
				categoryRepository.save(tea);
				categoryRepository.save(coffee);
				categoryRepository.save(bakery);
				categoryRepository.save(packaging);

				// Tạo supplier (nhà cung cấp)
				Supplier teaHouse = new Supplier("Helsinki Tea House", "orders@teahouse.fi");
				Supplier beanCo = new Supplier("Nordic Bean Co", "sales@nordicbean.fi");
				Supplier bakeSupply = new Supplier("Bakery Supplies Oy", "info@bakesupply.fi");
				supplierRepository.save(teaHouse);
				supplierRepository.save(beanCo);
				supplierRepository.save(bakeSupply);

				// Tạo ingredient: name, quantity, unit, price, lowStockThreshold, category
				// (phần gán supplier làm riêng ở dưới để code dễ đọc)
				Ingredient blackTea = new Ingredient("Black Tea Leaves", 8.0, "kg", 25.0, 3.0,
						LocalDate.now().plusMonths(6), tea);
				Ingredient oolong = new Ingredient("Oolong Tea", 2.0, "kg", 30.0, 3.0, LocalDate.now().plusMonths(4),
						tea);
				Ingredient beans = new Ingredient("Coffee Beans", 15.0, "kg", 18.0, 5.0, LocalDate.now().plusMonths(3),
						coffee);
				Ingredient oatMilk = new Ingredient("Oat Milk", 4.0, "liter", 2.5, 6.0, LocalDate.now().plusDays(3),
						coffee); // sắp hết hạn
				Ingredient flour = new Ingredient("Flour", 20.0, "kg", 1.2, 5.0, LocalDate.now().plusMonths(8), bakery);
				Ingredient butter = new Ingredient("Butter", 3.0, "kg", 8.0, 4.0, LocalDate.now().plusDays(5), bakery); // sắp
																														// hết
																														// hạn
				Ingredient cups = new Ingredient("Paper Cups", 500.0, "pcs", 0.05, 100.0, LocalDate.now().plusYears(2),
						packaging);
				// Gán nhà cung cấp cho từng nguyên liệu
				blackTea.setSupplier(teaHouse);
				oolong.setSupplier(teaHouse);
				beans.setSupplier(beanCo);
				oatMilk.setSupplier(beanCo);
				flour.setSupplier(bakeSupply);
				butter.setSupplier(bakeSupply);
				cups.setSupplier(bakeSupply);

				ingredientRepository.save(blackTea);
				ingredientRepository.save(oolong);
				ingredientRepository.save(beans);
				ingredientRepository.save(oatMilk);
				ingredientRepository.save(flour);
				ingredientRepository.save(butter);
				ingredientRepository.save(cups);

				// Users
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userRepository.save(new AppUser("staff",
						encoder.encode("staff"), "staff@mjtea.fi", "STAFF"));
				userRepository.save(new AppUser("manager",
						encoder.encode("manager"), "manager@mjtea.fi", "MANAGER"));
			}
		};
	}
}