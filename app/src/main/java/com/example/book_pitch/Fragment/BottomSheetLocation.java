package com.example.book_pitch.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.book_pitch.Models.MLocation;
import com.example.book_pitch.Models.User;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.example.book_pitch.Utils.UserUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BottomSheetLocation extends BottomSheetDialogFragment{
    private String latitude, longitude;
    private static final int REQUEST_LOCATION = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseFirestore firestore;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_location, null);

        firestore = FirebaseFirestore.getInstance();
        bottomSheetDialog.setContentView(view);
        init(view);
        return bottomSheetDialog;
    }
    private void init(View view) {
        TextView use_location = view.findViewById(R.id.use_location);

        use_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setCancelable(true);
//                dismiss();
                getMyLocation();
            }
        });
    }

    private void getMyLocation() {
        Log.d("test", "getMylocation");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if(buildConditionPermission()) {
            getCurrentLocation();
        }else askPermission();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Log.d("test", "getCurrentLocation");
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    Log.d("test", "co location");
                    handleLocation(location);
                }else requestLocation();
            }
        });

        Log.d("test", "qua if roi");
    }

    private void handleLocation(Location location) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            latitude = String.valueOf(addresses.get(0).getLatitude());
            longitude = String.valueOf(addresses.get(0).getLongitude());

            MLocation mLocation = new MLocation();
            mLocation.setId(String.valueOf(new Date()));
            mLocation.setLatitude(latitude);
            mLocation.setLongitude(longitude);

            firestore.collection("users").document(FirebaseUtil.currentUserId()).update("mLocation", mLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    User muser = UserUtil.getUser(requireContext());
                    muser.setmLocation(mLocation);
                    UserUtil.setUser(requireContext(), muser);
                    setCancelable(true);
                    dismiss();
                    AndroidUtil.showToast(getContext(), "Thành công");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            AndroidUtil.showToast(getContext(), "Không thể kết nối");
                        }
                    });

            Log.d("test", latitude);
            Log.d("test", longitude);

        }catch(IOException e){
            Log.d("test", "catch roi");
            e.printStackTrace();
        }
    }

    private boolean buildConditionPermission() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)  == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        Log.d("test", "requestLocation");

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .build();

        Log.d("test", "qua request");

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Log.d("test", "dayy ne");
                Location location = locationResult.getLastLocation();
                handleLocation(location);
                super.onLocationResult(locationResult);
            }
        };
        Log.d("test", "cuoi cugggg");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void askPermission() {
        Log.d("test", "askPermission");

        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_LOCATION);
        requestLocation();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("test", "result");
        if(requestCode == REQUEST_LOCATION) {
            Log.d("test", grantResults.toString());
            if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getMyLocation();
            }
        }
    }
}
