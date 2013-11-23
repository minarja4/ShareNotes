/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author nix
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//
//        dao.create(new User("test2@cvut.cz", "abcd", "token"));

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

//        User user = (User) session.get(User.class, 4);
//        Note note = (Note) session.get(Note.class, 1);
//        
//        Share share = new Share(user, note, true);
        

//        Note note = new Note("karel", "blabla", user);
        
        Share share = (Share) session.get(Share.class, new ShareId(1, 1));


//        session.save(share);
        tx.commit();
        session.close();
//        
//        User u = note.getOwner();
        System.out.println(share.getUser().getEmail());


    }
}
