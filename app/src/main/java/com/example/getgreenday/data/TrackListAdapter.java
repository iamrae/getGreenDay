package com.example.getgreenday.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.getgreenday.BR;
import com.example.getgreenday.R;
import com.example.getgreenday.databinding.ItemTracklistBinding;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder>  {

    private String TAG = getClass().getSimpleName();

    Context context;
    boolean isFavorite; // Favorite 리스트인지 TrackData 리스트인지 구분하기 위함
    OnItemClickListener listener;


    private ArrayList<TrackData> trackDataList; // 곡 목록
    private List<TrackData> favorites = new ArrayList<>(); // Favorite 목록


    public void setFavorites(List<TrackData> favorites) {
        // Favorite 목록 어댑터에 연결
        if (favorites == null) {
            return;
        }
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void setTrackListItems(ArrayList<TrackData> trackDataList) {
        // 곡 목록 어댑터에 연결
        if (trackDataList == null) {
            return;
        }
        this.trackDataList = trackDataList;
        notifyDataSetChanged();
    }


    public TrackData getTrackDataAt(int position) {
        // 클릭한 위치의 TrackData 정보 반환
        return (isFavorite)?favorites.get(position):trackDataList.get(position);
    }

    public void updateItem(int position) {
        // 아이템 클릭시 즐겨찾기 상태 변화 적용하기 (토글)
        TrackData dataToUpdate = getTrackDataAt(position);
        int updateStarStatus = (dataToUpdate.getStarred() ==0)?1:0;
        dataToUpdate.setStarred(updateStarStatus);
        notifyItemChanged(position);
    }



    public TrackListAdapter(Context context, OnItemClickListener listener) {
        this.trackDataList = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public TrackListAdapter(Context context, OnItemClickListener listener, boolean isFavorite) {
        // starred : favorite
        this.trackDataList = new ArrayList<>();
        this.context = context;
        this.listener = listener;
        this.isFavorite = isFavorite;
    }

    public interface OnItemClickListener {
        void onItemClick(TrackData trackData, boolean isStarred);
    }

    @NonNull
    @Override
    public TrackListAdapter.TrackListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemTracklistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tracklist, parent, false);

        return new TrackListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackListAdapter.TrackListViewHolder holder, int position) {

        TrackData trackData;
        trackData = (isFavorite) ? favorites.get(position): trackDataList.get(position);

        holder.bind(trackData);
        holder.binding.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(trackData, isFavorite);
                    updateItem(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return (isFavorite)?favorites.size():trackDataList.size();
    }



    public class TrackListViewHolder extends RecyclerView.ViewHolder {

        ItemTracklistBinding binding;
        public TrackListViewHolder(ItemTracklistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TrackData trackData) {
            binding.setVariable(BR.trackList, trackData);
            binding.trackName.setText(trackData.trackName);
            binding.collectionName.setText(trackData.collectionName);
            binding.artistName.setText(trackData.artistName);

            if (isFavorite) {
                // 즐겨찾기 목록에서는 무조건 solid 로 표시
                binding.star.setImageResource(R.drawable.ic_star_black_24);

            } else {
                // 곡목록에서는 data.starred 여부에 따라 solid / stroke 버전 표시
                if (trackData.starred == 0) {
                    binding.star.setImageResource(R.drawable.ic_star_outline_24);
                } else {
                    binding.star.setImageResource(R.drawable.ic_star_black_24);
                }
            }

            Glide
                    .with(binding.getRoot())
                    .load(trackData.artworkUrl)
                    .into(binding.artwork);

        }
    }

    // 리사이클러뷰에 연결된 데이터 비교하기 (for paging)
   static class TrackDataDiff extends DiffUtil.ItemCallback<TrackDataList> {

        private final TrackDataList oldTrackDataList;
        private final TrackDataList newTrackDataList;

        public TrackDataDiff (TrackDataList oldTrackDataList, TrackDataList newTrackDataList) {
            this.newTrackDataList = newTrackDataList;
            this.oldTrackDataList = oldTrackDataList;
        }


        @Override
        public boolean areItemsTheSame(@NonNull TrackDataList oldItem, @NonNull TrackDataList newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrackDataList oldItem, @NonNull TrackDataList newItem) {
            return oldItem.dataList.equals(newItem.dataList);
        }
    }

    public void updateTrackDataItems(TrackDataList trackDataList) {

        //final TrackDataDiff diff = new TrackDataDiff(this.trackDataList, trackDataList);

    }

}