package com.example.booklibrary;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = Ui.column(this);
        root.setGravity(Gravity.CENTER_VERTICAL);
        TextView kicker = Ui.text(this, "THƯ VIỆN CỦA BẠN", 12, Ui.GREEN);
        kicker.setLetterSpacing(0.14f);
        root.addView(kicker, Ui.lp(this, -1, -2));
        TextView title = Ui.heading(this, "Mỗi cuốn sách,\nmột hành trình.");
        title.setLineSpacing(Ui.dp(this, 6), 1f);
        LinearLayout.LayoutParams titleLp = Ui.lp(this, -1, -2);
        titleLp.bottomMargin = Ui.dp(this, 12);
        root.addView(title, titleLp);
        TextView intro = Ui.text(this, "Đăng nhập để tiếp tục hành trình đọc của bạn.", 15, Ui.MUTED);
        LinearLayout.LayoutParams introLp = Ui.lp(this, -1, -2);
        introLp.bottomMargin = Ui.dp(this, 34);
        root.addView(intro, introLp);
        EditText username = new EditText(this);
        username.setSingleLine(true);
        username.setHint("Tên đăng nhập");
        username.setPadding(Ui.dp(this, 16), 0, Ui.dp(this, 16), 0);
        username.setBackgroundColor(Color.WHITE);
        root.addView(username, Ui.lp(this, -1, 54));
        EditText password = new EditText(this);
        password.setSingleLine(true);
        password.setHint("Mật khẩu");
        password.setInputType(129);
        password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        password.setPadding(Ui.dp(this, 16), 0, Ui.dp(this, 16), 0);
        password.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams passLp = Ui.lp(this, -1, 54);
        passLp.topMargin = Ui.dp(this, 14);
        root.addView(password, passLp);
        TextView error = Ui.text(this, "", 14, Color.rgb(180, 58, 50));
        LinearLayout.LayoutParams errorLp = Ui.lp(this, -1, -2);
        errorLp.topMargin = Ui.dp(this, 8);
        root.addView(error, errorLp);
        android.widget.Button login = Ui.button(this, "Đăng nhập", true);
        LinearLayout.LayoutParams buttonLp = Ui.lp(this, -1, 54);
        buttonLp.topMargin = Ui.dp(this, 18);
        root.addView(login, buttonLp);
        TextView hint = Ui.text(this, "Tài khoản thực hành: admin  ·  Mật khẩu: 123456", 12, Ui.MUTED);
        hint.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams hintLp = Ui.lp(this, -1, -2);
        hintLp.topMargin = Ui.dp(this, 20);
        root.addView(hint, hintLp);
        login.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                error.setText("Vui lòng nhập tên đăng nhập và mật khẩu.");
            } else if (user.equals("admin") && pass.equals("123456")) {
                BookStore.initialize();
                Ui.open(this, MainActivity.class);
                finish();
            } else {
                error.setText("Sai tên đăng nhập hoặc mật khẩu");
            }
        });
        setContentView(root);
    }
}
