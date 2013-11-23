package dao;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author nix
 */
public abstract class AbstractDAO<R, J> {

    protected Session session = null;

    public AbstractDAO() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public abstract List<R> all();
    
    public abstract R byId(int id);

    public abstract R create(J entity);

    public abstract R update(J entity, int id);

    public abstract R delete(int id);
}
