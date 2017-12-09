package com.example.csci567.easyrentals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import utility.AppPreferences;

public class ImageUpload extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 10000;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_IMAGE_2 = 2;
    private static final int PICK_IMAGE_3 = 3;
    private String [] selectedPath = new String[3];
    private AppPreferences appPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

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
        appPreferences = new AppPreferences(getApplicationContext());
    }

    public void onNextPressed(View view) {
        int counter = 0;
        for (final String selectedImageIterator:selectedPath) {
            if (!ifNull(selectedImageIterator)) {
                uploadImage(selectedImageIterator, counter);
                counter++;
            }
        }

        //if (!ifNull(selectedPath)) {
        Intent intent = new Intent(this, FinalMessageActivity.class);
        startActivity(intent);
        // }
        //else
        //  Toast.makeText(getApplicationContext(), "Please upload all the images.", Toast.LENGTH_SHORT).show();
    }

    private void uploadImage(final String imageName, final int counter){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File(imageName);
                Bitmap compressedImageFile = null;
                try {
                    compressedImageFile = new Compressor(ImageUpload.this).compressToBitmap(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String content_type = getMimeType(imageName);
                Log.i("ImageName", imageName);
                Log.i("Content Type", content_type);

                //Bitmap bmp = BitmapFactory.decodeFile(selectedPath);
                int height = compressedImageFile.getHeight();
                int width= compressedImageFile.getWidth();
                Bitmap resized = compressedImageFile;
                if (width > 4096) {
                    resized  = Bitmap.createScaledBitmap(compressedImageFile, 4050, height, true);
                }
                if (height > 4096){
                    int newWidth = resized.getWidth();
                    resized  = Bitmap.createScaledBitmap(compressedImageFile, newWidth, 4050, true);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                if (resized != null) {
                    resized.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }


                OkHttpClient client = new OkHttpClient();
                RequestBody fileBody = RequestBody.create(MediaType.parse(content_type), bos.toByteArray());

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fileName", appPreferences.getDrivingLicense() + "Exterior" + counter)
                        .addFormDataPart("file", imageName.substring(imageName.lastIndexOf('/') + 1), fileBody)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://45.79.76.22/EasyRentals/EasyRentals/image/upload")
                        .post(requestBody)
                        .build();


                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        throw new IOException("Error:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    public void uploadOptions(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(intent,"Select picture to upload");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePicture});
        switch (view.getId()){
            case R.id.imageUploadButton1:
                startActivityForResult(chooserIntent,PICK_IMAGE);
                break;

            case R.id.imageUploadButton2:
                startActivityForResult(chooserIntent,PICK_IMAGE_2);
                break;

            case R.id.imageUploadButton3:
                startActivityForResult(chooserIntent,PICK_IMAGE_3);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView1, imageView2, imageView3, imageView4;
        imageView1 = (ImageView) findViewById(R.id.image_show1);
        imageView2 = (ImageView) findViewById(R.id.image_show2);
        imageView3 = (ImageView) findViewById(R.id.image_show3);

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

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    private Boolean ifNull(String... args){
        Boolean result = false;
        for (String arg : args) {
            if (arg.equals("")) {
                result = true;
            }
        }
        return result;
    }
}


