package com.cook.rotenzonew.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cook.rotenzonew.R;

import static com.cook.rotenzonew.Activity.MainActivity.toolbar_title;

public class Rate_Review_fragment extends Fragment {

    Button btn_write_review,btn_rate_app;

    public Rate_Review_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_rate__review_fragment, container, false);
        getActivity().setTitle(R.string.share_exp);
        toolbar_title.setText(R.string.share_exp);
        btn_write_review=root.findViewById(R.id.btn_write_review);
        btn_rate_app=root.findViewById(R.id.btn_rate_app);


        btn_rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rate_app_playstore();

            }
        });

        btn_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rate_app_playstore();

            }
        });


        return root;
    }

    private void rate_app_playstore() {

        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));

        }

    }

}
