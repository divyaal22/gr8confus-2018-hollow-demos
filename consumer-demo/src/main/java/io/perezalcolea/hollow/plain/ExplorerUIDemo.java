package io.perezalcolea.hollow.plain;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.explorer.ui.jetty.HollowExplorerUIServer;
import io.perezalcolea.hollow.HollowConsumerBuilder;
import io.perezalcolea.hollow.enhanced.Book;
import io.perezalcolea.hollow.enhanced.api.BooksAPI;

public class ExplorerUIDemo {

    public static void main(String[] args) {
        try {
            HollowConsumer hollowConsumer = HollowConsumerBuilder.build("plain-publish-dir", BooksAPI.class);
            hollowConsumer.triggerRefresh();

            HollowExplorerUIServer server = new HollowExplorerUIServer(hollowConsumer, 8080);
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Could not execute Explorer UI : " + e);
        }
    }
}
