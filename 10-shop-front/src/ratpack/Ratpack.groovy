import com.ggx.services.BookService
import groovy.json.JsonSlurper
import ratpack.exec.Blocking
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.http.client.HttpClient

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
    module(MarkupTemplateModule) { configuration ->
      configuration.autoNewLine = true
      configuration.useDoubleQuotes = true
      configuration.autoIndent = true
      configuration.expandEmptyElements = true
    }
    bind BookService
  }
  handlers { BookService booksService ->
    get { HttpClient client ->
      Blocking.get {
        booksService.catalog
      }.next { books ->
        books.each { book ->
          client.get(new URI("http://localhost:8902/score/${book.id}".toString())) {
          }.then { receivedResponse ->
            def bodyText = receivedResponse.body.text
            def score = new JsonSlurper().parseText(bodyText).'score' as Double
            book.score = Math.round(score * 100) / 100
          }
        }
      }.then { books ->
        render groovyMarkupTemplate("index.gtpl", title: "Groovy Book Shop", books: books)
      }
    }
    get('order/:bookId') { HttpClient client ->
      client.post(new URI("http://localhost:8901/order/${pathTokens['bookId']}")) { spec ->
        spec.body { b -> b.text "order data" }
      }.onError { Throwable t ->
        render json(message: 'FAIL', exception: t)
      }.then {
        render json(message: 'OK')
      }
    }
    files { dir "public" }
    files { dir "content" }
  }
}
