package dao;

import com.sun.jersey.api.NotFoundException;
import exception.BadRequestException;
import exception.NotAuthorizedException;
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

    public User byUsername(JsonUser json) {
        return byUsername(json.getUsername());
    }

    public User byUsername(String username) {
        User user = null;
        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", username));
            if (criteria.list().size() != 1) {
                throw new NotFoundException("User not found");
            }
            user = (User) criteria.list().get(0);
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return user;
    }

    public User create(JsonUser user) {
        User newUser = null;
        if (!ValidateUtil.username(user.getUsername())) {
            throw new BadRequestException("Username has incorrect format");
        }
        if (!ValidateUtil.email(user.getEmail())) {
            throw new BadRequestException("User email has incorrect format");
        }
        if (!ValidateUtil.password(user.getPassword())) {
            throw new BadRequestException("User password is not strong enough");
        }
        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", user.getUsername()));
            if (criteria.list().size() > 0) {
                throw new BadRequestException("User with same username already exists");
            }
            newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            //TODO: ma se heslo posilat jiz zahashovane nebo ne?
            newUser.setPassword(user.getPassword()); //HashUtil.sha256(user.getPassword()));
            newUser.setToken(HashUtil.sha256(user.getUsername() + "" + System.currentTimeMillis()));
            session.save(newUser);
            tx.commit();
        } catch (WebApplicationException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return newUser;
    }

    public User login(JsonUser json) {
        User user = byUsername(json.getUsername());

        //mozna zmenit token?

        //TODO: ma se heslo posilat jiz zahashovane nebo ne?
        if (!user.getPassword().equals(json.getPassword())) {
            throw new NotAuthorizedException("Password is incorrect");
        }
        return user;
    }
}
