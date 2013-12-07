 package cz.fel.cvut.via.sharenotes;


import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
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
import cz.fel.cvut.via.asyncTasks.GetSharedNotesTask;
import cz.fel.cvut.via.entities.SharedNote;
 
public class SharedNotesFragment extends Fragment {
 
	List<SharedNote> sharedNotes = null;
	SharedNote noteToDelete = null;
	boolean mine = false;
	
	
	NotesArrayAdapter<SharedNote> adapter = null;
	
	View rootView = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_shared_notes, container, false);
        
        this.rootView = rootView;
        readNotesAndShow(mine);
        
        //nastaveni akce tlacitka na refresh
        Button refreshButton = (Button) rootView.findViewById(R.id.refreshButtonShared);
        refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				readNotesAndShow(mine);
			}
		});
                
        return rootView;
    }
    

	private void readNotesAndShow(boolean mine) {
		// ziskame svoje poznamky
		GetSharedNotesTask g = new GetSharedNotesTask();
		
		g.execute();
		List<SharedNote> list = null;
		
		
		try {
			list = g.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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