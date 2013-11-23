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

    public List<R> all() {
        return null;
    }

    public R byId(int id) {
        return null;
    }

    public R create(J entity) {
        return null;
    }

    public R update(J entity, int id) {
        return null;
    }

    public R delete(int id) {
        return null;
    }
}
