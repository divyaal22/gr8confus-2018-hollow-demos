package io.perezalcolea.hollow.enhanced;

import com.netflix.hollow.core.write.objectmapper.HollowInline;
import com.netflix.hollow.core.write.objectmapper.HollowPrimaryKey;
import com.netflix.hollow.core.write.objectmapper.HollowTypeName;

import java.util.Set;

@HollowPrimaryKey(fields={"bookId"})
public class Book {
    long bookId;
    @HollowInline
    String title;
    @HollowInline
    String isbn;
    @HollowTypeName(name="Publisher")
    String publisher;
    @HollowTypeName(name="Language")
    String language;
    Set<Author> authors;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getBookId() {
        return bookId;
    }
}
