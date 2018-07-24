package io.perezalcolea.hollow.enhanced;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.range.LongRangeRandomizer;
import io.perezalcolea.hollow.Producer;
import io.perezalcolea.hollow.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;

public class ProducerDemo {

    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .stringLengthRange(10, 20)
            .randomize(Long.class, new LongRangeRandomizer(1L, 10000000000L))
            .exclude(field().named("publisher").ofType(String.class).inClass(Book.class).get())
            .exclude(field().named("language").ofType(String.class).inClass(Book.class).get())
            .build();

    public static void main(String args[]) {
        try {
            Producer producer = new Producer("enhanced-publish-dir", Book.class);
            producer.restore();
            for (int i = 0; i < 10; i++) {
                List<Book> books = randomBooks();
                producer.addRecords(books);
                producer.runCycle();
            }
        } catch (Exception e) {
            System.out.println("Could not execute producer : " + e);
        }
    }

    private static List<Book> randomBooks() {
        List<Book> books = new ArrayList<>();
        System.out.println("CREATING 10000 RECORDS");

        for (int i = 0; i < 10000; i++) {
            Book book = enhancedRandom.nextObject(Book.class);
            book.setLanguage(RandomDataGenerator.randomLanguage());
            book.setPublisher(RandomDataGenerator.randomPublisher());
            books.add(book);
        }
        return books;
    }
}
