package com.mgUnicorn.eh.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mgUnicorn.eh.BMI;
import com.mgUnicorn.eh.LegalActivity;
import com.mgUnicorn.eh.OpdRegistration;
import com.mgUnicorn.eh.pptView;
import com.mgUnicorn.eh.showcasepaper2;



import com.mgUnicorn.eh.databinding.FragmentChatsBinding;


public class chatsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //facebook ads

    private final String TAG = chatsFragment.class.getSimpleName();


    private String mParam1;
    private String mParam2;

    public chatsFragment() {

    }

    FragmentChatsBinding binding;
        /* ArrayList<Users>list=new ArrayList<>();
        FirebaseDatabase database;
         usersAdapter   adapter;

         */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentChatsBinding.inflate(inflater, container, false);

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown





        binding.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://jyotirlingadvanceehcenter.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.secondopinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), showcasepaper2.class));

            }
        });

        binding.bmi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), BMI.class));
            }
        });


        binding.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //   startActivity(new Intent(getActivity(),testxml.class));
            }
        });
        binding.opd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   startActivity(new Intent(getActivity(), OpdRegistration.class));
            }
        });


        binding.pptFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), pptView.class));



            }
        });

        binding.legalStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), LegalActivity.class));


            }
        });


        return binding.getRoot();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }


}












