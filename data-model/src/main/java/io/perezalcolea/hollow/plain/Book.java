package io.perezalcolea.hollow.plain;

import com.netflix.hollow.core.write.objectmapper.HollowPrimaryKey;

import java.util.Set;

@HollowPrimaryKey(fields={"bookId"})
public class Book {
    long bookId;
    String title;
    String isbn;
    String publisher;
    String language;
    Set<Author> authors;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
