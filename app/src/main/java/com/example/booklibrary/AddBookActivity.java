package com.example.booklibrary;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AddBookActivity extends Activity {
    private EditText title;
    private EditText author;
    private EditText notes;
    private Spinner genre;
    private RadioGroup status;
    private RatingBar rating;
    private TextView stars;

    private TextView label(LinearLayout root, String value) {
        TextView view = Ui.text(this, value, 14, Ui.INK);
        view.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams lp = Ui.lp(this, -1, -2);
        lp.topMargin = Ui.dp(this, 17);
        lp.bottomMargin = Ui.dp(this, 6);
        root.addView(view, lp);
        return view;
    }

    private EditText input(LinearLayout root, String hint, int lines) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setSingleLine(lines == 1);
        e.setMinHeight(Ui.dp(this, lines == 1 ? 50 : 90));
        e.setPadding(Ui.dp(this, 12), Ui.dp(this, 8), Ui.dp(this, 12), Ui.dp(this, 8));
        e.setBackgroundColor(Color.WHITE);
        if (lines > 1) { e.setGravity(Gravity.TOP); e.setInputType(0x00020001 | 0x00002000); }
        root.addView(e, Ui.lp(this, -1, lines == 1 ? 50 : 90));
        return e;
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = Ui.column(this);
        TextView back = Ui.text(this, "‹  TỦ SÁCH", 13, Ui.GREEN);
        root.addView(back, Ui.lp(this, -1, 28));
        back.setOnClickListener(v -> finish());
        root.addView(Ui.heading(this, "Thêm sách mới"), Ui.lp(this, -1, -2));
        label(root, "TÊN SÁCH"); title = input(root, "Tên cuốn sách", 1);
        label(root, "TÁC GIẢ"); author = input(root, "Tên tác giả", 1);
        label(root, "THỂ LOẠI");
        genre = new Spinner(this);
        genre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"}));
        root.addView(genre, Ui.lp(this, -1, 48));
        label(root, "TÌNH TRẠNG ĐỌC");
        status = new RadioGroup(this);
        status.setOrientation(RadioGroup.HORIZONTAL);
        String[] statuses = {"Chưa đọc", "Đang đọc", "Đã đọc"};
        int[] statusIds = {R.id.status_unread, R.id.status_reading, R.id.status_read};
        for (int i = 0; i < statuses.length; i++) {
            RadioButton r = new RadioButton(this);
            r.setId(statusIds[i]); r.setText(statuses[i]); r.setTextSize(12);
            status.addView(r, new RadioGroup.LayoutParams(0, Ui.dp(this, 46), 1));
        }
        status.check(R.id.status_unread);
        root.addView(status, Ui.lp(this, -1, 48));
        label(root, "ĐÁNH GIÁ");
        LinearLayout ratingLine = new LinearLayout(this);
        ratingLine.setGravity(Gravity.CENTER_VERTICAL);
        rating = new RatingBar(this);
        rating.setNumStars(5); rating.setStepSize(1); rating.setRating(0);
        ratingLine.addView(rating, new LinearLayout.LayoutParams(-2, Ui.dp(this, 48)));
        stars = Ui.text(this, "0 sao", 13, Ui.MUTED);
        LinearLayout.LayoutParams starLp = Ui.lp(this, -2, -2); starLp.leftMargin = Ui.dp(this, 12); ratingLine.addView(stars, starLp);
        rating.setOnRatingBarChangeListener((bar, value, fromUser) -> stars.setText((int)value + " sao"));
        root.addView(ratingLine, Ui.lp(this, -1, 48));
        label(root, "GHI CHÚ"); notes = input(root, "Thêm ghi chú cho cuốn sách...", 3);
        android.widget.Button save = Ui.button(this, "Lưu vào tủ sách", true);
        LinearLayout.LayoutParams saveLp = Ui.lp(this, -1, 54); saveLp.topMargin = Ui.dp(this, 18); root.addView(save, saveLp);
        save.setOnClickListener(v -> saveBook());
        setContentView(root);
    }

    private void saveBook() {
        String bookTitle = title.getText().toString().trim();
        String bookAuthor = author.getText().toString().trim();
        if (bookTitle.isEmpty()) { title.setError("Vui lòng nhập tên sách"); title.requestFocus(); return; }
        if (bookAuthor.isEmpty()) { author.setError("Vui lòng nhập tên tác giả"); author.requestFocus(); return; }
        int checked = status.getCheckedRadioButtonId();
        String state = ((RadioButton) findViewById(checked)).getText().toString();
        BookStore.BOOKS.add(new Book(bookTitle, bookAuthor, genre.getSelectedItem().toString(), state, rating.getRating(), notes.getText().toString().trim()));
        finish();
    }
}
