package com.example.mynews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Story>> {

    private StoryArrayAdapter mAdapter;
    private static final String GUARDIAN_URL = "https://content.guardianapis.com/search?q=technology&" +
            "show-fields=thumbnail,trailText,headline&show-references=author&api-key=test";
    private static final String GUARDIAN_URL_2 = "https://content.guardianapis.com/search?q=technology" +
            "&show-fields=thumbnail,trailText,headline" +
            "&show-references=author&order-by=newest&section=technology&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //A comment
        ListView storyListView = (ListView) findViewById(R.id.listview);

        mAdapter = new StoryArrayAdapter(this, new ArrayList<Story>());

        //Start the process while the application starts
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(1, null,this);
        storyListView.setAdapter(mAdapter);

        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Story story = mAdapter.getItem(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(story.getmURL()));
                startActivity(intent);

            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Loader<ArrayList<Story>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        //Initiate a new loader to start performing the network request of the main thread
        StoryLoader loader = new StoryLoader(this, GUARDIAN_URL_2);

        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull @org.jetbrains.annotations.NotNull Loader<ArrayList<Story>> loader, ArrayList<Story> data) {
        mAdapter.clear();
        //We need to check if we have the requested data, and after that we populate the adapter with it
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull @org.jetbrains.annotations.NotNull Loader<ArrayList<Story>> loader) {
        mAdapter.clear();
    }
}