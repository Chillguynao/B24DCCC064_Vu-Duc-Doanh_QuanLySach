package com.example.booklibrary;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends Activity {
    private Book book;
    private android.widget.Button readButton;
    private RatingBar rating;
    private boolean savingRating;

    private void addDetail(LinearLayout root, String caption, String value) {
        TextView key = Ui.text(this, caption.toUpperCase(), 12, Ui.GREEN);
        key.setLetterSpacing(.08f);
        LinearLayout.LayoutParams keyLp = Ui.lp(this, -1, -2); keyLp.topMargin = Ui.dp(this, 21); root.addView(key, keyLp);
        TextView text = Ui.text(this, value.isEmpty() ? "Chưa có ghi chú" : value, 16, value.isEmpty() ? Ui.MUTED : Ui.INK);
        LinearLayout.LayoutParams textLp = Ui.lp(this, -1, -2); textLp.topMargin = Ui.dp(this, 6); root.addView(text, textLp);
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        int index = getIntent().getIntExtra("book_index", -1);
        if (index < 0 || index >= BookStore.BOOKS.size()) { finish(); return; }
        book = BookStore.BOOKS.get(index);
        LinearLayout root = Ui.column(this);
        TextView back = Ui.text(this, "‹  TỦ SÁCH", 13, Ui.GREEN);
        root.addView(back, Ui.lp(this, -1, 28)); back.setOnClickListener(v -> finish());
        TextView title = Ui.heading(this, book.title);
        title.setLineSpacing(Ui.dp(this, 4), 1f);
        LinearLayout.LayoutParams titleLp = Ui.lp(this, -1, -2); titleLp.topMargin = Ui.dp(this, 14); root.addView(title, titleLp);
        TextView author = Ui.text(this, "bởi " + book.author, 16, Ui.MUTED);
        LinearLayout.LayoutParams authorLp = Ui.lp(this, -1, -2); authorLp.topMargin = Ui.dp(this, 6); root.addView(author, authorLp);
        addDetail(root, "Thể loại", book.genre);
        TextView statusText = Ui.text(this, book.status, 16, Ui.INK);
        LinearLayout.LayoutParams statusLp = Ui.lp(this, -1, -2); statusLp.topMargin = Ui.dp(this, 6); root.addView(statusText, statusLp);
        rating = new RatingBar(this);
        rating.setNumStars(5); rating.setStepSize(1); rating.setRating(book.rating);
        LinearLayout.LayoutParams ratingLp = Ui.lp(this, -2, 48); ratingLp.topMargin = Ui.dp(this, 6); root.addView(rating, ratingLp);
        savingRating = true;
        rating.setOnRatingBarChangeListener((bar, value, fromUser) -> { book.rating = value; });
        savingRating = false;
        addDetail(root, "Ghi chú", book.notes);
        LinearLayout spacer = new LinearLayout(this);
        root.addView(spacer, new LinearLayout.LayoutParams(-1, 0, 1));
        readButton = Ui.button(this, "✓   Đánh dấu đã đọc", true);
        readButton.setEnabled(!book.status.equals("Đã đọc"));
        if (book.status.equals("Đã đọc")) readButton.setText("✓   Đã hoàn thành");
        if (readButton.isEnabled()) readButton.setOnClickListener(v -> { book.status = "Đã đọc"; finish(); });
        LinearLayout.LayoutParams readLp = Ui.lp(this, -1, 54); readLp.topMargin = Ui.dp(this, 12); root.addView(readButton, readLp);
        android.widget.Button delete = Ui.button(this, "Xóa sách", false);
        LinearLayout.LayoutParams deleteLp = Ui.lp(this, -1, 50); deleteLp.topMargin = Ui.dp(this, 9); root.addView(delete, deleteLp);
        delete.setTextColor(Color.rgb(165, 68, 58));
        delete.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(this).setTitle("Xóa sách?").setMessage("“" + book.title + "” sẽ bị xóa khỏi tủ sách.").setNegativeButton("Hủy", (d, w) -> d.dismiss()).setPositiveButton("Xóa", (d, w) -> {
                BookStore.BOOKS.remove(book); Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show(); finish();
            }).show();
        });
        android.widget.Button backButton = Ui.button(this, "Quay lại", false);
        LinearLayout.LayoutParams backLp = Ui.lp(this, -1, 48); backLp.topMargin = Ui.dp(this, 8); root.addView(backButton, backLp);
        backButton.setOnClickListener(v -> finish());
        setContentView(root);
    }

}
