package com.example.booklibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private LinearLayout root;
    private TextView stats;
    private ListView list;
    private ArrayAdapter<Book> adapter;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        BookStore.initialize();
        build();
    }

    @Override protected void onResume() { super.onResume(); refresh(); }

    private void build() {
        root = Ui.column(this);
        TextView kicker = Ui.text(this, "LA MÌNH VÀ CUỐN SÁCH", 12, Ui.GREEN);
        kicker.setLetterSpacing(.12f);
        root.addView(kicker, Ui.lp(this, -1, -2));
        TextView title = Ui.heading(this, "Tủ sách của tôi");
        LinearLayout.LayoutParams titleLp = Ui.lp(this, -1, -2);
        titleLp.topMargin = Ui.dp(this, 5);
        root.addView(title, titleLp);
        stats = Ui.text(this, "", 14, Ui.MUTED);
        LinearLayout.LayoutParams statsLp = Ui.lp(this, -1, -2);
        statsLp.topMargin = Ui.dp(this, 8);
        statsLp.bottomMargin = Ui.dp(this, 20);
        root.addView(stats, statsLp);
        list = new ListView(this);
        list.setDivider(null);
        list.setDividerHeight(Ui.dp(this, 10));
        list.setBackgroundColor(Ui.BG);
        adapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, BookStore.BOOKS) {
            @Override public View getView(int position, View convert, android.view.ViewGroup parent) {
                LinearLayout card = new LinearLayout(MainActivity.this);
                card.setOrientation(LinearLayout.VERTICAL);
                card.setPadding(Ui.dp(MainActivity.this, 16), Ui.dp(MainActivity.this, 14), Ui.dp(MainActivity.this, 16), Ui.dp(MainActivity.this, 14));
                card.setBackgroundColor(android.graphics.Color.WHITE);
                Book book = getItem(position);
                if (book == null) return card;
                TextView name = Ui.text(MainActivity.this, "", 17, Ui.INK);
                name.setTypeface(null, android.graphics.Typeface.BOLD);
                TextView detail = Ui.text(MainActivity.this, "", 13, Ui.MUTED);
                TextView status = Ui.text(MainActivity.this, "", 12, Ui.GREEN);
                name.setText(book.title);
                detail.setText(book.author + "  ·  " + book.genre);
                status.setText(book.status + "     " + stars(book.rating));
                card.addView(name);
                LinearLayout.LayoutParams dlp = Ui.lp(MainActivity.this, -1, -2); dlp.topMargin = Ui.dp(MainActivity.this, 5); card.addView(detail, dlp);
                LinearLayout.LayoutParams slp = Ui.lp(MainActivity.this, -1, -2); slp.topMargin = Ui.dp(MainActivity.this, 8); card.addView(status, slp);
                return card;
            }
        };
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            android.content.Intent intent = new android.content.Intent(this, BookDetailActivity.class);
            intent.putExtra("book_index", position);
            startActivity(intent);
        });
        root.addView(list, new LinearLayout.LayoutParams(-1, 0, 1));
        android.widget.Button add = Ui.button(this, "+   Thêm sách mới", true);
        LinearLayout.LayoutParams addLp = Ui.lp(this, -1, 54);
        addLp.topMargin = Ui.dp(this, 14);
        root.addView(add, addLp);
        add.setOnClickListener(v -> Ui.open(this, AddBookActivity.class));
        setContentView(root);
    }

    private String stars(float rating) {
        StringBuilder out = new StringBuilder();
        for (int i = 1; i <= 5; i++) out.append(i <= rating ? "★" : "☆");
        return out.toString();
    }

    private void refresh() {
        if (stats == null) return;
        int read = 0;
        for (Book b : BookStore.BOOKS) if (b.status.equals("Đã đọc")) read++;
        stats.setText(BookStore.BOOKS.size() + " cuốn sách     ·     " + read + " đã đọc     ·     " + (BookStore.BOOKS.size() - read) + " chưa hoàn thành");
        adapter.notifyDataSetChanged();
        list.invalidateViews();
    }
}
