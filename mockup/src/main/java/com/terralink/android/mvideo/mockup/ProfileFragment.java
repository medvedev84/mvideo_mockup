package com.terralink.android.mvideo.mockup;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //do business logic here

        TextView dateText = (TextView)rootView.findViewById(R.id.profile_date);
        dateText.setText("31.12.2014");
        TextView nameText = (TextView)rootView.findViewById(R.id.profile_name);
        nameText.setText("John Smith");
        TextView courierText = (TextView)rootView.findViewById(R.id.courier_name);
        courierText.setText("Tom Tailor Jr.");
        TextView vehicleText = (TextView)rootView.findViewById(R.id.profile_vehicle);
        vehicleText.setText("A001AA RUS 01");

        Button logoutBtn = (Button) rootView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AuthActivity.class));
            }
        });
        return rootView;
    }
}
