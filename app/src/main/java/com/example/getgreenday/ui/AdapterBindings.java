package com.example.getgreenday.ui;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getgreenday.data.TrackData;
import com.example.getgreenday.data.TrackListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterBindings {

    @BindingAdapter("trackListAdapter")
    public static void trackListAdapter(RecyclerView recyclerView, ArrayList<TrackData> trackDataList) {
        TrackListAdapter adapter = (TrackListAdapter)recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setTrackListItems(trackDataList);
        }
    }

    @BindingAdapter("favoriteListAdapter")
    public static void favoriteListAdapter(RecyclerView recyclerView, List<TrackData> favoriteDataList) {
        TrackListAdapter adapter = (TrackListAdapter)recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setFavorites(favoriteDataList);
        }
    }

}
