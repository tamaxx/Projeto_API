package com.example.projetoapi;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PictureAdapter extends ArrayAdapter<Picture> {

    private ArrayList<Picture> pictureList;

    public PictureAdapter(@NonNull Context context, int resource, ArrayList<Picture> pictureList) {
        super(context, resource, pictureList);
        this.pictureList = pictureList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.picturelist_item,
                    parent, false);
        }

        CircleImageView pictureImage = convertView.findViewById(R.id.img_picture);

        pictureImage.setImageResource(pictureList.get(position).getPictureId());

        return convertView;
    }
}
