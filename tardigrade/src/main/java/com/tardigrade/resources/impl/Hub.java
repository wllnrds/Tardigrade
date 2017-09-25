package com.tardigrade.resources.impl;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.tardigrade.comunication.IHub;

public class Hub implements IHub {
    private static Activity mContext;
    private static Hub ourInstance = null;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private Hub() {
    }

    public static Hub getInstance(Activity context) {
        if (ourInstance == null) {
            if (context != null) {
                mContext = context;
                ourInstance = new Hub();
            }
        }
        return ourInstance;
    }

    public void readQRCard(){
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            mContext.startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            mContext.startActivity(marketIntent);
        }
    }

    public static void destroy() {
        ourInstance = null;
        mContext = null;
    }
}