package com.example.myvideoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sachin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //Uri uri = Uri.parse("android.content://com.example.myvideoapp/raw/hasil_movie_scene_1");
        String fileImagePath = "android.resource://" + getPackageName() + "/" + R.raw.hasil_movie_scene_1;
        //File fileImagePath = new File(getFilesDir().getPath() + "/hasil_movie_scene_1.mp4");

        Uri uri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(fileImagePath));
        //Uri uri = Uri.parse("content://" + getPackageName() + "/" + R.raw.hasil_movie_scene_1);
        //Log.d(TAG,uri.getUserInfo().toString());

        //String path = getFilesDir().getPath();
        //Log.d(TAG," path = "+path);

        /*(if (!imageFile.exists()) { // image isn't in the files dir, copy from the res/raw
            final InputStream inputStream =getResources().openRawResource(R.raw.hasil_movie_scene_1);
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput("image", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte buf[] = new byte[1024];
            int len;
            while (true) {
                try {
                    if (!((len = inputStream.read(buf)) > 0)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.write(buf, 0, len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageFile = new File(getFilesDir().getPath() + "/image");
        }

        if (!imageFile.exists()) {
            try {
                throw new IOException("couldn't find file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Uri uri = Uri.fromFile(imageFile);
        */
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        File file = new File(uri.getPath());

        //MimeTypeMap mime = MimeTypeMap.getSingleton();
        //String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        //String type = mime.getMimeTypeFromExtension(ext);


        intent.setDataAndType(Uri.fromFile(file), "video/*");
        //intent.setType("video/*");
        //String path_2 = "android.resource://" + getPackageName() + "/" + R.raw.hasil_movie_scene_1;
        //intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(path_2));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
    }
}
