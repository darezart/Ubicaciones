package com.example.locations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locations.databinding.ItemLocationsBinding;
import com.example.locations.objects.Locations;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Locations>locations = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public MainAdapter(View.OnClickListener onClickListener, Context context){
        this.context = context;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
         ItemLocationsBinding binding = ItemLocationsBinding.inflate(inflater, parent, false);
        return new MainAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof MainAdapter.MainViewHolder){

            MainAdapter.MainViewHolder holder = (MainAdapter.MainViewHolder) viewHolder;
            Locations location = locations.get(position);

            holder.itemView.setTag(location);
            holder.binding.itemDate.setText(location.getDate());
            holder.binding.itemTime.setText(location.getTime());
            holder.binding.itemLongitude.setText(location.getLongitude());
            holder.binding.itemLatitude.setText(location.getLatitude());


        }

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setUpdate(ArrayList<Locations> locations){
        this.locations.clear();
        this.locations.addAll(locations);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        ItemLocationsBinding binding;

        public MainViewHolder(@NonNull ItemLocationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(onClickListener);

        }
    }

}
