package dao;

import com.sun.jersey.api.NotFoundException;
import exception.BadRequestException;
import exception.NotAuthorizedException;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import jsonmodel.JsonUser;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import utils.HashUtil;
import utils.ValidateUtil;

public class UserDAO extends AbstractDAO<User, JsonUser> {

    @Override
    public List<User> all() {
        return null;
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

    public User byEmail(String email) {
        User user = null;
        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", email));
            if (criteria.list().size() != 1) {
                throw new NotFoundException("User not found");
            }
            user = (User) criteria.list().get(0);
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception");
        }
        return user;
    }

    @Override
    public User create(JsonUser user) {
        User newUser = null;
        if (!ValidateUtil.email(user.getEmail())) {
            throw new BadRequestException("User email has incorrect format");
        }
        if (!ValidateUtil.password(user.getPassword())) {
            throw new BadRequestException("User password is not strong enough");
        }
        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", user.getEmail()));
            if (criteria.list().size() > 0) {
                throw new BadRequestException("User with same email already exists");
            }
            newUser = new User();
            newUser.setEmail(user.getEmail());
            //TODO: ma se heslo posilat jiz zahashovane nebo ne?
            newUser.setPassword(user.getPassword()); //HashUtil.sha256(user.getPassword()));
            newUser.setToken(HashUtil.sha256(user.getEmail() + "" + System.currentTimeMillis()));
            session.save(newUser);
            tx.commit();
        } catch (WebApplicationException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception");
        }
        return newUser;
    }

    @Override
    public User update(JsonUser entity, int id) {
        return null;
    }

    @Override
    public User delete(int id) {
        return null;
    }

    public User login(JsonUser json) {
        User user = byEmail(json.getEmail());
        
        //mozna zmenit token?

        //TODO: ma se heslo posilat jiz zahashovane nebo ne?
        if (!user.getPassword().equals(json.getPassword())) {
            throw new NotAuthorizedException("Password is incorrect");
        }
        return user;
    }
}
