package io.perezalcolea.hollow

import com.netflix.hollow.api.consumer.HollowConsumer
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever
import com.netflix.hollow.api.custom.HollowAPI
import com.netflix.hollow.core.read.engine.HollowReadStateEngine
import io.perezalcolea.hollow.enhanced.api.Author
import io.perezalcolea.hollow.enhanced.api.AuthorPrimaryKeyIndex
import io.perezalcolea.hollow.enhanced.api.Book
import io.perezalcolea.hollow.enhanced.api.BookPrimaryKeyIndex
import io.perezalcolea.hollow.enhanced.api.BooksAPI
import io.perezalcolea.hollow.enhanced.api.BooksAPIHashIndex
import ratpack.service.Service
import ratpack.service.StartEvent
import rx.Observable

import java.util.concurrent.CompletableFuture

class HollowConsumerService implements Service {

    public static final String SCRATCH_DIR = System.getProperty("java.io.tmpdir")

    private final File publishDir = new File(SCRATCH_DIR, "enhanced-publish-dir")

    private BookPrimaryKeyIndex bookPrimaryKeyIndex
    private AuthorPrimaryKeyIndex authorPrimaryKeyIndex
    private BooksAPIHashIndex publisherHashIndex

    @Override
    void onStart(StartEvent event) throws Exception {
        initHollowConsumer()
    }

    Observable<Book> findBook(long bookId) {
        Observable.just(bookPrimaryKeyIndex.findMatch(bookId))
    }

    Observable<Author> findAuthor(long authorId) {
        Observable.just(authorPrimaryKeyIndex.findMatch(authorId))
    }

    Observable<Iterable<Book>> findBooksByPublisher(String publisher) {
        Observable.just(publisherHashIndex.findBookMatches(publisher))
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
    }
}
