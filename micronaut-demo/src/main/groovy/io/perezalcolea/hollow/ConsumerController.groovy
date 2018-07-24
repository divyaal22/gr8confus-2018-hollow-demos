package io.perezalcolea.hollow

import com.netflix.hollow.tools.stringifier.HollowRecordJsonStringifier
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType
import io.perezalcolea.hollow.enhanced.api.Author
import io.perezalcolea.hollow.enhanced.api.Book

import javax.inject.Inject

import static io.micronaut.http.HttpResponse.*

@CompileStatic
@Controller("/")
class ConsumerController {

    HollowRecordJsonStringifier hollowRecordJsonStringifier = new HollowRecordJsonStringifier(false, true)

    @Inject
    HollowConsumerService hollowConsumerService

    @Get("/books/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<String> findBook(Long bookId) {
        Book book = hollowConsumerService.findBook(bookId)
        if(!book) {
            return notFound()
        }

        return ok(hollowRecordJsonStringifier.stringify(book))
    }

    @Get("/authors/{authorId}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<String> findAuthor(Long authorId) {
        Author author = hollowConsumerService.findAuthor(authorId)
        if(!author) {
            return notFound()
        }

        return ok(hollowRecordJsonStringifier.stringify(author))
    }

    @Get("/books/publisher/{publisher}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<String> findBooksByPublisher(String publisher) {
        Iterable<Book> books = hollowConsumerService.findBooksByPublisher(publisher)
        if(!books) {
            return notFound()
        }

        StringWriter out = new StringWriter()
        out.write("[")
        books.eachWithIndex { value, index ->
            hollowRecordJsonStringifier.stringify(out, value)
            if (books.iterator().hasNext()) {
                out.write(",")
            }
        }
        out.write("]")
        return ok(out.toString())
    }
}