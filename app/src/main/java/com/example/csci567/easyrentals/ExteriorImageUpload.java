package com.example.csci567.easyrentals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;

public class ExteriorImageUpload extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10000;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_IMAGE_2 = 2;
    private static final int PICK_IMAGE_3 = 3;
    private static final int PICK_IMAGE_4 = 4;
    private String [] selectedPath = new String[4];

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exterior_image_upload);
        Bundle bundle = getIntent().getExtras();
        //Toast.makeText(this,bundle.getString("transmission") + " " + bundle.getString("year") + " " + bundle.getString("style"),Toast.LENGTH_LONG).show();

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

        }
    }

    public void onNextPressed(View view) {
        Intent intent = new Intent(this, InteriorImageUpload.class);
        startActivity(intent);
    }

    public void uploadOptions(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(intent,"Select picture to upload");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});
        switch (view.getId()){
            case R.id.upload_button1:
                startActivityForResult(chooserIntent,PICK_IMAGE);
                break;

            case R.id.upload_button2:
                startActivityForResult(chooserIntent,PICK_IMAGE_2);
                break;

            case R.id.upload_button3:
                startActivityForResult(chooserIntent,PICK_IMAGE_3);
                break;

            case R.id.upload_button4:
                startActivityForResult(chooserIntent,PICK_IMAGE_4);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView1, imageView2, imageView3, imageView4;
        imageView1 = (ImageView) findViewById(R.id.image_display1);
        imageView2 = (ImageView) findViewById(R.id.image_display2);
        imageView3 = (ImageView) findViewById(R.id.image_display3);
        imageView4 = (ImageView) findViewById(R.id.image_display4);
        if (resultCode == RESULT_OK) {
            Uri selectedImage;

            switch (requestCode){
                case PICK_IMAGE:
                    selectedImage = data.getData();
                    selectedPath[0] = getRealPath(selectedImage);
                    Toast.makeText(this, "Selected Path: " + selectedPath[0], Toast.LENGTH_LONG).show();
                    Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView1);
                    break;

                case PICK_IMAGE_2:
                    selectedImage = data.getData();
                    selectedPath[1] = getRealPath(selectedImage);
                    Toast.makeText(this, "Selected Path: " + selectedPath[1], Toast.LENGTH_LONG).show();
                    Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView2);
                    break;

                case PICK_IMAGE_3:
                    selectedImage = data.getData();
                    selectedPath[2] = getRealPath(selectedImage);
                    Toast.makeText(this, "Selected Path: " + selectedPath[2], Toast.LENGTH_LONG).show();
                    Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView3);
                    break;

                case PICK_IMAGE_4:
                    selectedImage = data.getData();
                    selectedPath[3] = getRealPath(selectedImage);
                    Toast.makeText(this, "Selected Path: " + selectedPath[3], Toast.LENGTH_LONG).show();
                    Picasso.with(getApplicationContext()).load(selectedImage).fit().centerCrop().into(imageView4);
                    break;
            }
        }
    }

    private String getRealPath(Uri selectedImage) {
        return ImageFilePath.getPath(this, selectedImage);
    }
}
