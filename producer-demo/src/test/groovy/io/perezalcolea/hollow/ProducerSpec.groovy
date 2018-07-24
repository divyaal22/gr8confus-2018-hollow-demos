package io.perezalcolea.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.perezalcolea.hollow.enhanced.Book
import io.perezalcolea.hollow.enhanced.api.BooksAPI
import io.perezalcolea.hollow.enhanced.api.BookPrimaryKeyIndex
import spock.lang.Specification

import static io.github.benas.randombeans.FieldDefinitionBuilder.field

class ProducerSpec extends Specification {

    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .stringLengthRange(10, 20)
            .exclude(field().named("publisher").ofType(String).inClass(Book).get())
            .exclude(field().named("language").ofType(String).inClass(Book).get())
            .build()

    def 'produces snapshot'() {
        setup:
        String dirName = "test-enhanced-publish-dir"
        File publishDir = new File(Producer.SCRATCH_DIR, dirName)
        Producer producer = new Producer(dirName, Book)
        List<Book> books = randomBooks()

        when: "adding records"
        producer.addRecords(books)

        and: "running a cycle"
        long version = producer.runCycle()

        then: "a cycle was processed properly"
        version != Long.MIN_VALUE

        when: "creating a consumer and triggering a refresh"
        HollowConsumer hollowConsumer = createConsumer(publishDir)
        hollowConsumer.triggerRefreshTo(version)

        and: "creating a primary key index"
        BookPrimaryKeyIndex bookPrimaryKeyIndex = new BookPrimaryKeyIndex(hollowConsumer)

        then: "the records are in the hollow consumer"
        bookPrimaryKeyIndex.findMatch(books.first().bookId)

        cleanup:
        publishDir.deleteDir()
    }

    private HollowConsumer createConsumer(File publishDir) {
        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir)
        return HollowConsumer.withBlobRetriever(blobRetriever)
                .withGeneratedAPIClass(BooksAPI)
                .build()
    }

    private List<Book> randomBooks() {
        List<Book> books = []
        for (int i = 0; i < 10000; i++) {
            Book book = enhancedRandom.nextObject(Book)
            book.setLanguage(RandomDataGenerator.randomLanguage())
            book.setPublisher(RandomDataGenerator.randomPublisher())
            books.add(book)
        }
        return books
    }
}
