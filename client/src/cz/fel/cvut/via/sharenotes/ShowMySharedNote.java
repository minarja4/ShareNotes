	package cz.fel.cvut.via.sharenotes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.EditMySharesTask;
import cz.fel.cvut.via.asyncTasks.GetSharesTask;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.Share;
import cz.fel.cvut.via.entities.SharedNote;
import cz.fel.cvut.via.entities.SharesNoteCarry;

public class ShowMySharedNote extends Activity {

	List<Share> list = new ArrayList<Share>();
	SharesArrayAdapter adapter = null;
	Note actualNote = null;
	Share shareToDele = null;
	
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
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				
				shareToDele = (Share) listview.getItemAtPosition(pos);
								
				 if (shareToDele != null) {
//					  Toast.makeText(this, "Mazu: " + shareToDele.getUsername(), Toast.LENGTH_SHORT).show();
					adapter.remove(shareToDele);  
					adapter.notifyDataSetChanged();
				  }
				
			}
		});
	}
	
	
	
//	//kontextove menu
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
//	  if (v.getId()==R.id.show_my_shared_shares) {
//	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
////	    Toast.makeText(this, "Vybrana poznamka: " + notes.get(info.position), Toast.LENGTH_SHORT).show();
//	    shareToDele = adapter.getItem(info.position);
//	    menu.add(Menu.NONE, 0, 0, "Smazat");
//	    
//	  }
//	}
//	
//	
	//kliknuti na kontextove menu
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {	  
//	  int menuItemIndex = item.getItemId();
//	  
//	  if (menuItemIndex == 0) {
//		  if (shareToDele != null) {
//			  Toast.makeText(this, "Mazu: " + shareToDele.getUsername(), Toast.LENGTH_SHORT).show();
//			adapter.remove(shareToDele);  
//		  }
//	  }
//	  
//	  adapter.notifyDataSetChanged();
//	  
//	  return true;
//	}
	
	
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
	
	public void save(View view) {
		List<Share> list = adapter.getList();
		
		SharesNoteCarry carry = new SharesNoteCarry(actualNote, list);
		EditMySharesTask task = new EditMySharesTask();
		task.execute(carry);
		
	}
	
}
