 package cz.fel.cvut.via.sharenotes;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import cz.fel.cvut.via.asyncTasks.DeleteNoteTask;
import cz.fel.cvut.via.asyncTasks.GetSharedNotesTask;
import cz.fel.cvut.via.entities.SharedNote;
import cz.fel.cvut.via.utils.Login;
 
public class SharedNotesFragment extends Fragment {
 
	List<SharedNote> sharedNotes = null;
	SharedNote noteToDelete = null;
	boolean mine = false;
	
	
	NotesArrayAdapter<SharedNote> adapter = null;
	
	View rootView = null;
	
	private int interval = 60000; // refresh kazdych 60s
	private Handler handler;
	
	Runnable notesChecker = new Runnable() {
		@Override
		public void run() {
//			Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
			readNotesAndShow(mine);
			handler.postDelayed(notesChecker, interval);
		}
	};
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_shared_notes, container, false);
        
        this.rootView = rootView;
        readNotesAndShow(mine);
        
        handler = new Handler();
        
        //nastaveni akce tlacitka na refresh
//        Button refreshButton = (Button) rootView.findViewById(R.id.refreshButtonShared);
//        refreshButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				readNotesAndShow(mine);
//			}
//		});
        
        notesChecker.run();
                
        return rootView;
    }
    

	private void readNotesAndShow(boolean mine) {
		// ziskame sdilene poznamky - pokud jsme online
		
		
		if(Login.getLoggedUser()==null){
			return;
		}
		//jsme online?
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
	
		List<SharedNote> list = new ArrayList<SharedNote>();
		
		if (isConnected) {
			GetSharedNotesTask g = new GetSharedNotesTask();
			
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
	
		} else {
			list.add(new SharedNote(1, "OFFLINE", "Jste v rezimu Offline", 1, "", true));
		}
		sharedNotes = list;
		
		// v list jsou ted vsechny poznamky usera
		// zobrazit zatim v listview

		final ListView listview = (ListView) rootView.findViewById(R.id.notesListViewShared);
		adapter = new NotesArrayAdapter<SharedNote>(getActivity(), R.layout.listview_item,list);
		listview.setAdapter(adapter);
		
		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				
				SharedNote n = (SharedNote) listview.getItemAtPosition(pos);
				
				
				Intent i = new Intent(view.getContext(), ShowSharedNoteActivity.class);
				i.putExtra("note", n);												
				
				startActivityForResult(i, 44);
				
			}
		});
	}


	


	
	
	
	//kontextove menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.notesListViewShared) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//	    Toast.makeText(this, "Vybrana poznamka: " + notes.get(info.position), Toast.LENGTH_SHORT).show();
	    noteToDelete = sharedNotes.get(info.position);
	    menu.add(Menu.NONE, 0, 0, "Smazat ze seznamu");
	    
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
		sharedNotes.remove(noteToDelete);
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
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == 22) {
			//navrat z editace poznamky - refresh
			readNotesAndShow(mine);
//		}/
		super.onActivityResult(requestCode, resultCode, data);
	}

	
    
}