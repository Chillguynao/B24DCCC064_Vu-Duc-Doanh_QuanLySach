package com.example.booklibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class Ui {
    public static final int INK = Color.rgb(34, 49, 43);
    public static final int MUTED = Color.rgb(119, 128, 121);
    public static final int GREEN = Color.rgb(43, 93, 75);
    public static final int BG = Color.rgb(247, 245, 240);

    private Ui() {}

    public static int dp(Activity a, int n) { return (int) (n * a.getResources().getDisplayMetrics().density + 0.5f); }

    public static LinearLayout column(Activity a) {
        LinearLayout v = new LinearLayout(a);
        v.setOrientation(LinearLayout.VERTICAL);
        v.setPadding(dp(a, 24), dp(a, 22), dp(a, 24), dp(a, 20));
        v.setBackgroundColor(BG);
        return v;
    }

    public static LinearLayout.LayoutParams lp(Activity a, int w, int h) {
        return new LinearLayout.LayoutParams(w < 0 ? w : dp(a, w), h < 0 ? h : dp(a, h));
    }

    public static TextView text(Activity a, String s, int size, int color) {
        TextView v = new TextView(a);
        v.setText(s);
        v.setTextSize(size);
        v.setTextColor(color);
        return v;
    }

    public static TextView heading(Activity a, String s) {
        TextView v = text(a, s, 29, INK);
        v.setTypeface(null, Typeface.BOLD);
        return v;
    }

    public static Button button(Activity a, String label, boolean primary) {
        Button b = new Button(a);
        b.setText(label);
        b.setAllCaps(false);
        b.setTextSize(15);
        b.setTextColor(primary ? Color.WHITE : GREEN);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(primary ? GREEN : Color.rgb(231, 238, 232));
        bg.setCornerRadius(dp(a, 16));
        b.setBackground(bg);
        b.setMinHeight(dp(a, 52));
        b.setPadding(dp(a, 12), 0, dp(a, 12), 0);
        return b;
    }

    public static void open(Activity a, Class<?> cls) { a.startActivity(new Intent(a, cls)); }

    public static void labelStyle(TextView view) { view.setGravity(Gravity.CENTER_VERTICAL); }
}
