package dao;

import com.sun.jersey.api.NotFoundException;
import java.util.List;
import javax.ws.rs.BadRequestException;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import utils.HashUtil;

public class UserDAO extends AbstractDAO<User> {

    @Override
    public List<User> all() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User byId(int id) {
        User user = null;
        try {
            Transaction tx = session.beginTransaction();
            user = (User) session.get(User.class, id);
            tx.commit();
            if (user == null) {
                throw new NotFoundException("User not found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception");
        }
        return user;
    }

    @Override
    public User create(User user) {
        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", user.getEmail()));
            if (criteria.list().size() > 0) {
                throw new BadRequestException("User with same email already exists");
            }
            user.setPassword(HashUtil.sha256(user.getPassword()));
            user.setToken(HashUtil.sha256(Long.toString(System.currentTimeMillis())));
            session.save(user);
            tx.commit();
        } catch (BadRequestException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception");
        }
        return user;
    }

    @Override
    public User update(User entity, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
