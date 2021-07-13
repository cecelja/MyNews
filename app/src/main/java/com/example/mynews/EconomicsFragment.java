package com.example.mynews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EconomicsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Story>>  {

    private StoryArrayAdapter mAdapter;
    private static final String GUARDIAN_URL_2 = "https://content.guardianapis.com/search?q=economics" +
            "&show-fields=thumbnail,trailText,headline" +
            "&show-references=author&order-by=newest&section=technology&api-key=test";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.economics_layout, container, false);
        //Find the listview from the inflated root view layout
        ListView techListView = (ListView) rootView.findViewById(R.id.listview);

        //Initiate a new arrayadapter
        mAdapter = new StoryArrayAdapter(this.getActivity(),new ArrayList<Story>());

        SavedStoryDbHelper db = new SavedStoryDbHelper(getContext());
        Log.i("Logger", "Is it initilazied " + db);

        //Start the download process
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(1, null,this);
        techListView.setAdapter(mAdapter);

        techListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Story story = mAdapter.getItem(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(story.getmURL()));
                startActivity(intent);

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        Log.i("Logger", "Economcis has stopped.");
        super.onStop();
    }

    @NonNull
    @NotNull
    @Override
    public Loader<ArrayList<Story>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        //Initiate a new loader to start performing the network request of the main thread
        StoryLoader loader = new StoryLoader(this.getActivity(), GUARDIAN_URL_2);

        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<ArrayList<Story>> loader, ArrayList<Story> data) {
        mAdapter.clear();
        //We need to check if we have the requested data, and after that we populate the adapter with it
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<ArrayList<Story>> loader) {
        mAdapter.clear();
    }
}
