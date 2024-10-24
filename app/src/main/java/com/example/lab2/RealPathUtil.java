package com.example.lab2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class RealPathUtil {
    public static String getRealPath(Context context, Uri uri) {
        String result = null;
        ContentResolver contentResolver = context.getContentResolver();
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        return result;
    }
}
