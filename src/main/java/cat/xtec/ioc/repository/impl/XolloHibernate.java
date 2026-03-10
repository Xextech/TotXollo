package cat.xtec.ioc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.xtec.ioc.domain.Xollo;
import cat.xtec.ioc.repository.XolloRepository;

// Implementació Hibernate del repositori de xollos.
@Repository
public class XolloHibernate implements XolloRepository {

    @Autowired
    private SessionFactory sessionFactory; // Injecció de la SessionFactory configurada a HibernateConfiguration

    // Fa operacions CRUD sobre la taula Xollo mitjançant Hibernate, utilitzant la SessionFactory per gestionar
    @Override
    @Transactional
    public void addXollo(Xollo xollo) {
        sessionFactory.getCurrentSession().save(xollo);
    }

    // Recupera un xollo per codi utilitzant Hibernate Criteria
    @Override
    @Transactional
    public Xollo getXolloByCodi(String codi) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Xollo.class);
        criteria.add(Restrictions.eq("codi", codi));
        return (Xollo) criteria.uniqueResult();
    }

    // Actualitza un xollo existent utilitzant Hibernate
    @Override
    @Transactional
    public void updateXollo(Xollo xollo) {
        sessionFactory.getCurrentSession().update(xollo);
    }

    // Recupera tots els xollos utilitzant Hibernate Criteria
    @Override
    @Transactional
    public List<Xollo> getAllXollos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Xollo.class);
        return criteria.list();
    }
}
