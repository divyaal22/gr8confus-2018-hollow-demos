package io.perezalcolea.hollow;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever;
import com.netflix.hollow.api.producer.HollowIncrementalProducer;
import com.netflix.hollow.api.producer.HollowProducer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemAnnouncer;
import com.netflix.hollow.api.producer.fs.HollowFilesystemPublisher;

import java.io.File;
import java.util.List;

public class Producer {

    public static final String SCRATCH_DIR = System.getProperty("java.io.tmpdir");

    private final Class<?> dataModel;
    private final HollowIncrementalProducer hollowIncrementalProducer;

    public Producer(String dirName, Class<?> dataModel) {
        this.dataModel = dataModel;
        this.hollowIncrementalProducer = buildHollowProducer(new File(SCRATCH_DIR, dirName));
    }

    private HollowIncrementalProducer buildHollowProducer(File publishDir) {
        System.out.println("I AM THE PRODUCER.  I WILL PUBLISH TO " + publishDir.getAbsolutePath());

        HollowProducer.Publisher publisher = new HollowFilesystemPublisher(publishDir);
        HollowProducer.Announcer announcer = new HollowFilesystemAnnouncer(publishDir);

        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir);
        HollowConsumer.AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(publishDir);

        HollowProducer hollowProducer = HollowProducer.withPublisher(publisher)
                .withAnnouncer(announcer)
                .build();

        return HollowIncrementalProducer.withProducer(hollowProducer)
                .withDataModel(dataModel)
                .withBlobRetriever(blobRetriever)
                .withAnnouncementWatcher(announcementWatcher)
                .build();
    }

    public void restore() {
        hollowIncrementalProducer.restoreFromLastState();
    }

    public void addRecords(List records) {
        hollowIncrementalProducer.addOrModifyInParallel(records);
    }

    public void removeRecords(List records) {
        hollowIncrementalProducer.deleteInParallel(records);
    }

    public long runCycle() {
        return hollowIncrementalProducer.runCycle();
    }
}
