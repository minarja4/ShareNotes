package cz.fel.cvut.via.sharenotes;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import cz.fel.cvut.via.asyncTasks.GetMyNotesTask;
import cz.fel.cvut.via.db.notes.DeleteNoteFromDB;
import cz.fel.cvut.via.db.notes.ReadNotes;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
 
public class MyNotesFragment extends Fragment {
 
	List<Note> notes = null;
	Note noteToDelete = null;
	boolean mine = true;
	
	
	NotesArrayAdapter<Note> adapter = null;
	
	View rootView = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_notes, container, false);
        
//        System.out.println("CREATE: " + rootView.findViewById(R.id.notesListView));
        this.rootView = rootView;
        readNotesAndShow(mine);
        
        //nastaveni akce tlacitka na refresh
        Button refreshButton = (Button) rootView.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				readNotesAndShow(mine);
			}
		});
        
        
        return rootView;
    }
    

	protected void readNotesAndShow(boolean mine) {
		// ziskame svoje poznamky - pokud jsme online
		//jsme online?
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
		
		List<Note> list = null;
		
		if (isConnected) {
			GetMyNotesTask g = new GetMyNotesTask();
			//g.execute("pepik","a652c7e7312205c3db98dd931d541d1cd6e3e94259e48074ae4242d9a35d473f");
			g.execute(mine?"true":"false");
			
			
			
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
		}
		
				
		//pridam jeste cachovane poznamky z lokalni DB
		List<Note> cachedNotes = ReadNotes.getAllNotesForUser(Login.getLoggedUser().getUsername(), getActivity());
		
		if (list == null) 
			list = new ArrayList<Note>();
		
		list.addAll(cachedNotes);
		
		// v list jsou ted vsechny poznamky usera
		// zobrazit zatim v listview
//		System.out.println("tests: "); 
//		
//		System.out.println("getview: " + getActivity() );
//		System.out.println("id: " + rootView.findViewById(R.id.notesListView));
//		
		final ListView listview = (ListView) rootView.findViewById(R.id.notesListView);
		adapter = new NotesArrayAdapter<Note>(getActivity(), R.layout.listview_item,list);
		listview.setAdapter(adapter);
		
		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Note n = (Note) listview.getItemAtPosition(pos);
				
				if (!n.isCached()){
					Intent i = new Intent(view.getContext(), ShowNoteActivity.class);
					i.putExtra("note", n);
					
					startActivityForResult(i, 22);
			}
			}
		});
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
		  if (!noteToDelete.isCached()) {
			  //mazene
			  task = new DeleteNoteTask();
			  task.execute(noteToDelete);
		  }else {
			  DeleteNoteFromDB.deleteFromDBByLocalId(noteToDelete, getActivity());			  
		  }
	  }
	  
	  
//		task.get();
		notes.remove(noteToDelete);
	
	  
	  
	 adapter.notifyDataSetChanged();
	  
	  return true;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == 22) {
			//navrat z editace poznamky - refresh
		readNotesAndShow(mine);
//		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
    
}