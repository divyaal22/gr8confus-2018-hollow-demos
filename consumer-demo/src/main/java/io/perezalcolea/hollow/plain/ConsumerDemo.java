package io.perezalcolea.hollow.plain;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;
import com.netflix.hollow.core.read.engine.HollowTypeReadState;
import io.perezalcolea.hollow.HollowConsumerBuilder;
import io.perezalcolea.hollow.plain.api.BooksAPI;

public class ConsumerDemo {

    public static void main(String[] args) {
        HollowConsumer hollowConsumer = HollowConsumerBuilder.build("plain-publish-dir", BooksAPI.class);
        hollowConsumer.triggerRefresh();

        BooksAPI booksAPI = (BooksAPI) hollowConsumer.getAPI();

        for(io.perezalcolea.hollow.plain.api.Book book : booksAPI.getAllBook()) {
            System.out.println(book.getBookId() + ", " +
                    book.getTitle().getValue() + ", " +
                    book.getPublisher().getValue() + ", " +
                    book.getLanguage().getValue());
        }

        HollowReadStateEngine stateEngine = hollowConsumer.getStateEngine();/// a populated state engine

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
