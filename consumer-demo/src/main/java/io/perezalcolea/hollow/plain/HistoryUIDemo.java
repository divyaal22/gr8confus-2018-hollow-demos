package io.perezalcolea.hollow.plain;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.explorer.ui.jetty.HollowExplorerUIServer;
import com.netflix.hollow.history.ui.HollowHistoryUI;
import com.netflix.hollow.history.ui.jetty.HollowHistoryUIServer;
import io.perezalcolea.hollow.HollowConsumerBuilder;
import io.perezalcolea.hollow.enhanced.Book;
import io.perezalcolea.hollow.enhanced.api.BooksAPI;

public class HistoryUIDemo {

    public static void main(String[] args) {
        try {
            HollowConsumer hollowConsumer = HollowConsumerBuilder.build("plain-publish-dir", BooksAPI.class);
            hollowConsumer.triggerRefresh();

            HollowHistoryUIServer server = new HollowHistoryUIServer(hollowConsumer, 8090);
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Could not execute History UI : " + e);
        }
    }
}
