package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<GiftCertificate, Long> {
    @Query("from GiftCertificate c left join fetch c.tags where c.id =:id")
    Optional<GiftCertificate> findById(Long id);

    @Query("""
              from GiftCertificate c left join fetch c.tags t
              where (:tagName is null or t.name =:tagName)
                  and (:partOfName is null or c.name like cast('%' || :partOfName || '%' as string))
                  and (:partOfDescription is null or c.description like cast('%' || :partOfDescription || '%' as string))
            """
    )
    List<GiftCertificate> findAllWithFilter(String tagName, String partOfName, String partOfDescription, Pageable pageable);
}
