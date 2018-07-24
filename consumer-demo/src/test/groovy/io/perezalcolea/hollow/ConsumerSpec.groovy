package io.perezalcolea.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.netflix.hollow.api.producer.HollowIncrementalProducer
import com.netflix.hollow.api.producer.HollowProducer
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.perezalcolea.hollow.enhanced.Book
import io.perezalcolea.hollow.enhanced.api.BookPrimaryKeyIndex
import io.perezalcolea.hollow.enhanced.api.BooksAPI
import spock.lang.Specification

import static io.github.benas.randombeans.FieldDefinitionBuilder.field

class ConsumerSpec extends Specification {

    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .stringLengthRange(10, 20)
            .exclude(field().named("publisher").ofType(String).inClass(Book).get())
            .exclude(field().named("language").ofType(String).inClass(Book).get())
            .build()

    def 'produces snapshot'() {
        setup:
        String dirName = "test-enhanced-publish-dir"
        File publishDir = new File(HollowConsumerBuilder.SCRATCH_DIR, dirName)
        HollowIncrementalProducer incrementalProducer = createIncrementalProducer(publishDir)
        incrementalProducer.restoreFromLastState()
        List<Book> books = randomBooks()

        when: "adding records"
        incrementalProducer.addOrModifyInParallel(books)

        and: "running a cycle"
        long version = incrementalProducer.runCycle()

        then: "a cycle was processed properly"
        version != Long.MIN_VALUE

        when: "creating a consumer and triggering a refresh"
        HollowConsumer hollowConsumer = HollowConsumerBuilder.build(publishDir, BooksAPI)
        hollowConsumer.triggerRefresh()

        and: "creating a primary key index"
        BookPrimaryKeyIndex bookPrimaryKeyIndex = new BookPrimaryKeyIndex(hollowConsumer)

        then: "the records are in the hollow consumer"
        bookPrimaryKeyIndex.findMatch(books.first().bookId)

        cleanup:
        publishDir.deleteDir()
    }

    private HollowIncrementalProducer createIncrementalProducer(File publishDir) {
        HollowProducer.Publisher publisher = new HollowFilesystemPublisher(publishDir)
        HollowProducer.Announcer announcer = new HollowFilesystemAnnouncer(publishDir)

        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir)
        HollowConsumer.AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(publishDir)

        HollowProducer hollowProducer = HollowProducer.withPublisher(publisher)
                .withAnnouncer(announcer)
                .build()

        return HollowIncrementalProducer.withProducer(hollowProducer)
                .withAnnouncementWatcher(announcementWatcher)
                .withDataModel(Book)
                .withBlobRetriever(blobRetriever)
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
