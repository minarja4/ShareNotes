package cz.fel.cvut.via.entities;

import java.io.Serializable;

public class Note implements Serializable {

	private int id;
	private String name;
	private String note;
	private int version;
//	private boolean shared;
	private String owner;
	
	private transient boolean cached = false;
	private transient Long dbId;
	
	public Note(int id, String name, String note, int version, String owner){
		
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
	public boolean isCached() {
		return cached;
	}
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	@Override
	public String toString() {
		return "name: " + name + ", note: " + note + ", owner: " + owner; 
	}
	public Long getDbId() {
		return dbId;
	}
	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	
	
	
}

