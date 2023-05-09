package ru.clevertec.ecl.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dto.CertificateListDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.exception.RepositoryException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository class for certificates with <b>CRUD</b> operations
 *
 * @author Yuryeu Andrei
 */
@Repository
@RequiredArgsConstructor
public class CertificateRepository {
    private static final String ID_COLUMN = "id";

    private static final String SELECT_BY_ID_QUERY = "from GiftCertificate c left join fetch c.tags where c.id=:id";
    private static final String SELECT_ALL_QUERY = """
            from GiftCertificate c left join fetch c.tags t
            where (:tagName is null or t.name =:tagName)
                and (:partOfName is null or c.name like cast('%' || :partOfName || '%' as string))
                and (:partOfDescription is null or c.description like cast('%' || :partOfDescription || '%' as string))
            ORDER BY
                CASE WHEN :dateSortingOrder = 'asc' THEN c.createDate END ASC,
                CASE WHEN :dateSortingOrder = 'desc' THEN c.createDate END DESC,
                CASE WHEN :nameSortingOrder = 'asc' THEN c.name END ASC,
                CASE WHEN :nameSortingOrder = 'desc' THEN c.name END DESC
            """;
    private final SessionFactory sessionFactory;

    public GiftCertificate findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<GiftCertificate> query = session.createQuery(SELECT_BY_ID_QUERY, GiftCertificate.class)
                    .setParameter(ID_COLUMN, id);
            return query.getSingleResult();
        } catch (Exception ex) {
            throw new RepositoryException("Certificate was not found[" + ex.getMessage() + "]");
        }
    }

    /**
     * Repository method, used for finding Certificates by given conditions
     *
     * @param tagName           used for filtering(can be null), defines the Tag name,
     *                          which must be present in resulting CertificateListDtos
     * @param partOfName        used for filtering(can be null), defines part of giftCertificate name,
     *                          which must be present in resulting CertificateListDtos
     * @param partOfDescription used for filtering(can be null), defines part of giftCertificate description,
     *                          which must be present in resulting CertificateListDtos
     * @param dateSortingOrder  used for sorting(can be null), defines the order of date sorting
     * @param nameSortingOrder  used for sorting(can be null), defines the order of name sorting
     * @param limit             used for pagination, defines the number of tags on the page
     * @param offset            used for pagination, defines the number of tags to be skipped (from the beginning)
     * @return returns found <b>Certificates</b> by given conditions(it may be empty list)
     * @see CertificateListDto
     */
    public List<GiftCertificate> findAll(
            String tagName,
            String partOfName,
            String partOfDescription,
            String dateSortingOrder,
            String nameSortingOrder,
            int limit,
            int offset
    ) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<GiftCertificate> query = session.createQuery(SELECT_ALL_QUERY, GiftCertificate.class)
                    .setParameter("tagName", tagName)
                    .setParameter("partOfName", partOfName)
                    .setParameter("partOfDescription", partOfDescription)
                    .setParameter("dateSortingOrder", dateSortingOrder)
                    .setParameter("nameSortingOrder", nameSortingOrder);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception ex) {
            throw new RepositoryException("Certificates were not found[" + ex.getMessage() + "]");
        }
    }

    public GiftCertificate add(GiftCertificate element) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.persist(element);
                session.getTransaction().commit();
                return element;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Certificate was not added[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository method, which updates Certificate(accepts the Certificate entity, which can contain null fields, it means
     * that these fields must not be updated). Fields, which mustn't be updated are taken from actual Certificate in DB
     * before updating
     *
     * @param element contains the id of target entity and fields to be updated
     *                (fields which must not be updated are null)
     * @return returns <b>boolean</b> value (only in case of successful call)
     * @throws RepositoryException in case of failure
     */
    public boolean update(GiftCertificate element) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                GiftCertificate giftCertificate = findById(element.getId());
                element.setName(element.getName() == null ? giftCertificate.getName() : element.getName());
                element.setDescription(
                        element.getDescription() == null ? giftCertificate.getDescription() : element.getDescription()
                );
                element.setPrice(element.getPrice() == null ? giftCertificate.getPrice() : element.getPrice());
                element.setDuration(element.getDuration() == null ? giftCertificate.getDuration() : element.getDuration());
                element.setPrice(element.getPrice() == null ? giftCertificate.getPrice() : element.getPrice());
                element.setCreateDate(giftCertificate.getCreateDate());
                element.setLastUpdateDate(LocalDateTime.now());
                element.setTags(element.getTags().size() == 0 ? giftCertificate.getTags() : element.getTags());
                session.merge(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Certificate was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository method, which deletes Certificate
     *
     * @param id id of the entity to be deleted
     * @return returns <b>boolean</b> value (only in case of successful call)
     * @throws RepositoryException in case of failure
     */
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                GiftCertificate element = session.get(GiftCertificate.class, id);
                deleteTagCertificateLinks(element);
                session.remove(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Certificate was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository private method, which deletes the links with tags of deleted giftCertificate
     *
     * @param giftCertificate id of the entity to be deleted
     */
    private void deleteTagCertificateLinks(GiftCertificate giftCertificate) {
        giftCertificate.getTags().forEach(tag -> tag.getCertificates().remove(giftCertificate));
    }
}
