package com.example.book_pitch.Fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.book_pitch.Activities.DetailPitchActivity;
import com.example.book_pitch.Activities.NotificationActivity;
import com.example.book_pitch.Adapters.PopularAdapter;
import com.example.book_pitch.Models.Location;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Services.StadiumService;
import com.example.book_pitch.Utils.AndroidUtil;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PopularAdapter.PopularAdapterOnClickHandler {
    private RecyclerView recyclerPopular, rclNearMe;
    ProgressBar loading1, loading2;
    private ListView lvNearMe;
    private List<Stadium> stadiums;
    private boolean dataLoaded = false;

    ImageView btn_noti;
    PopularAdapter popularAdapter, nearMeAdapter;

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

        init(view);
        getData();
//        fetch();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!dataLoaded) {
            // Nếu chưa, thực hiện việc tải dữ liệu
            getData();
        }
    }

    private void init(View view) {
        recyclerPopular = view.findViewById(R.id.rclPopular);
        rclNearMe = view.findViewById(R.id.rclNearMe);
        btn_noti = view.findViewById(R.id.btn_noti);
        loading1 = view.findViewById(R.id.loading1);
        loading2 = view.findViewById(R.id.loading2);

        stadiums = new ArrayList<>();

        popularAdapter = new PopularAdapter(stadiums, this);
        recyclerPopular.setAdapter(popularAdapter);
        recyclerPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerPopular.setHasFixedSize(true);

//        rclNearMe.setAdapter(popularAdapter);
        rclNearMe.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rclNearMe.setHasFixedSize(true);
        rclNearMe.setNestedScrollingEnabled(false);

        btn_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
    }

//    public void fetch() {
//        StadiumService.getAll(new FirebaseUtil.OnDataLoadedListener<Stadium>() {
//            @Override
//            public void onDataLoaded(List<Stadium> stadiums) {
//                loadRecycleView(stadiums);
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                AndroidUtil.showToast(getActivity(), "Đã xảy ra lỗi !!!");
//            }
//        });
//    }

    public void getData() {
        FirebaseFirestore.getInstance().collection("stadiums").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Stadium stadium = document.toObject(Stadium.class);
                        stadiums.add(stadium);
//                        Log.d(TAG, document.getId() + " => " + data.toString());
                    }
                    popularAdapter.notifyDataSetChanged();
                    dataLoaded = true;
//                    onDataLoadedListener.onDataLoaded(dataList);
                }else {
//                    onDataLoadedListener.onError("Đã xảy ra lỗi nào đó");
                    Log.d(TAG, "Loi nao do");
                }
                loading1.setVisibility(View.GONE);
                loading2.setVisibility(View.GONE);
            }
        });
    }

//    private void loadRecycleView() {
//        popularAdapter = new PopularAdapter((ArrayList<Stadium>) stadiums, this);
//        recyclerPopular.setAdapter(popularAdapter);
//        recyclerPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerPopular.setHasFixedSize(true);
//    }

    @Override
    public void onClick(Stadium stadium) {
        Intent intent = new Intent(getActivity(), MessageItemActivity.class);
        startActivity(intent);
    }
}