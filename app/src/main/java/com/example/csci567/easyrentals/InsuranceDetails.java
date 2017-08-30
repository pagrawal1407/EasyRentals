package com.example.csci567.easyrentals;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class InsuranceDetails extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_IMAGE_2 = 2;
    private static final int PICK_IMAGE_3 = 3;
    private String [] selectedPath = new String[3];
    private int seekBarValue;
    private TextView seekBarValueDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insuarance_details);

        SeekBar seekBar = (SeekBar) findViewById(R.id.priceSeekBar);
        seekBar.setMax(20000);
        seekBar.setOnSeekBarChangeListener(this);

        seekBarValueDisplay = (TextView) findViewById(R.id.seekBarValue);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView1, imageView2, imageView3;
        imageView1 = (ImageView) findViewById(R.id.image_display1);
        imageView2 = (ImageView) findViewById(R.id.image_display2);
        imageView3 = (ImageView) findViewById(R.id.image_display3);
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
            }
        }
    }

    private String getRealPath(Uri selectedImage) {
        return ImageFilePath.getPath(this, selectedImage);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        seekBarValue = i;
        seekBarValueDisplay.setText(String.format("%s%s", getString(R.string.selectedPrice), String.valueOf(seekBarValue)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onNextPressed(View view) {
        Intent intent = new Intent(this, SignUpChoiceScreen.class);
        startActivity(intent);
    }
}
