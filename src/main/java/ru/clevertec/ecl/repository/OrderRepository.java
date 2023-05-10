package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
                from Order o left join fetch o.user u left join fetch o.certificate c left join fetch c.tags
                where o.id = :id
            """
    )
    Optional<Order> findById(Long id);

    @Query("""
                from Order o left join fetch o.user u left join fetch o.certificate c left join fetch c.tags
                where u.id = :userId
            """
    )
    List<Order> findByUserId(Long userId, Pageable pageable);

    @Query(value = """
                from Order o left join fetch o.certificate c left join fetch c.tags left join fetch o.user u
            """,
            countQuery = "select count(t) from Order t left join t.user left join t.certificate c left join c.tags"
    )
    Page<Order> findAllOrders(Pageable pageable);
}
