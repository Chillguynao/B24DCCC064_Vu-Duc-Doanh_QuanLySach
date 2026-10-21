package com.example.booklibrary;

import java.util.ArrayList;

public final class BookStore {
    public static final ArrayList<Book> BOOKS = new ArrayList<>();
    private static boolean initialized;

    private BookStore() {}

    public static void initialize() {
        if (initialized) return;
        initialized = true;
        BOOKS.add(new Book("Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4, "Ghi chú: đọc mỗi ngày một chương."));
        BOOKS.add(new Book("Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5, ""));
        BOOKS.add(new Book("Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0, ""));
    }
}
