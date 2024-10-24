package com.example.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.contactNameTextView.setText(contact.getName());
        holder.contactPhoneTextView.setText(contact.getPhoneNumber());
        holder.contactCheckBox.setChecked(contact.isStatus());

        holder.contactCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            contact.setStatus(isChecked);
        });

        // Kiểm tra xem đường dẫn ảnh có tồn tại không
        if (contact.getImagePath() != null) {
            File imgFile = new File(contact.getImagePath());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.contactImageView.setImageBitmap(myBitmap);
            } else {
                // Nếu không có ảnh, sử dụng ảnh mặc định
                holder.contactImageView.setImageResource(R.drawable.ic_default_avatar);
            }
        } else {
            // Nếu không có đường dẫn ảnh, sử dụng ảnh mặc định
            holder.contactImageView.setImageResource(R.drawable.ic_default_avatar);
        }

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactNameTextView;
        TextView contactPhoneTextView;
        ImageView contactImageView;
        CheckBox contactCheckBox;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView);
            contactPhoneTextView = itemView.findViewById(R.id.contactPhoneTextView);
            contactImageView = itemView.findViewById(R.id.contactImageView);
            contactCheckBox = itemView.findViewById(R.id.contactCheckBox);
        }
    }
}
