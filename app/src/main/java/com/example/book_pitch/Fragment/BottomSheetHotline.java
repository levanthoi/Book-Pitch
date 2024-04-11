package com.example.book_pitch.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.book_pitch.Activities.MessageItemActivity;
import com.example.book_pitch.Models.MessageGroup;
import com.example.book_pitch.Models.Stadium;
import com.example.book_pitch.R;
import com.example.book_pitch.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetHotline extends BottomSheetDialogFragment {
    private static final int REQUEST_CALL_PHONE = 1;
    Button btn_phone, btn_message;
    TextView copy_phone, text_phone, title_contact, address_contact;
    Context ctx;
    Stadium stadium;

    public BottomSheetHotline(Context ctx, Stadium stadium) {
        this.ctx = ctx;
        this.stadium = stadium;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_hotline, null);

        bottomSheetDialog.setContentView(view);
        render(view);
        return bottomSheetDialog;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        render(view);
//    }

    private void render(View view) {
        btn_phone = view.findViewById(R.id.btn_phone);
        btn_message = view.findViewById(R.id.btn_message);
        copy_phone = view.findViewById(R.id.copy_phone);
        text_phone = view.findViewById(R.id.text_phone);
        title_contact = view.findViewById(R.id.title_contact);
        address_contact = view.findViewById(R.id.address_contact);

        if(stadium != null) {
            title_contact.setText(stadium.getTitle());
            address_contact.setText(stadium.getAddress());
            text_phone.setText(stadium.getPhone());
        }


        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessageItemActivity();
            }
        });

        copy_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard();
            }
        });
    }

    private void copyToClipboard() {
        String phone = text_phone.getText().toString().trim();
        ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if(clipboardManager != null){
            ClipData clipData = ClipData.newPlainText("phone", phone);
            clipboardManager.setPrimaryClip(clipData);
            showCopySuccessToast("Sao chép thành công");
        }
    }

    private void openMessageItemActivity() {
        if (stadium != null) {
            DatabaseReference messageGroupRef = FirebaseDatabase.getInstance().getReference().child("message_group");
            String currentUserId = FirebaseUtil.currentUserId();
            String stadiumOwnerId = stadium.getUser_id();

            // Kiểm tra xem có message group nào chứa cả 2 id khong
            messageGroupRef.orderByChild("members").equalTo(currentUserId + "_" + stadiumOwnerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            goToMessageItemActivity(snapshot.getValue(MessageGroup.class));
                            return;
                        }
                    } else {
                        createAndGoToMessageItemActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Failed to check message group.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), "Stadium information is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAndGoToMessageItemActivity() {
        DatabaseReference messageGroupRef = FirebaseDatabase.getInstance().getReference().child("message_group");
        String currentUserId = FirebaseUtil.currentUserId();
        String toId = stadium.getUser_id();
        String groupId = messageGroupRef.push().getKey();
        List<String>members = new ArrayList<>();
        members.add(currentUserId);
        members.add(toId);

        MessageGroup messageGroup = new MessageGroup(groupId, "", members, stadium.getTitle());

        messageGroupRef.child(groupId).setValue(messageGroup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    goToMessageItemActivity(messageGroup);
                } else {
                    Toast.makeText(requireContext(), "Failed to create message group.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToMessageItemActivity(MessageGroup messageGroup) {
        Intent intent = new Intent(getContext(), MessageItemActivity.class);
        Gson gson = new Gson();
        intent.putExtra("messageGroup", gson.toJson(messageGroup));
        startActivity(intent);
    }



    private void makePhoneCall() {
        if(ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:0339083266"));
            startActivity(intent);
        }
    }

    private void showCopySuccessToast(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
    }


}
