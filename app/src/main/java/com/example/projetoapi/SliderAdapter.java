package com.example.projetoapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;
    private TextView txt_itemTitle;
    private TextView txt_itemGenre;

    SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem item = sliderItems.get(position);
        Picasso.get().load(item.getPoster()).fit().centerInside().into(holder.img_poster);
        txt_itemTitle.setText(item.getTitle());
        txt_itemGenre.setText(item.getGender());
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView img_poster;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.imageSlide);
            txt_itemTitle = itemView.findViewById(R.id.txt_itemTitle);
            txt_itemGenre = itemView.findViewById(R.id.txt_itemGenre);
        }
    }

}
