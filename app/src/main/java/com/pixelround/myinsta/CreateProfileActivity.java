package com.pixelround.myinsta;

import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;

import java.io.Console;
import java.lang.reflect.Array;
import java.net.URI;

public class CreateProfileActivity extends AppCompatActivity {
    private static final int IMAGE_INTENT_REQ = 1;
    private static final String TAG = "Create Profile";

    private CircularImageView profilePic;
    private EditText fullNameText;
    private Spinner educationSpinner;
    private EditText taglineText;
    private Button createBtn;
    private TextView skipText;

    public Uri profileUri;
    public String fullName;
    public String education;
    public String tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        profilePic = findViewById(R.id.set_profile_picture);
        fullNameText = findViewById(R.id.full_name_editText);
        taglineText = findViewById(R.id.tagline_editText);
        createBtn = findViewById(R.id.create_profile_button);
        skipText = findViewById(R.id.skip_label);
        educationSpinner = findViewById(R.id.education_spinner);

        profilePic.setImageResource(R.drawable.defaultpic);
        profilePic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"),
                        IMAGE_INTENT_REQ);
            }
        });

        //set education dropdown options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.education_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationSpinner.setAdapter(adapter);

        //create profile
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = fullNameText.getText().toString();
                education = educationSpinner.getSelectedItem().toString();
                tagline = taglineText.getText().toString();

                if (fullName.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You must enter your name", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                startActivity(new Intent(CreateProfileActivity.this, SearchActivity.class));
            }
        });

        //skip profile creation
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateProfileActivity.this, SearchActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_INTENT_REQ) {
            if (resultCode == RESULT_OK) {
                profileUri = data.getData();
                profilePic.setImageURI(profileUri);
                Toast toast = Toast.makeText(getApplicationContext(), "Profile Picture Updated", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}