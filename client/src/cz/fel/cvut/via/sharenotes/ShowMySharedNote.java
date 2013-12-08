	package cz.fel.cvut.via.sharenotes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.GetSharesTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.Share;

public class ShowMySharedNote extends Activity {

	List<Share> list = new ArrayList<Share>();
	SharesArrayAdapter adapter = null;
	Note actualNote = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_my_shared_note);
		
		actualNote = (Note) getIntent().getSerializableExtra("note");
		
		TextView name = (TextView) findViewById(R.id.show_my_shared_name);
		TextView note = (TextView) findViewById(R.id.show_my_shared_note);
		
		name.setText(actualNote.getName());
		note.setText(actualNote.getNote());
		
		readNotesAndShow();
	}

	
	
	
	private void readNotesAndShow() {
		//jsme online?
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
	
		if (isConnected) {
			GetSharesTask task = new GetSharesTask();
			
			task.execute(actualNote);
			
			try {
				list = task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} else {
			Toast.makeText(this, "Jste offline", Toast.LENGTH_SHORT).show();
		}
		
		
		// v list jsou ted vsechny poznamky usera
		// zobrazit zatim v listview

		final ListView listview = (ListView) findViewById(R.id.show_my_shared_shares);
		adapter = new SharesArrayAdapter(this, R.layout.listview_item, list);
		listview.setAdapter(adapter);
		
//		registerForContextMenu(listview);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int pos,
//					long id) {
//				
//				SharedNote n = (SharedNote) listview.getItemAtPosition(pos);
//				
//				
//				Intent i = new Intent(view.getContext(), ShowSharedNoteActivity.class);
//				i.putExtra("note", n);												
//				
//				startActivityForResult(i, 44);
//				
//			}
//		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_my_shared_note, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
}
