package com.example.myvideoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sachin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Field[] fields=R.raw.class.getFields();
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int count=0;count<fields.length;count++){
            arrayList.add(fields[count].getName());
        }
        for(int res_count=0;res_count<fields.length;res_count++){
            try {
                copyRawResource(fields[res_count].getInt(fields[res_count]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for(String ele:arrayList){
            Log.d(TAG,"array elements are "+ele);
        }
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File imageFile = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                //Log.d(TAG," path = "+path.getAbsolutePath());
                //list all the files in app specific path


                final Uri uri = Uri.fromFile(imageFile);
                Log.d(TAG,"final uri = "+uri.getPath());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                File file = new File(uri.getPath());
                intent.setDataAndType(Uri.fromFile(file), "video/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(intent);
            }
        });
        //addRecyclerView();
        /*
        //copyRawResource(R.raw.hasil_movie_scene_1);
        File imageFile = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        //Log.d(TAG," path = "+path.getAbsolutePath());
        //list all the files in app specific path


        final Uri uri = Uri.fromFile(imageFile);
        Log.d(TAG,"final uri = "+uri.getPath());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        File file = new File(uri.getPath());
        intent.setDataAndType(Uri.fromFile(file), "video/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
        */
    }

    private void addRecyclerView() {
        List<Data> data = fill_with_data();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Data> fill_with_data() {
        List<Data> data = new ArrayList<>();
        data.add(new Data("hasil_movie_scene_1"));
        return data;
    }

    private void copyRawResource(int resId) {
        Log.i("Test", "Setup::copyResources");
        InputStream in = getResources().openRawResource(resId);
        String filename = getResources().getResourceEntryName(resId);

        File f = new File(filename+".mp4");

        if(!f.exists()){
            try {
                //OutputStream out = new FileOutputStream(new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_MOVIES))), Boolean.parseBoolean(filename));
                File appSpecificExternalDir = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
                OutputStream out = new FileOutputStream(appSpecificExternalDir);
                byte[] buffer=new byte[1024];
                int len;
                while((len = in.read(buffer, 0, buffer.length)) != -1){
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                Log.i("Test", "Setup::copyResources - "+e.getMessage());
            } catch (IOException e) {
                Log.i("Test", "Setup::copyResources - "+e.getMessage());
            }
        }
    }
}
