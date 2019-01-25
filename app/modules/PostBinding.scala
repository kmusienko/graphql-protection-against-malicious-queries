package modules

import com.google.inject._
import repositories.{PostRepository, PostRepositoryImpl, UserRepository, UserRepositoryImpl}


/**
  * The Guice module with bindings related to the Post entity.
  */
class PostBinding extends AbstractModule {

  /**
    * A method where bindings should be defined.
    */
  override def configure(): Unit = {
    bind(classOf[PostRepository]).to(classOf[PostRepositoryImpl]).in(Scopes.SINGLETON)
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl]).in(Scopes.SINGLETON)
  }
}