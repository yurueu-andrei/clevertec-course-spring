package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
                from User u
                left join fetch u.orders o 
                left join fetch o.certificate c 
                left join fetch c.tags
                where u.id = :id
            """
    )
    Optional<User> findById(Long id);

    @Query("""
            SELECT u.id
            FROM User u
            LEFT JOIN u.orders o
            GROUP BY u.id
            ORDER BY SUM(o.cost) DESC
            """
    )
    List<Long> findIdOfUserWithTheHighestCostOfAllOrders(Pageable pageable);
}
