package cz.fel.cvut.via.entities;

public class Note {

	private int id;
	private String name;
	private String note;
	private int version;
//	private boolean shared;
	private String owner;
	
	public Note(int id, String name, String note, int version, String owner) {
		
		this.id = id;
		this.name = name;
		this.note = note;
		this.version = version;		
		this.owner = owner;
	}
	public Note() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	
	
	
}

