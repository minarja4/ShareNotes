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
import cz.fel.cvut.via.asyncTasks.GetMyNotesTask;
import cz.fel.cvut.via.entities.Note;
 
public class MyNotesFragment extends Fragment {
 
	List<Note> notes = null;
	Note noteToDelete = null;
	boolean mine = true;
	
	
	NotesArrayAdapter adapter = null;
	
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
    

	private void readNotesAndShow(boolean mine) {
		// ziskame svoje poznamky
		GetMyNotesTask g = new GetMyNotesTask();
		//g.execute("pepik","a652c7e7312205c3db98dd931d541d1cd6e3e94259e48074ae4242d9a35d473f");
		g.execute(mine?"true":"false");
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
		// zobrazit zatim v listview
		System.out.println("tests: "); 
		
		System.out.println("getview: " + getActivity() );
		System.out.println("id: " + rootView.findViewById(R.id.notesListView));
		
		final ListView listview = (ListView) rootView.findViewById(R.id.notesListView);
		adapter = new NotesArrayAdapter(getActivity(), R.layout.listview_item,list);
		listview.setAdapter(adapter);
		
		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Note n = (Note) listview.getItemAtPosition(pos);
				
				Intent i = new Intent(view.getContext(), ShowNoteActivity.class);
				i.putExtra("note", n);
				
				startActivityForResult(i, 22);
				
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
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 22) {
			//navrat z editace poznamky - refresh
			readNotesAndShow(mine);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
    
}