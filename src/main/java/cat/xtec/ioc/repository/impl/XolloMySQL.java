package cat.xtec.ioc.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import cat.xtec.ioc.domain.Xollo;
import cat.xtec.ioc.repository.XolloRepository;

/**
 * Implementació JPA del repositori de xollos.
 *
 * Fa operacions CRUD sobre la taula "xollos" mitjançant JPQL i la unitat
 * de persistència "XollosPersistenceUnit".
 */
@Primary
@Repository
public class XolloMySQL implements XolloRepository {

    private EntityManager entityManager;

    public XolloMySQL() {
        entityManager = Persistence.createEntityManagerFactory("XollosPersistenceUnit").createEntityManager();
    }

    @Override
    public void addXollo(Xollo xollo) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(xollo);
        tx.commit();
    }

    @Override
    public Xollo getXolloByCodi(String codi) {
        TypedQuery<Xollo> query = entityManager.createQuery(
                "select object(o) from Xollo o where o.codi = :codi", Xollo.class);
        query.setParameter("codi", codi);
        List<Xollo> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void updateXollo(Xollo xollo) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        TypedQuery<Xollo> query = entityManager.createQuery(
                "select object(o) from Xollo o where o.codi = :codi", Xollo.class);
        query.setParameter("codi", xollo.getCodi());
        List<Xollo> results = query.getResultList();
        if (!results.isEmpty()) {
            Xollo xolloPersistent = results.get(0);
            xolloPersistent.setNumeroUnitats(xollo.getNumeroUnitats());
            xolloPersistent.setNumeroReserves(xollo.getNumeroReserves());
            xolloPersistent.setTitol(xollo.getTitol());
            xolloPersistent.setDescripcio(xollo.getDescripcio());
        }
        tx.commit();
    }

    @Override
    public List<Xollo> getAllXollos() {
        TypedQuery<Xollo> query = entityManager.createQuery(
                "select object(o) from Xollo o", Xollo.class);
        return query.getResultList();
    }
}
