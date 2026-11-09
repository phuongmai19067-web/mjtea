package fi.haagahelia.mjtea.model;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    // Tìm user theo username (dùng khi đăng nhập)
    Optional<AppUser> findByUsername(String username);
}