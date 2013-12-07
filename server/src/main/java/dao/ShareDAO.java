package dao;

import exception.BadRequestException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import jsonmodel.JsonAccess;
import model.Note;
import model.Share;
import model.ShareId;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class ShareDAO extends AbstractDAO<Share, JsonAccess> {

    public List<Note> allSharing(User user) {
        List<Note> notes = new ArrayList<Note>();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Note.class)
                    .add(Restrictions.eq("owner", user))
                    .add(Restrictions.eq("shared", Boolean.TRUE));
            notes = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return notes;
    }

    public List<Share> allShared(User user) {
        List<Share> notes = new ArrayList<Share>();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Share.class)
                    .add(Restrictions.eq("user", user));
            notes = (List<Share>) criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return notes;
    }

    public List<Share> allAccess(Note note) {
        List<Share> shares = new ArrayList<Share>();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Share.class)
                    .add(Restrictions.eq("note", note));
            shares = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return shares;
    }

    public Share byUserNote(User user, Note note) {
        Share share = null;
        try {
            Transaction tx = session.beginTransaction();
            share = (Share) session.get(Share.class, new ShareId(user.getId(), note.getId()));
            if (share == null) {
                throw new NotFoundException("The note is not shared with the user");
            }
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return share;
    }

    public List<Share> createAccess(List<JsonAccess> list, int noteId) {
        List<Share> shares = null;
        try {
            Transaction tx = session.beginTransaction();
            Note note = (Note) session.get(Note.class, noteId);
            if (note == null) {
                throw new BadRequestException("Note not found");
            }
            if (list == null || list.isEmpty()) {
                throw new BadRequestException("The list of users is empty");
            }
            for (JsonAccess json : list) {
                Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", json.getUser().getUsername()));
                if (criteria.list().size() != 1) {
                    throw new NotFoundException("User " + json.getUser().getUsername() + " not found");
                }

                User user = (User) criteria.list().get(0);
                if (note.getOwner().getId().equals(user.getId())) {
                    throw new BadRequestException("You cant share note with yourself");
                }

                Share exists = (Share) session.get(Share.class, new ShareId(user.getId(), note.getId()));
                if (exists != null) {
                    throw new BadRequestException("The note is already shared with the user " + json.getUser().getUsername());
                }

                note.setShared(Boolean.TRUE); //zmenime priznak sdileni

                Share share = new Share();
                share.setReadonly(json.getReadonly());
                share.setUser(user);
                share.setNote(note);
                session.save(share);
            }

            Criteria criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", note));
            shares = criteria.list();

            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return shares;
    }

    public List<Share> updateAccess(List<JsonAccess> list, int noteId) {
        List<Share> shares = null;
        try {
            Transaction tx = session.beginTransaction();
            Note note = (Note) session.get(Note.class, noteId);
            if (note == null) {
                throw new BadRequestException("Note not found");
            }
            //humus reseni
            Criteria criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", note));
            for (Share share : (List<Share>) criteria.list()) {
                session.delete(share);
            }

            if (list == null || list.isEmpty()) {
                note.setShared(Boolean.FALSE);
                session.update(note);
                tx.commit();
                return new ArrayList<Share>();
            }

            for (JsonAccess json : list) {
                criteria = session.createCriteria(User.class).add(Restrictions.eq("username", json.getUser().getUsername()));
                if (criteria.list().size() != 1) {
                    throw new NotFoundException("User " + json.getUser().getUsername() + " not found");
                }

                User user = (User) criteria.list().get(0);
                if (note.getOwner().getId().equals(user.getId())) {
                    throw new BadRequestException("You cant share note with yourself");
                }

                Share exists = (Share) session.get(Share.class, new ShareId(user.getId(), note.getId()));
                if (exists != null) {
                    throw new BadRequestException("The note is already shared with the user " + json.getUser().getUsername());
                }
                note.setShared(Boolean.TRUE); //zmenime priznak sdileni
                Share share = new Share();
                share.setReadonly(json.getReadonly());
                share.setUser(user);
                share.setNote(note);
                session.save(share);
            }

            criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", note));
            shares = criteria.list();

            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (BadRequestException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return shares;
    }

    public void unshare(Note noteId) {
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", noteId));
            for (Share share : (List<Share>) criteria.list()) {
                session.delete(share);
            }
            Note note = (Note) session.get(Note.class, noteId.getId());
            note.setShared(Boolean.FALSE);
            session.update(note);
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception " + e.getMessage());
        }
    }

    public Share byUserNote(int userId, int noteId) {
        Share share = null;
        try {
            Transaction tx = session.beginTransaction();
            share = (Share) session.get(Share.class, new ShareId(userId, noteId));
            if (share == null) {
                throw new NotFoundException("The note is not shared with the user");
            }
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception " + e.getMessage());
        }
        return share;
    }

    public Share signoff(User user, Note note) {
        Share share = null;
        try {
            Transaction tx = session.beginTransaction();
            share = (Share) session.get(Share.class, new ShareId(user.getId(), note.getId()));
            if (share == null) {
                throw new NotFoundException("The note is not shared with the user");
            }
            session.delete(share);

            note = (Note) session.get(Note.class, note.getId());
            Criteria criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", note));
            if (criteria.list().isEmpty()) {
                note.setShared(Boolean.FALSE);
                session.save(note);
            }
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception " + e.getMessage());
        }
        return share;
    }
}
