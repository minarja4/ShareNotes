package dao;

import exception.BadRequestException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import jsonmodel.JsonNote;
import model.Note;
import model.Share;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class NoteDAO extends AbstractDAO<Note, JsonNote> {

    public List<Note> all(User user) {
        List<Note> notes = new ArrayList<Note>();
        try {
            Transaction tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Note.class)
                    .add(Restrictions.eq("owner", user));
            notes = criteria.list();
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return notes;
    }

    public Note byId(int id) {
        Note note = null;
        try {
            Transaction tx = session.beginTransaction();
            note = (Note) session.get(Note.class, id);
            tx.commit();
            if (note == null) {
                throw new NotFoundException("Note not found");
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return note;
    }

    public Note create(User user, JsonNote json) {
        Note newNote = null;
        try {
            Transaction tx = session.beginTransaction();

            newNote = new Note();
            newNote.setName(json.getName());
            newNote.setNote(json.getNote());
            newNote.setOwner(user);
            newNote.setVersion(1);
            newNote.setShared(Boolean.FALSE);

            session.save(newNote);
            tx.commit();
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception: " + e.getMessage());
        }
        return newNote;
    }

    public Note update(JsonNote json, int id) {
        Note note = null;
        try {
            Transaction tx = session.beginTransaction();
            note = (Note) session.get(Note.class, id);
            if (note == null) {
                throw new NotFoundException("Note not found");
            }
            //zmenit lze jen nazev, poznamku 
            note.setName(json.getName());
            note.setNote(json.getNote());
            note.setVersion(1 + note.getVersion());
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception " + e.getMessage());
        }
        return note;
    }

    public Note delete(int id) {
        Note note = null;
        try {
            Transaction tx = session.beginTransaction();
            note = (Note) session.get(Note.class, id);
            if (note == null) {
                throw new NotFoundException("Note not found");
            }
            Criteria criteria = session.createCriteria(Share.class).add(Restrictions.eq("note", note));
            for (Share share : (List<Share>) criteria.list()) {
                session.delete(share);
            }
            session.delete(note);
            tx.commit();
        } catch (NotFoundException e) {
            throw e;
        } catch (HibernateException e) {
            throw new BadRequestException("Hibernate exception " + e.getMessage());
        }
        return note;
    }
}
