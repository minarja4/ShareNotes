package cz.fel.cvut.via.sharenotes;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.DeleteMySharedNote;
import cz.fel.cvut.via.asyncTasks.GetMySharedNotesTask;
import cz.fel.cvut.via.entities.Note;

public class MySharedNotesActivity extends Activity {

	List<Note> list = null;
	NotesArrayAdapter<Note> adapter = null;
	private Note noteToDelete = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_shared);
	
		//readNotesAndShow();
		
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		readNotesAndShow();
		
	}
	
	protected void readNotesAndShow() {
		//jsme online?
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
		
				
		if (isConnected) {
			GetMySharedNotesTask g = new GetMySharedNotesTask();
			//g.execute("pepik","a652c7e7312205c3db98dd931d541d1cd6e3e94259e48074ae4242d9a35d473f");
			g.execute();
							
			try {
				list = g.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		


		final ListView listview = (ListView) findViewById(R.id.sharedNotesListView);
		adapter = new NotesArrayAdapter<Note>(this, R.layout.listview_item,list);
		listview.setAdapter(adapter);
		
		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Note n = (Note) listview.getItemAtPosition(pos);
				
				Intent i = new Intent(view.getContext(), ShowMySharedNote.class);
				i.putExtra("note", n);
				
				startActivityForResult(i, 22);
		
			}
		});
	}
	
	
//	kontextove menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
//	  if (v.getId()==R.id.) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//	    Toast.makeText(this, "Vybrana poznamka: " + notes.get(info.position), Toast.LENGTH_SHORT).show();
	    noteToDelete = adapter.getItem(info.position);
	    menu.add(Menu.NONE, 0, 0, "Smazat");
	    
//	  }
	}
	
	
	//kliknuti na kontextove menu
	@Override
	public boolean onContextItemSelected(MenuItem item) {	  
	  int menuItemIndex = item.getItemId();
	  
	  if (menuItemIndex == 0) {
		  if (noteToDelete != null) {
//			  Toast.makeText(this, "Mazu: " + shareToDele.getUsername(), Toast.LENGTH_SHORT).show();
			adapter.remove(noteToDelete);
			adapter.notifyDataSetChanged();
			
			DeleteMySharedNote task = new DeleteMySharedNote();
			task.execute(noteToDelete);
			
			
		  }
	  }
	  
	  adapter.notifyDataSetChanged();
	  
	  return true;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_shared, menu);
		return true;
	}


	@Override
	public void onBackPressed() {
		finish();
	}

	
	
}
