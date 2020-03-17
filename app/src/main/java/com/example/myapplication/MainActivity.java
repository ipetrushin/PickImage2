package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final int PICK_IMAGE = 123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){

            Uri uri = data.getData();

            Log.d("mytag", "request:"+uri);

            // объект с растром
            // источник кода https://developer.android.com/guide/topics/providers/document-provider?hl=ru
            Bitmap image = null;
            try {
                // загрузка данных по Uri
                ParcelFileDescriptor parcelFileDescriptor =
                        getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
            } catch (IOException e) { Log.d("mytag", e.getLocalizedMessage());  }

            ImageView iv = findViewById(R.id.image);
            // 1) проверить, что изображение выбрано (не null)
            // в противном случае вывести toast с сообщением, что картинка не выбрана
            iv.setImageBitmap(image);

            // 2) реализовать отображение размеров (Ш * В) изображения
            // в виде подписи к картинке
            /*
            final String[] columns = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
            };

            Cursor cursor = getContentResolver()
                    .query(uri, columns, null, null, null);
            Log.d("mytag", "cursor size:"+ cursor.getCount());
            cursor.moveToFirst();
            int width = cursor.getInt(1);
            int height = cursor.getInt(2);
            Log.d("mytag", "image w:"+width+"image h:"+height);
            */

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){
        Log.d("mytag", "TAG:"+v.getId());
        Intent i = new Intent(Intent.ACTION_PICK);


        i.setType("image/*");

        startActivityForResult(i, PICK_IMAGE);


    }
}
