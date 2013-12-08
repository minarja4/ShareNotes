package cz.fel.cvut.via.sharenotes;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cz.fel.cvut.via.entities.Share;

public class SharesArrayAdapter extends ArrayAdapter<Share> {

		private List<Share> list = null;
		private Context ctx;

		public SharesArrayAdapter(Context context, int textViewResourceId, List<Share> objects) {
			super(context, textViewResourceId);
			this.list = objects;
			ctx = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Share getItem(int position) {			
			return list.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			LayoutInflater vi;
			vi = LayoutInflater.from(ctx);
			v = vi.inflate(R.layout.listview_item, null);

			Share share = list.get(position);

			
			TextView title = (TextView) v.findViewById(R.id.noteTitle);
			TextView desc = (TextView) v.findViewById(R.id.noteDesc);

			
			title.setText(share.getUsername());
			desc.setText(share.isReadonly()?"Read Only":"Write access granted");

			
			return v;
		}

	}