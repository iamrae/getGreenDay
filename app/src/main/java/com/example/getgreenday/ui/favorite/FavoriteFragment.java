package com.example.getgreenday.ui.favorite;

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
import com.example.getgreenday.databinding.FragmentFavoriteBinding;
import com.example.getgreenday.util.CustomSnackBar;

import java.util.List;

public class FavoriteFragment extends Fragment implements TrackListAdapter.OnItemClickListener {


    String TAG = "FavoriteFragment";
    private FavoriteViewModel favoriteViewModel;
    FragmentFavoriteBinding binding;
    TrackListAdapter favoriteListAdapter;
    RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        binding.setFavoriteViewModel(favoriteViewModel);
        binding.setLifecycleOwner(this);
        initObservers();
        prepRecyclerView();
        return binding.getRoot();
    }

    public void initObservers() {

        // 즐겨찾기 목록 Observer
        final Observer<List<TrackData>> favoriteObserver = new Observer<List<TrackData>>() {
            @Override
            public void onChanged(List<TrackData> trackData) {
                binding.setFavorite(trackData);
            }
        };
        favoriteViewModel.getAllFavorites().observe(getViewLifecycleOwner(), favoriteObserver);

    }

    public void prepRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        favoriteListAdapter = new TrackListAdapter(getContext(), this::onItemClick, true);
        binding.recyclerViewFavoritelist.setAdapter(favoriteListAdapter);
        binding.recyclerViewFavoritelist.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(TrackData trackData, boolean isStarred) {
        // 즐겨찾기 목록에서 삭제하기
        showSnackBar();
        favoriteViewModel.removeFromFavorites(trackData);
    }


    private void showSnackBar() {
        // 삭제 안내 메시지 표시
        CustomSnackBar.inflateSnackBar(getContext(), getView(),false);
    }
}