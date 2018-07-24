package io.perezalcolea.hollow.enhanced;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.explorer.ui.jetty.HollowExplorerUIServer;
import io.perezalcolea.hollow.HollowConsumerBuilder;
import io.perezalcolea.hollow.enhanced.api.BooksAPI;

public class ExplorerUIDemo {

    public static void main(String[] args) {
        try {
            HollowConsumer hollowConsumer = HollowConsumerBuilder.build("enhanced-publish-dir", BooksAPI.class);
            hollowConsumer.triggerRefresh();

            HollowExplorerUIServer server = new HollowExplorerUIServer(hollowConsumer, 8080);
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Could not execute Explorer UI : " + e);
        }
    }
}
