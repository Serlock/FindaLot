package com.example.googlemapsdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.util.ArrayList;

public class LocationRecViewAdapter extends RecyclerView.Adapter<LocationRecViewAdapter.ViewHolder> {
    private static final String TAG = "LocationRecViewAdapter";
    public static final String LOCATION_ID_KEY = "locationId";

    private ArrayList<Location> locations = new ArrayList<>();
    private Context mContext;

    public LocationRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
        Log.d("locationId", String.valueOf(locations.get(0).getId()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtLocationSubAdminArea.setText(locations.get(position).getSubAdminArea().toString());

        //map
        /*Glide.with(mContext).asBitmap().load(locations.get(position).getImageUrl()).into(holder.imgBook);*/

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra(LOCATION_ID_KEY, locations.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        if (locations.get(position).isExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.txtCountryName.setText(locations.get(position).getCountryName());
            holder.txtCityName.setText(locations.get(position).getAdminArea());
            holder.txtAddressLine.setText(locations.get(position).getAddressLine());
            holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
            holder.imageDown.setVisibility(View.GONE);
        }
        else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelativeLayout.setVisibility(View.GONE);
            holder.imageDown.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView imgBook;
        private TextView txtLocationSubAdminArea;

        private ImageView imageDown, imageUp;

        private RelativeLayout expandedRelativeLayout;
        private TextView txtCountryName, txtAddressLine, txtCityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            //imgBook = itemView.findViewById(R.id.map);
            txtLocationSubAdminArea = itemView.findViewById(R.id.txtLocationSubAdminArea);

            imageDown = itemView.findViewById(R.id.imageDown);
            imageUp = itemView.findViewById(R.id.imageUp);

            expandedRelativeLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);
            txtAddressLine = itemView.findViewById(R.id.txtAddressLine);
            txtCityName = itemView.findViewById(R.id.txtCityName);

            imageDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Location book = locations.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            imageUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Location book = locations.get(getAdapterPosition());
                    book.setExpanded((!book.isExpanded()));
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
