package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

// import com.mgUnicorn.eh.BuildConfig; // Temporarily commented out


import com.mgUnicorn.eh.databinding.ActivityShowcasepaper2Binding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class showcasepaper2 extends AppCompatActivity implements OnPdfSelectListener {

    private MainAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;



    ActivityShowcasepaper2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowcasepaper2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        runtimePermission();

        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    private void runtimePermission(){

        Dexter.withContext(showcasepaper2.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                displayPdf();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    public ArrayList<File> findPdf(File file){

        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=file.listFiles();

        try {
            for (File singleFile : files) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {

                    arrayList.addAll(findPdf(singleFile));
                } else {

                    if (singleFile.getName().endsWith(".pdf")) {
                        arrayList.add(singleFile);
                    }
                }

            }


        }catch (Exception e){
        }
        return arrayList;
    }

    public void displayPdf(){

        recyclerView=findViewById(R.id.recyclerviewOfPdf);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        pdfList=new ArrayList<>();
        String folder_main = "Download";
        pdfList.addAll(findPdf(new File(Environment.getExternalStorageDirectory() + "/" + folder_main)));
        adapter=new MainAdapter(this,pdfList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnPdfSelected(File file) {
       // startActivity(new Intent(showcasepaper2.this, pdfActiivity.class).putExtra("PdfPath",file.getAbsolutePath()));


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pdfKey", file.getAbsolutePath());

        editor.apply();


        try {
            Uri contentUri = FileProvider.getUriForFile(this,
                    "com.mgUnicorn.eh.fileprovider",
                    file);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, contentUri);
            share.setPackage("com.whatsapp");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share PDF via"));

        }catch (Exception eh){
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);


        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}