package cz.fel.cvut.via.sharenotes;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cz.fel.cvut.via.asyncTasks.DeleteNoteTask;
import cz.fel.cvut.via.asyncTasks.GetMyNotesTask;
import cz.fel.cvut.via.entities.Note;

public class Notes extends Activity {

	List<Note> notes = null;
	Note noteToDelete = null;
	
	NotesArrayAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		readNotesAndShow();
		
		
	}

	private void readNotesAndShow() {
		// ziskame svoje poznamky
		GetMyNotesTask g = new GetMyNotesTask();
		//g.execute("pepik","a652c7e7312205c3db98dd931d541d1cd6e3e94259e48074ae4242d9a35d473f");
		g.execute();
		List<Note> list = null;
		
		
		try {
			list = g.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		notes = list;
		
		// v list jsou ted vsechny poznamky usera
		// zobrazit zatim v gridview
		final ListView listview = (ListView) findViewById(R.id.notesListView);
		adapter = new NotesArrayAdapter(this, R.layout.listview_item,list);
		listview.setAdapter(adapter);
		
		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Note n = (Note) listview.getItemAtPosition(pos);
				
				Intent i = new Intent(view.getContext(), ShowNoteActivity.class);
				i.putExtra("note", n);
				
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	private class NotesArrayAdapter extends ArrayAdapter<Note> {

		private List<Note> list = null;
		private Context ctx;

		public NotesArrayAdapter(Context context, int textViewResourceId,
				List<Note> objects) {
			super(context, textViewResourceId);
			this.list = objects;
			ctx = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Note getItem(int position) {
			return list.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.listview_item, null);

			Note note = list.get(position);

			if (note != null) {

				TextView title = (TextView) v.findViewById(R.id.noteTitle);
				TextView desc = (TextView) v.findViewById(R.id.noteDesc);

				title.setText(note.getName());
				desc.setText(note.getNote());

			}

			return v;
		}

	}


	//kliknuti na button v Actionbaru
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_note_menu:
	        	Log.d(Notes.class.getName(), "Starting addNote activity");
	            Intent i = new Intent(this, AddNote.class);
	            startActivity(i);
	            return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	//zakaz vraceni na LoginAtivity
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed	       
	        return false;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	//kontextove menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.notesListView) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//	    Toast.makeText(this, "Vybrana poznamka: " + notes.get(info.position), Toast.LENGTH_SHORT).show();
	    noteToDelete = notes.get(info.position);
	    menu.add(Menu.NONE, 0, 0, "Smazat");
	    
	  }
	}
	
	
	//kliknuti na kontextove menu
	@Override
	public boolean onContextItemSelected(MenuItem item) {	  
	  int menuItemIndex = item.getItemId();
	  
	  DeleteNoteTask task = null;
	  
	  if (menuItemIndex == 0) {
		  //mazene
		  task = new DeleteNoteTask();
		  task.execute(noteToDelete);
	  }
	  
	  try {
		task.get();
		notes.remove(noteToDelete);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	 adapter.notifyDataSetChanged();
	  
	  return true;
	}
	
	public void refresh(View view) {
		readNotesAndShow();
	}
	
}
