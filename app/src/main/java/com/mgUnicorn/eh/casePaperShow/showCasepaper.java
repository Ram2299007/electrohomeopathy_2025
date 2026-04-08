package com.mgUnicorn.eh.casePaperShow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mgUnicorn.eh.MainAdapter;
import com.mgUnicorn.eh.OnPdfSelectListener;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.databinding.ActivityShowCasepaperBinding;
import com.mgUnicorn.eh.pdfActiivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class showCasepaper extends AppCompatActivity implements OnPdfSelectListener {

    private MainAdapter adapter;
    private List<File> pdfList;
    private RecyclerView recyclerView;



    ActivityShowCasepaperBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowCasepaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        runtimePermission();

        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    private void runtimePermission(){

        Dexter.withContext(showCasepaper.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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
        startActivity(new Intent(showCasepaper.this, pdfActiivity.class).putExtra("PdfPath",file.getAbsolutePath()));




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