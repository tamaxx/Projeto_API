package com.example.projetoapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements PictureFragment.OnFragmentInteractionListener{
    public static final String EXTRA_NUMBER = "com.example.projetoapi.EXTRA_NUMBER";
    public static final String EXTRA_TEXT = "com.example.projetoapi.EXTRA_TEXT";

    private FrameLayout frameContainer;
    private CircleImageView img_profile;
    private Button btn_confirm;

    private TextView txt_username;
    private EditText txt_editName;
    private ImageView img_editName;
    private ViewSwitcher switcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intentFromMain = getIntent();
        String usernameFromMain = intentFromMain.getStringExtra(MainActivity.EXTRA_TEXT);
        int pictureFromMain = intentFromMain.getIntExtra(EXTRA_NUMBER,0);

        img_profile = findViewById(R.id.img_profilepic);
        img_profile.setImageResource(pictureFromMain);
        img_profile.setTag(pictureFromMain);

        txt_username = findViewById(R.id.txt_user2);
        txt_username.setText(usernameFromMain);

        frameContainer = findViewById(R.id.fragment_container);
        btn_confirm = findViewById(R.id.btn_confirm);

        img_editName = findViewById(R.id.img_editName);

        img_editName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switcher = findViewById(R.id.viewSwitcher);
                txt_editName = switcher.findViewById(R.id.txt_editName);
                String currentName = txt_username.getText().toString();
                String newName = txt_editName.getText().toString();
                switcher.showNext();
                if(switcher.getCurrentView() == txt_editName){
                    txt_editName.setText(currentName);
                    img_editName.setImageResource(R.drawable.ic_done);
                } else {
                    txt_username.setText(newName);
                    img_editName.setImageResource(R.drawable.ic_edit);
                }
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pictureToMain = (int) img_profile.getTag();
                String nameToMain = txt_username.getText().toString();
                Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
                intentToMain.putExtra(EXTRA_TEXT, nameToMain);
                intentToMain.putExtra(EXTRA_NUMBER, pictureToMain);
                startActivity(intentToMain);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPicFragment();
            }
        });
    }

    public void openPicFragment(){
        PictureFragment fragment = PictureFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, fragment, "PICTURE_FRAGMENT").commit();
    }

    @Override
    public void onFragmentInteraction(int newPicture) {
        img_profile.setImageResource(newPicture);
        img_profile.setTag(newPicture);
        onBackPressed();
    }
}