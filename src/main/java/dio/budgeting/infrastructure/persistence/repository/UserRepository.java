package dio.budgeting.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import dio.budgeting.infrastructure.persistence.entity.UserEntity;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
}
