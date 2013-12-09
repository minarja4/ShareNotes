package cz.fel.cvut.via.entities;

import java.util.List;

public class SharesNoteCarry {

	private Note note;
	private List<Share> list;
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public List<Share> getList() {
		return list;
	}
	public void setList(List<Share> list) {
		this.list = list;
	}
	public SharesNoteCarry(Note note, List<Share> list) {
		this.note = note;
		this.list = list;
	}
	
	
	
}
