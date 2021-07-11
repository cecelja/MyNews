package com.example.mynews;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class SavedFragment extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if(container != null){
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.saved_activity, container, false);

        TextView txt = (TextView) rootView.findViewById(R.id.savedtext);

        SavedStoryDbHelper helper = new SavedStoryDbHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SavedStoryContract.SavedStory.COLUMN_NAME_TITLE, "First entry");
        values.put(SavedStoryContract.SavedStory.COLUMN_NAME_SUBTITLE, "https://www.google.com/search?q=google");

        long newRowID;
        newRowID = db.insert(SavedStoryContract.SavedStory.TABLE_NAME, null, values);
        Log.i("Logger", "Print the database" + db.getPath().toString());


        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
