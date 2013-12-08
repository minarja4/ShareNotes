package cz.fel.cvut.via.sharenotes;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.AddMultipleNotesTask;
import cz.fel.cvut.via.db.notes.DeleteNoteFromDB;
import cz.fel.cvut.via.db.notes.ReadNotes;
import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.utils.Login;
import cz.fel.cvut.via.utils.Utils;

public class Notes extends FragmentActivity implements TabListener {

	
    private ViewPager viewPager;
    private NotesFragmentAdapter mAdapter;
    private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		
		//TABs
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new NotesFragmentAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // pridat taby
        actionBar.addTab(actionBar.newTab().setText("Moje").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Sdilene").setTabListener(this));

        
        //TABS - swipe listener
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
	
	}
	
	
	//kliknuti na button v Actionbaru
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_note_menu:
	        	Log.d(Notes.class.getName(), "Starting addNote activity");
	            Intent i = new Intent(this, AddNote.class);
	            startActivityForResult(i,2);
	            return true;	    
	        case R.id.logout_menu:
	        	Login.setLoggedUser(null);
	        	finish();
	        	return true;
	        case R.id.synchronize_menu:			
				try {
					synchronize();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
	        	return true;
	        case R.id.show_my_shared_menu:
	        	Intent hh = new Intent(this, MySharedNotesActivity.class);
	        	startActivityForResult(hh, 11);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	//odeslat ulozene poznamky na server
	private void synchronize() throws InterruptedException, ExecutionException {
		if(Utils.isConnected(this)) {
			
			List<Note> cachedNotes = ReadNotes.getAllNotesForUser(Login.getLoggedUser().getUsername(), this);
			
			AddMultipleNotesTask task = new AddMultipleNotesTask();
			
			task.execute(cachedNotes);
			task.get();
	
			Toast.makeText(this, "Pocet ulozenych poznamek: " + cachedNotes.size(), Toast.LENGTH_SHORT).show();
			
			for(Note g : cachedNotes) {
				DeleteNoteFromDB.deleteFromDBByLocalId(g, this);
			}
		} else
			Toast.makeText(this, "Chyba pri synchronizaci", Toast.LENGTH_SHORT).show();
		
			
			//		MyNotesFragment f = (MyNotesFragment) mAdapter.getItem(0);
//		f.readNotesAndShow(true);
		
		
	}
	
	
	//zakaz vraceni na LoginActivity
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed	       
	        return false;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	
	@Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

	    
	
}
