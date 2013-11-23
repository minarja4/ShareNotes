package dao;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author nix
 */
public abstract class AbstractDAO<T> {

    protected Session session = null;

    public AbstractDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public abstract List<T> all();
    
    public abstract T byId(int id);

    public abstract T create(T entity);

    public abstract T update(T entity, int id);

    public abstract T delete(int id);
}
