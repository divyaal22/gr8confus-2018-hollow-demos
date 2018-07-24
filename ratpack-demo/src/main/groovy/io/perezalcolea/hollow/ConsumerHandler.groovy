package io.perezalcolea.hollow

import com.google.inject.Inject
import com.netflix.hollow.api.objects.HollowRecord
import com.netflix.hollow.tools.stringifier.HollowRecordJsonStringifier
import io.perezalcolea.hollow.enhanced.api.Book
import ratpack.groovy.handling.GroovyChainAction
import ratpack.rx.RxRatpack

class ConsumerHandler extends GroovyChainAction {

    HollowRecordJsonStringifier hollowRecordJsonStringifier = new HollowRecordJsonStringifier(false, true)

    @Inject
    HollowConsumerService hollowConsumerService

    @Override
    void execute() throws Exception {
        path('books/:bookId') {
            byMethod {
                get {
                    Long bookId = context?.pathTokens?.bookId?.toLong()

                    RxRatpack.promiseSingle(hollowConsumerService.findBook(bookId)).then { book ->
                        if(!book) {
                            context.clientError(404)
                            return
                        }

                        context.response.contentType('application/json;charset=UTF-8')
                        context.render(hollowRecordJsonStringifier.stringify(book))
                    }
                }
            }
        }

        path('authors/:authorId') {
            byMethod {
                get {
                    Long authorId = context?.pathTokens?.authorId?.toLong()

                    RxRatpack.promiseSingle(hollowConsumerService.findAuthor(authorId)).then { author ->
                        if(!author) {
                            context.clientError(404)
                            return
                        }

                        context.response.contentType('application/json;charset=UTF-8')
                        context.render(hollowRecordJsonStringifier.stringify(author))
                    }
                }
            }
        }

        path('books/publisher/:publisher') {
            byMethod {
                get {
                    String publisher = context?.pathTokens?.publisher

                    RxRatpack.promiseSingle(hollowConsumerService.findBooksByPublisher(publisher)).then { Iterable<Book>  books ->
                        if(!books) {
                            context.clientError(404)
                            return
                        }

                        context.response.contentType('application/json;charset=UTF-8')
                        StringWriter out = new StringWriter()
                        out.write("[")
                        books.eachWithIndex { value, index ->
                            hollowRecordJsonStringifier.stringify(out, value)
                            if (books.iterator().hasNext()) {
                                out.write(",")
                            }
                        }
                        out.write("]")
                        context.render(out.toString())
                    }
                }
            }
        }
    }
}
