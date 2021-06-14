package com.example.projetoapi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class PictureFragment extends Fragment {

    private ArrayList<Picture> pictureList;

    private OnFragmentInteractionListener mListener;

    public PictureFragment() {

    }

    public static PictureFragment newInstance() {
        PictureFragment fragment = new PictureFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        pictureList = new ArrayList<Picture>();

        pictureList.add(new Picture(R.drawable.clockworkorange));
        pictureList.add(new Picture(R.drawable.pinocchio));
        pictureList.add(new Picture(R.drawable.bugs_bunny));
        pictureList.add(new Picture(R.drawable.deadpool));
        pictureList.add(new Picture(R.drawable.joker));
        pictureList.add(new Picture(R.drawable.poo));
        pictureList.add(new Picture(R.drawable.wolverine));

        PictureAdapter pictureAdapter = new PictureAdapter(getActivity(),
                R.layout.picturelist_item, pictureList);

        ListView listView = view.findViewById(R.id.picture_list);
        listView.setAdapter(pictureAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int newPicture = pictureList.get(position).getPictureId();
                returnPicture(newPicture);
            }
        });

        return view;
    }

    public void returnPicture(int newPicture){
        if(mListener != null){
            mListener.onFragmentInteraction(newPicture);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implementing OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(int newPicture);
    }
}



