package io.perezalcolea.hollow.enhanced;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;
import com.netflix.hollow.core.read.engine.HollowTypeReadState;
import io.perezalcolea.hollow.HollowConsumerBuilder;
import io.perezalcolea.hollow.enhanced.api.BooksAPI;

public class ConsumerDemo {

    public static void main(String[] args) {
        HollowConsumer hollowConsumer = HollowConsumerBuilder.build("enhanced-publish-dir", BooksAPI.class);
        hollowConsumer.triggerRefresh();

        BooksAPI booksAPI = (BooksAPI) hollowConsumer.getAPI();
        booksAPI.getAllBook();

        for(io.perezalcolea.hollow.enhanced.api.Book book : booksAPI.getAllBook()) {
            System.out.println(book.getBookId() + ", " +
                    book.getTitle() + ", " +
                    book.getPublisher().getValue() + ", " +
                    book.getLanguage().getValue());
        }


        HollowReadStateEngine stateEngine = hollowConsumer.getStateEngine();

        long totalApproximateHeapFootprint = 0;

        for(HollowTypeReadState typeState : stateEngine.getTypeStates()) {
            String typeName = typeState.getSchema().getName();
            long heapCost = typeState.getApproximateHeapFootprintInBytes();
            System.out.println(typeName + ": " + heapCost);
            totalApproximateHeapFootprint += heapCost;
        }

        System.out.println("TOTAL: " + totalApproximateHeapFootprint);
    }
}
