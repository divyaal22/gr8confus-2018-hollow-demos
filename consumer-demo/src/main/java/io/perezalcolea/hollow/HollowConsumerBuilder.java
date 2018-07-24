package io.perezalcolea.hollow;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever;

import java.io.File;

public class HollowConsumerBuilder {

    public static final String SCRATCH_DIR = System.getProperty("java.io.tmpdir");

    public static HollowConsumer build(String dirName, Class apiClass) {
        File publishDir = new File(SCRATCH_DIR, dirName);
        return build(publishDir, apiClass);
    }

    public static HollowConsumer build(File publishDir, Class apiClass) {
        System.out.println("I AM THE CONSUMER.  I WILL READ FROM " + publishDir.getAbsolutePath());

        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir);
        HollowConsumer.AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(publishDir);

        return HollowConsumer.withBlobRetriever(blobRetriever)
                .withAnnouncementWatcher(announcementWatcher)
                .withGeneratedAPIClass(apiClass)
                .build();
    }
}
