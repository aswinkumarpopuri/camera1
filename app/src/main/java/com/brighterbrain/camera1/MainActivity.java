package com.brighterbrain.camera1;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btnTakePhoto;
    ImageView imgTakenPhoto;
    Uri imageUri;
    private static final int CAM_REQUEST=13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTakePhoto = (Button)findViewById(R.id.button1);
        imgTakenPhoto = (ImageView)findViewById(R.id.ImageView1);
        btnTakePhoto .setOnClickListener(new btnTakePhotoClicker());
    }




     class btnTakePhotoClicker implements  Button.OnClickListener
        {
        @Override
        public void onClick(View view) {

            Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            boolean storageDirAvailable = false;
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "image_capture");
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    storageDirAvailable = false;
                } else {
                    storageDirAvailable = true;
                }
            } else {
                storageDirAvailable = true;
            }

            if (storageDirAvailable) {
                File imageFile = new File(storageDir + File.separator + "output.jpg");
                imageUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode==RESULT_OK){
        if(requestCode==CAM_REQUEST){
           //Bitmap thumbnail= (Bitmap) data.getExtras().get("data");
            imgTakenPhoto.setImageBitmap(BitmapFactory.decodeFile(imageUri.getPath()));
        }
       }

    }
}

