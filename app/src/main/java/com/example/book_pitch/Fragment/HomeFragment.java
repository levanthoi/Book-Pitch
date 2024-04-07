package com.example.book_pitch.Fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.book_pitch.Activities.DetailPitchActivity;
import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Activities.SearchActivity;
import com.example.book_pitch.Adapters.PopularAdapter;
import com.example.book_pitch.Adapters.WrapContentGridLayoutManager;
import com.example.book_pitch.Adapters.WrapContentLinearLayoutManager;
import com.example.book_pitch.Models.Location;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Services.StadiumService;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PopularAdapter.PopularAdapterOnClickHandler {
    private RecyclerView recyclerPopular, rclNearMe;
    ProgressBar loading1, loading2;
    private List<Stadium> stadiums;
    private boolean dataLoaded = false;
    private boolean isSearchViewExpanded = false;

    ImageView btn_noti;
    TextView search_home;
    PopularAdapter popularAdapter;
    FirebaseFirestore firestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Fragment sliderFragment1 = new SliderFragment();
//        Fragment sliderFragment2 = new SliderFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.sliderFragmentLayout, sliderFragment1).commit();
//        transaction.replace(R.id.sliderFragmentLayout2, sliderFragment2).commit();

        firestore = FirebaseFirestore.getInstance();

        init(view);
//        handleToolbarAnimation(view);
//        getData();
        return view;
    }

    private void init(View view) {
        recyclerPopular = view.findViewById(R.id.rclPopular);
        rclNearMe = view.findViewById(R.id.rclNearMe);
        btn_noti = view.findViewById(R.id.btn_noti);
        search_home = view.findViewById(R.id.search_home);

//        loading1 = view.findViewById(R.id.loading1);
//        loading2 = view.findViewById(R.id.loading2);



//        loading1.setVisibility(View.GONE);
//        loading2.setVisibility(View.GONE);


        recyclerPopular.setLayoutManager(new WrapContentLinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        rclNearMe.setLayoutManager(new WrapContentGridLayoutManager(getActivity(), 1));

//        recyclerPopular.setHasFixedSize(true);
        // Khởi tạo FirestoreRecyclerOptions
        Query query = firestore.collection("stadiums");
        FirestoreRecyclerOptions<Stadium> options = new FirestoreRecyclerOptions.Builder<Stadium>()
                .setQuery(query, Stadium.class)
                .build();

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerPopular);
        helper.attachToRecyclerView(rclNearMe);

        popularAdapter = new PopularAdapter(options, this);
        popularAdapter.notifyDataSetChanged();
        recyclerPopular.setAdapter(popularAdapter);


//        rclNearMe.setHasFixedSize(true);

        rclNearMe.setAdapter(popularAdapter);
        rclNearMe.setNestedScrollingEnabled(false);

        btn_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);

            }
        });
        Animation shakeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation);
        btn_noti.startAnimation(shakeAnimation);
        search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData() {
        // Khởi tạo FirestoreRecyclerOptions
        Query query = firestore.collection("stadiums");
        FirestoreRecyclerOptions<Stadium> options = new FirestoreRecyclerOptions.Builder<Stadium>()
                .setQuery(query, Stadium.class)
                .build();
        popularAdapter.startListening();
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerPopular);
        popularAdapter.notifyDataSetChanged();
        recyclerPopular.setAdapter(popularAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bắt đầu lắng nghe sự kiện từ Firestore khi Fragment được hiển thị
        popularAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Dừng lắng nghe sự kiện từ Firestore khi Fragment bị ẩn
        popularAdapter.stopListening();
    }

    private void handleToolbarAnimation(View v) {
//        CollapsingToolbarLayout collapsingToolbarLayout;
//        collapsingToolbarLayout = v.findViewById(R.id.collapsing);
//        AppBarLayout appBarLayout = v.findViewById(R.id.appbar_layout_home);
//        Toolbar toolbar = v.findViewById(R.id.toolbar_home);
//        if (getActivity() instanceof AppCompatActivity) {
//            AndroidUtil.showToast(getContext(), "HIIHIH");
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        }
    }

    @Override
    public void onClick(Stadium stadium) {
        Intent intent = new Intent(getActivity(), DetailPitchActivity.class);
        Gson gson = new Gson();
        intent.putExtra("stadium", gson.toJson(stadium));
        startActivity(intent);
    }
}