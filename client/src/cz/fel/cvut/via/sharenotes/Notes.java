package cz.fel.cvut.via.sharenotes;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.GetMyNotesTask;
import cz.fel.cvut.via.entities.Note;

public class Notes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		// ziskame svoje poznamky
		GetMyNotesTask g = new GetMyNotesTask();
		//g.execute("pepik","a652c7e7312205c3db98dd931d541d1cd6e3e94259e48074ae4242d9a35d473f");
		
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

		
		
		
		// v list jsou ted vsechny poznamky usera
		// zobrazit zatim v gridview
		ListView listview = (ListView) findViewById(R.id.notesListView);
		listview.setAdapter(new NotesArrayAdapter(this, R.layout.listview_item,list));

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

}
