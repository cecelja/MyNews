package com.example.mynews;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.mynews.SavedStoryContract.SavedStory.TABLE_NAME;

public class SavedFragment extends Fragment {

    public SQLiteDatabase db;
    SQLiteDatabase readDb;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if(container != null){
            container.removeAllViews();
        }
        View rootView = inflater.inflate(R.layout.saved_activity, container, false);

        //TextView txt = (TextView) rootView.findViewById(R.id.savedtext);
        ListView list = (ListView) rootView.findViewById(R.id.listview_saved);

        DataArrayAdapter adapterData = new DataArrayAdapter(getActivity(), new ArrayList<DataModel>());

        SavedStoryDbHelper helper = new SavedStoryDbHelper(getContext());
        db = helper.getWritableDatabase();


        Button btnDelete = (Button) rootView.findViewById(R.id.button_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(TABLE_NAME, null, null);
            }
        });


        Button btnAdd = (Button) rootView.findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(SavedStoryContract.SavedStory.COLUMN_NAME_TITLE, "Filip");
                values.put(SavedStoryContract.SavedStory.COLUMN_NAME_SUBTITLE, "Cecelja");

                long newRowID = db.insert(TABLE_NAME, null, values);
                Log.i("Logger", "Print the database " + newRowID);
                values.clear();
            }
        });


        readDb = helper.getReadableDatabase();

        Cursor data = readDb.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        Log.i("Logger", "Is there a problem? " + getProfilesCount());
        int returned = data.getColumnCount();
        ArrayList<String> podatci = new ArrayList<String>();
        for(int i = 0; i <= getProfilesCount(); i++){
            if(data.moveToNext()){
                int index = data.getColumnIndex("url");
            podatci.add(data.getString(index));
            }

        }
        Cursor data2 = readDb.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        Log.i("Logger", "Is there a problem? 1" + podatci);
        ArrayList<String> podatci2 = new ArrayList<String>();
        for(int k = 0; k <= getProfilesCount(); k++){
            if(data2.moveToNext()){
                int index2 = data2.getColumnIndex("title");
                podatci2.add(data2.getString(index2));
            }
        }
        Log.i("Logger", "Is there a problem? 2" + podatci2);
        ArrayList<DataModel> listData = new ArrayList<DataModel>();
        for(int j = 0; j < getProfilesCount(); j++){
            listData.add(new DataModel(podatci2.get(j), podatci.get(j)));
        }

        Log.i("Logger", "Is there a problem? 3" +" " + podatci);

        adapterData.addAll(listData);
        list.setAdapter(adapterData);




        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public long getProfilesCount() {
        SQLiteDatabase dreb = readDb;
        long count = DatabaseUtils.queryNumEntries(dreb, TABLE_NAME);
        return count;
    }

}
