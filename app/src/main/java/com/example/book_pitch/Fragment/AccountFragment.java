package com.example.book_pitch.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.book_pitch.Activities.BookedAndHistoryActivity;
import com.example.book_pitch.Activities.EditProfileActivity;
import com.example.book_pitch.Activities.FavouriteActivity;
import com.example.book_pitch.Activities.LoginPhoneNumberActivity;
import com.example.book_pitch.Activities.MainActivity;
import com.example.book_pitch.Activities.SettingActivity;
import com.example.book_pitch.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AccountFragment extends Fragment {
    FirebaseAuth mAuth;
    LinearLayout history_book;
    TextView displayNameUser;
    TextView phoneNumberUser;
    TextView addressUser;
    TextView editProfileBtn;
    LinearLayout actionUserContainer;
    LinearLayout favourite;
    LinearLayout setting;
    Button loginBtn;
    FirebaseFirestore fireStore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        history_book = view.findViewById(R.id.history_book);
        displayNameUser = view.findViewById(R.id.displayNameUser);
        phoneNumberUser = view.findViewById(R.id.phoneNumberUser);
        addressUser = view.findViewById(R.id.addressUser);
        loginBtn = view.findViewById(R.id.loginBtn);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);
        actionUserContainer = view.findViewById(R.id.actionUserContainer);
        favourite = view.findViewById(R.id.favourite);
        setting = view.findViewById(R.id.setting);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginPhoneNumberActivity.class);
                startActivity(i);
            }
        });
        if(mAuth.getCurrentUser() == null) {
            loginBtn.setVisibility(View.VISIBLE);
            displayNameUser.setVisibility(View.GONE);
            phoneNumberUser.setVisibility(View.GONE);
            addressUser.setVisibility(View.GONE);
            editProfileBtn.setVisibility(View.GONE);
            actionUserContainer.setVisibility(View.GONE);
        } else {
            String phoneNumber = mAuth.getCurrentUser().getPhoneNumber();
            if (phoneNumber != null) {
                fireStore.collection("users")
                        .whereEqualTo("phoneNumber", phoneNumber)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.exists()) {
                                        // Hiển thị thông tin người dùng
                                        String displayName = document.getString("displayName");
                                        String phoneNumberStr = document.getString("phoneNumber");
                                        String address = document.getString("address");

                                        displayNameUser.setText(displayName);
                                        phoneNumberUser.setText(phoneNumberStr);
                                        addressUser.setText("Địa chỉ: " + address);

                                        // Hiển thị thông tin người dùng và nút đăng xuất, ẩn nút đăng nhập
                                        loginBtn.setVisibility(View.GONE);
                                        displayNameUser.setVisibility(View.VISIBLE);
                                        phoneNumberUser.setVisibility(View.VISIBLE);
                                        addressUser.setVisibility(View.VISIBLE);
                                        editProfileBtn.setVisibility(View.VISIBLE);
                                        actionUserContainer.setVisibility(View.VISIBLE);
                                    } else {
                                        System.out.println("No such document!");
                                    }
                                }
                            } else {
                                System.err.println("Error getting user information: " + task.getException().getMessage());
                            }
                        });
            } else {
                fireStore.collection("users")
                        .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.exists()) {
                                        // Hiển thị thông tin người dùng
                                        String displayName = document.getString("displayName");
                                        String phoneNumberStr = document.getString("phoneNumber");
                                        String address = document.getString("address");

                                        displayNameUser.setText(displayName);
                                        phoneNumberUser.setText(phoneNumberStr);
                                        addressUser.setText("Địa chỉ: " + address);

                                        // Hiển thị thông tin người dùng và nút đăng xuất, ẩn nút đăng nhập
                                        loginBtn.setVisibility(View.GONE);
                                        displayNameUser.setVisibility(View.VISIBLE);
                                        phoneNumberUser.setVisibility(View.VISIBLE);
                                        addressUser.setVisibility(View.VISIBLE);
                                        editProfileBtn.setVisibility(View.VISIBLE);
                                        actionUserContainer.setVisibility(View.VISIBLE);
                                    } else {
                                        System.out.println("No such document!");
                                    }
                                }
                            } else {
                                System.err.println("Error getting user information: " + task.getException().getMessage());
                            }
                        });
            }

        }
        history_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BookedAndHistoryActivity.class);
                startActivity(i);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                i.putExtra("email", mAuth.getCurrentUser().getEmail());
                startActivity(i);
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FavouriteActivity.class);
                startActivity(i);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });
    }
}