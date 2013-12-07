package cz.fel.cvut.via.entities;

public class SharedNote extends Note {

	private static final long serialVersionUID = 1L;

	private boolean readonly;

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public SharedNote(int id, String name, String note, int version,
			String owner, boolean readonly) {
		super(id, name, note, version, owner);
		this.readonly = readonly;
	}

	
	
	
}
