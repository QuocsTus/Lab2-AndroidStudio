package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_CONTACT_REQUEST = 1;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();

        // Thêm sẵn 2 contact vào danh sách
        contactList.add(new Contact(1, "Mot", "34567", false,null));
        contactList.add(new Contact(2, "Hai", "0987", false,null));

        adapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở AddContactActivity để thêm mới liên hệ
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, ADD_CONTACT_REQUEST);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận trước khi xóa
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa các liên hệ đã chọn?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            // Xóa các Contact đã chọn
                            for (int i = contactList.size() - 1; i >= 0; i--) {
                                if (contactList.get(i).isStatus()) {
                                    contactList.remove(i);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Đã xóa các liên hệ được chọn", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");

            // Thêm liên hệ mới vào danh sách
            contactList.add(new Contact(contactList.size() + 1, name, phone, false,null));
            adapter.notifyDataSetChanged();
        }
    }
}
