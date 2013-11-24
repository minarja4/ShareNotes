package dao;

import org.hibernate.Session;

public abstract class AbstractDAO<R, J> {

    protected Session session = null;

    public AbstractDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
