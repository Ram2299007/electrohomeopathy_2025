package com.mgUnicorn.eh.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgUnicorn.eh.Adapter.TypesOfDilution;
import com.mgUnicorn.eh.AtoZ.A;
import com.mgUnicorn.eh.CancerAndEh;
import com.mgUnicorn.eh.ExternalApplication;
import com.mgUnicorn.eh.SpeOfAge.Count;
import com.mgUnicorn.eh.SpeOfAge.EhRemedy;
import com.mgUnicorn.eh.TemparamentMaterial;
import com.mgUnicorn.eh.databinding.FragmentCallsBinding;


public class callsFragment extends Fragment {



    //facebook ads

    private final String TAG = callsFragment.class.getSimpleName();

    FragmentCallsBinding binding;
    Context context;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public callsFragment() {
        // Required empty public constructor
    }


    public static callsFragment newInstance(String param1, String param2) {
        callsFragment fragment = new callsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCallsBinding.inflate(inflater,container,false);






        binding.Temperament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TemparamentMaterial.class);
                startActivity(intent);
            }
        });

        binding.CountIntroduction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), Count.class);
                startActivity(intent);
            }
        });

        binding.EhRemedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), EhRemedy.class);
                startActivity(intent);
            }
        });

        binding.ExternalApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), ExternalApplication.class);
                startActivity(intent);
            }
        });


        binding.TypesOfDilution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), TypesOfDilution.class);
                startActivity(intent);
            }
        });


        binding.CancerAndEh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), CancerAndEh.class);
                startActivity(intent);
            }
        });

        binding.AtoZdiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), A.class);
                startActivity(intent);
            }
        });





        return binding.getRoot();


    }


}