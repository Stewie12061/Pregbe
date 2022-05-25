package com.example.pregbe.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.example.pregbe.Music.ChonNhacActivity;
//import com.example.pregbe.Music.MusicActivity;
import com.example.pregbe.R;


public class CongcuFragment extends Fragment {

    Button next, previous, btn_baby, btn_relax, btn_mom, btn_radio, btn_truyen;
    SharedPreferences sharedPreferences;
    private ViewFlipper view_flipper;
    View v;

    public CongcuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_congcu, container, false);

        // Inflate the layout for this fragment
        view_flipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        sharedPreferences = getActivity().getSharedPreferences("MusicList", MODE_PRIVATE);
        btn_baby = (Button) view.findViewById(R.id.btn_baby);
        btn_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("List", "Baby");
                editor.commit();
                Intent i = new Intent(getActivity(), ChonNhacActivity.class);
                startActivity(i);
            }
        });

        btn_mom = (Button) view.findViewById(R.id.btn_mom);
       btn_mom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("List", "Mom");
                editor.commit();
                Intent intent = new Intent(getActivity(), ChonNhacActivity.class);
                startActivity(intent);
            }
        });

        btn_radio = (Button) view.findViewById(R.id.btn_radio);
        btn_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("List", "Radio");
                editor.commit();
                Intent t = new Intent(getActivity(), ChonNhacActivity.class);
                startActivity(t);
            }
        });

        btn_relax = (Button) view.findViewById(R.id.btn_relax);
        btn_relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("List", "Relax");
                editor.commit();
                Intent intent = new Intent(getActivity(), ChonNhacActivity.class);
                startActivity(intent);
            }
        });

        btn_truyen = (Button) view.findViewById(R.id.btn_truyen);
        btn_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("List", "Truyen Thai Giao");
                editor.commit();
                Intent intent = new Intent(getActivity(), ChonNhacActivity.class);
                startActivity(intent);
            }
        });

        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_flipper.showNext();
            }
        });

        previous =(Button) view.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_flipper.showPrevious();
            }
        });
return view;
    }
//    private void previousView() {
//        view_flipper.showPrevious();
//
//    }
//
//    private void nextView() {
//        view_flipper.showNext();
//    }
}