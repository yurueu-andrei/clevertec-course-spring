package ru.clevertec.ecl.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.RepositoryException;

import java.util.List;

/**
 * Repository class for tags with <b>CRUD</b> operations
 *
 * @author Yuryeu Andrei
 */
@Repository
@RequiredArgsConstructor
public class TagRepository {
    private static final String ID_COLUMN = "id";
    private static final String TAG_NAME_COLUMN = "name";

    private static final String SELECT_BY_ID_QUERY = "from Tag t left join fetch t.certificates where t.id=:id";
    private static final String SELECT_BY_NAME_QUERY = "from Tag t where t.name=:name";
    private static final String SELECT_ALL_QUERY = "from Tag";
    private final SessionFactory sessionFactory;

    public Tag findById(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery(SELECT_BY_ID_QUERY, Tag.class)
                    .setParameter(ID_COLUMN, id);
            return query.getSingleResult();
        } catch (Exception ex) {
            throw new RepositoryException("Tag was not found[" + ex.getMessage() + "]");
        }
    }

    public Tag findByName(String name) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery(SELECT_BY_NAME_QUERY, Tag.class)
                    .setParameter(TAG_NAME_COLUMN, name);
            return query.getSingleResultOrNull();
        } catch (Exception ex) {
            throw new RepositoryException("Tag was not found[" + ex.getMessage() + "]");
        }
    }

    public List<Tag> findAll(int limit, int offset) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery(SELECT_ALL_QUERY, Tag.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception ex) {
            throw new RepositoryException("Tags were not found[" + ex.getMessage() + "]");
        }
    }

    public Tag add(Tag element) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                session.persist(element);
                session.getTransaction().commit();
                return element;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Tag was not added[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository method, which updates Tag using Criteria API
     *
     * @param element Tag entity, which contains the fields to be updated
     * @return returns <b>boolean</b> value (only in case of successful call)
     * @throws RepositoryException in case of failure
     */
    public boolean update(Tag element) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaUpdate<Tag> update = cb.createCriteriaUpdate(Tag.class);
                Root<Tag> root = update.from(Tag.class);

                update.set(root.get("name"), element.getName())
                        .where(cb.equal(root.get("id"), element.getId()));
                session.createMutationQuery(update).executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Tag was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository method, which deletes Tag
     *
     * @param id id of the entity to be deleted
     * @return returns <b>boolean</b> value (only in case of successful call)
     * @throws RepositoryException in case of failure
     */
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction().begin();
                Tag element = session.get(Tag.class, id);
                deleteCertificateTagLinks(element);
                session.remove(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Tag was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    /**
     * Repository private method, which deletes the links with certificates of deleted tag
     *
     * @param tag id of the entity to be deleted
     */
    private void deleteCertificateTagLinks(Tag tag) {
        tag.getGiftCertificates().forEach(certificate -> certificate.getTags().remove(tag));
    }
}
