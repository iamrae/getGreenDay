package com.example.getgreenday.util;

import android.content.Context;
import android.view.View;
import com.example.getgreenday.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {


    public static void inflateSnackBar(Context context, View view, boolean addToFavorite) {
        // 01: 추가, 00: 삭제

        String msg = (addToFavorite) ? context.getString(R.string.msg_addedToFavorites) :
                context.getString(R.string.msg_removedFromFavorites);

        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }


}
