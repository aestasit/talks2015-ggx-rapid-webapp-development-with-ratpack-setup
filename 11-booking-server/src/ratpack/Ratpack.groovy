import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

ratpack {
  handlers {
    all {
      println request.uri
      next()
    }
    post('order/:itemId') {
      render json(message: 'OK')
    }
    get {
      response.send "Booking API"
    }
  }
}
