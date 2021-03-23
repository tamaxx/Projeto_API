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

    private OnItemSelectedListener listener;

    SliderAdapter(OnItemSelectedListener listener, List<SliderItem> sliderItems, ViewPager2 viewPager) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager;
        this.listener = listener;
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
        Picasso.get().load(item.getPoster()).into(holder.img_poster);
        if(position == sliderItems.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RoundedImageView img_poster;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_poster = itemView.findViewById(R.id.imageSlide);
        }

        @Override
        public void onClick(View v) {
            SliderItem selectedItem = sliderItems.get(getAdapterPosition());

            listener.onItemSelected(selectedItem);
        }
    }

    private Runnable runnable = () -> {
        sliderItems.addAll(sliderItems);
        notifyDataSetChanged();
    };

    public interface OnItemSelectedListener {
        void onItemSelected(SliderItem item);
    }
}
