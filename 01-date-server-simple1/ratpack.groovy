@Grab("org.slf4j:slf4j-simple:1.7.10")
@Grab("io.ratpack:ratpack-groovy:1.1.1")
import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    get {
      response.send "Time in London is " + new Date().toString()
    }
  }
}


