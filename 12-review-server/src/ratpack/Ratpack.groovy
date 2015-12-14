import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  bindings {
  }
  handlers {
    get('score/:itemId') {
      println "1"
      render json(itemId: pathTokens['itemID'], score: new Random().nextDouble() + 4)
    }
    post('review/:itemId') {
      render json(message: 'OK')
    }
    get {
      response.send "Review API"
    }
  }
}
