package cz.fel.cvut.via.entities;

public class Carry {

	private String username;
	private Note note;
	private boolean readOnly;
	
	
	
	public Carry(String username, Note note, boolean readOnly) {
		this.username = username;
		this.note = note;
		this.readOnly = readOnly;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public Carry(String username, Note note) {
		this.username = username;
		this.note = note;
	}
	
	
	
}
