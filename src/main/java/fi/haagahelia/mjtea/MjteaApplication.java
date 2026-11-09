package fi.haagahelia.mjtea;

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

@SpringBootApplication
public class MjteaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MjteaApplication.class, args);
	}

	// CommandLineRunner chạy 1 lần khi app khởi động - dùng để nạp dữ liệu mẫu
	@Bean
	public CommandLineRunner mjteaRunner(IngredientRepository ingredientRepository,
			CategoryRepository categoryRepository,
			AppUserRepository userRepository) {
		return (args) -> {
			// Chỉ nạp mẫu khi database trống (tránh lặp khi khởi động lại)
			if (ingredientRepository.count() == 0) {

				// Tạo các loại nguyên liệu
				Category tea = new Category("Tea");
				Category coffee = new Category("Coffee");
				Category bakery = new Category("Bakery");
				Category packaging = new Category("Packaging");
				categoryRepository.save(tea);
				categoryRepository.save(coffee);
				categoryRepository.save(bakery);
				categoryRepository.save(packaging);

				// Tạo nguyên liệu mẫu: name, quantity, unit, price, lowStockThreshold, category
				ingredientRepository.save(new Ingredient("Black Tea Leaves", 8.0, "kg", 25.0, 3.0, tea));
				ingredientRepository.save(new Ingredient("Oolong Tea", 2.0, "kg", 30.0, 3.0, tea)); // sẽ cảnh báo (2 <
																									// 3)
				ingredientRepository.save(new Ingredient("Coffee Beans", 15.0, "kg", 18.0, 5.0, coffee));
				ingredientRepository.save(new Ingredient("Oat Milk", 4.0, "liter", 2.5, 6.0, coffee)); // cảnh báo (4 <
																										// 6)
				ingredientRepository.save(new Ingredient("Flour", 20.0, "kg", 1.2, 5.0, bakery));
				ingredientRepository.save(new Ingredient("Butter", 3.0, "kg", 8.0, 4.0, bakery)); // cảnh báo (3 < 4)
				ingredientRepository.save(new Ingredient("Paper Cups", 500.0, "pcs", 0.05, 100.0, packaging));

				// Thêm 2 user, hash mật khẩu bằng BCrypt
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userRepository.save(new AppUser("staff",
						encoder.encode("staff"), "staff@mjtea.fi", "STAFF"));
				userRepository.save(new AppUser("manager",
						encoder.encode("manager"), "manager@mjtea.fi", "MANAGER"));
			}
		};
	}
}