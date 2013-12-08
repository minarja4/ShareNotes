package cz.fel.cvut.via.sharenotes;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cz.fel.cvut.via.entities.Note;

public class NotesArrayAdapter<T extends Note> extends ArrayAdapter<T> {

		private List<T> list = null;
		private Context ctx;

		public NotesArrayAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId);
			this.list = objects;
			ctx = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public T getItem(int position) {			
			return list.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			LayoutInflater vi;
			vi = LayoutInflater.from(ctx);
			v = vi.inflate(R.layout.listview_item, null);

			T note = list.get(position);

			if (note != null) {

				TextView title = (TextView) v.findViewById(R.id.noteTitle);
				TextView desc = (TextView) v.findViewById(R.id.noteDesc);

				if (note.isCached()) {
					//kesovane poznamky maji jinou barvu
					title.setTextColor(ColorStateList.valueOf(Color.GREEN));
					desc.setBackgroundColor(Color.BLUE);
				}
				
				title.setText(note.getName());
				desc.setText(note.getNote());

			}

			return v;
		}

	}