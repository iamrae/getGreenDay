package com.example.getgreenday.ui.tracklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getgreenday.R;
import com.example.getgreenday.data.TrackData;
import com.example.getgreenday.data.TrackListAdapter;
import com.example.getgreenday.databinding.FragmentTracklistBinding;
import com.example.getgreenday.util.CustomSnackBar;

import java.util.ArrayList;

public class TrackListFragment extends Fragment implements TrackListAdapter.OnItemClickListener {

    String TAG = "TrackListFragment";
    private TrackListViewModel trackListViewModel;
    FragmentTracklistBinding binding;
    TrackListAdapter trackListAdapter;
    RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trackListViewModel =
                new ViewModelProvider(getActivity()).get(TrackListViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracklist, container, false);
        binding.setTrackListViewModel(trackListViewModel);
        binding.setLifecycleOwner(getActivity());
        prepRecyclerView();
        initObservers();
        return binding.getRoot();
    }


    public void initObservers() {

        // 곡 목록 Observer
        final Observer<ArrayList<TrackData>> trackDataObserver = new Observer<ArrayList<TrackData>>() {
            @Override
            public void onChanged(ArrayList<TrackData> trackData) {
                binding.setTrackData(trackData); // 프래그먼트 새로 시작되고 Observe 시작할 때마다 어댑터에 새로 바인딩이 되어버린
            }
        };
        trackListViewModel.trackData.observe(getViewLifecycleOwner(), trackDataObserver);
    }

    public void prepRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        trackListAdapter = new TrackListAdapter(getContext(), this::onItemClick);
        binding.recyclerViewTracklist.setAdapter(trackListAdapter);
        binding.recyclerViewTracklist.setLayoutManager(layoutManager);

        // 어댑터 상태 저장 옵션
        trackListAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

    }


    @Override
    public void onItemClick(TrackData trackData, boolean isStarred) {
        // 별 누르면 1. status 바꾸고 2. favorite 목록에 추가 또는 삭제

        int starred = trackData.starred;
        if (starred == 0) {
            trackListViewModel.addToFavorites(trackData);
        } else {
            trackListViewModel.removeFromFavorites(trackData);
        }

        showOrHideSnackBar(starred);
    }


    private void showOrHideSnackBar(int starred) {
        // 즐겨찾기 삭제/추가 여부에 따라 다른 안내 메시지 표시
        boolean addToFavorite = (starred == 0) ? true : false;
        CustomSnackBar.inflateSnackBar(getContext(), getView(), addToFavorite);
    }
}