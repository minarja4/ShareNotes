package cz.fel.cvut.via.sharenotes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class NotesFragmentAdapter extends FragmentPagerAdapter {
 
    public NotesFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:            
            return new MyNotesFragment();
        case 1:
            return new SharedNotesFragment();

        }
 
        return null;
    }
 
    @Override
    public int getCount() { 
        return 2;
    }
 
}
