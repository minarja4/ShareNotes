package cz.fel.cvut.via.sharenotes;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cz.fel.cvut.via.asyncTasks.ShareNoteTask;
import cz.fel.cvut.via.entities.Carry;
import cz.fel.cvut.via.entities.Note;

public class ShareNoteActivity extends Activity {

	private Note sharedNote = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_note);
				
		sharedNote = (Note) getIntent().getSerializableExtra("note");
		
		TextView t = (TextView) findViewById(R.id.shareNoteName);
		t.setTag(sharedNote.getName());
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_note, menu);
		return true;
	}
	
	public void share(View view) throws InterruptedException, ExecutionException {
		ShareNoteTask task = new ShareNoteTask();
		
		EditText user = (EditText) findViewById(R.id.shareToUsername);
		String username = user.getText().toString();
		CheckBox readOnly = (CheckBox) findViewById(R.id.shareReadOnly);		
		
		task.execute(new Carry(username, sharedNote, readOnly.isChecked()));
		task.get();
		
		Toast.makeText(this, "Nasdileno", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, Notes.class);
		startActivity(i);
		
	}

}
