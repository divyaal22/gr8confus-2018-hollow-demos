package io.perezalcolea.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.netflix.hollow.api.custom.HollowAPI
import com.netflix.hollow.core.read.engine.HollowReadStateEngine
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Context
import io.perezalcolea.hollow.enhanced.api.*

import javax.annotation.PostConstruct
import javax.inject.Singleton
import java.util.concurrent.CompletableFuture

@Slf4j
@Singleton
@Context
@CompileStatic
class HollowConsumerService  {

    public static final String SCRATCH_DIR = System.getProperty("java.io.tmpdir")

    private final File publishDir = new File(SCRATCH_DIR, "enhanced-publish-dir")

    private BookPrimaryKeyIndex bookPrimaryKeyIndex
    private AuthorPrimaryKeyIndex authorPrimaryKeyIndex
    private BooksAPIHashIndex publisherHashIndex

    @PostConstruct
    void init() {
        initHollowConsumer()
    }

    Book findBook(long bookId) {
        bookPrimaryKeyIndex.findMatch(bookId)
    }

    Author findAuthor(long authorId) {
        authorPrimaryKeyIndex.findMatch(authorId)
    }

    Iterable<Book> findBooksByPublisher(String publisher) {
        publisherHashIndex.findBookMatches(publisher)
    }

    private initHollowConsumer() {
        CompletableFuture firstRefresh = new CompletableFuture<>()
        HollowConsumer.RefreshListener refreshListener = new HollowConsumer.RefreshListener() {
            void refreshSuccessful(long beforeVersion, long afterVersion, long requestedVersion) {
                if(beforeVersion == Long.MIN_VALUE)
                    firstRefresh.complete(afterVersion)
            }

            @Override void refreshStarted(long currentVersion, long requestedVersion) { }
            @Override void snapshotUpdateOccurred(HollowAPI api, HollowReadStateEngine stateEngine, long version) throws Exception { }
            @Override void deltaUpdateOccurred(HollowAPI api, HollowReadStateEngine stateEngine, long version) throws Exception { }
            @Override void blobLoaded(HollowConsumer.Blob transition) { }
            @Override void refreshFailed(long beforeVersion, long afterVersion, long requestedVersion, Throwable failureCause) { }
        }

        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir)
        HollowConsumer.AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(publishDir)

        HollowConsumer consumer = HollowConsumer.withBlobRetriever(blobRetriever)
                .withAnnouncementWatcher(announcementWatcher)
                .withRefreshListener(refreshListener)
                .withGeneratedAPIClass(BooksAPI)
                .build()

        consumer.triggerAsyncRefresh()

        firstRefresh.get()

        bookPrimaryKeyIndex = new BookPrimaryKeyIndex(consumer)
        authorPrimaryKeyIndex = new AuthorPrimaryKeyIndex(consumer, "id")
        publisherHashIndex = new BooksAPIHashIndex(consumer, "Book", "", "publisher.value")
        println("Finished creating indexes")
    }
}
