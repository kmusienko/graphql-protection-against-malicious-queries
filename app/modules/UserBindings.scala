package modules

import com.google.inject.{AbstractModule, Scopes}
import repositories.{UserRepository, UserRepositoryImpl}

/**
  * The Guice module with bindings related to the User entity.
  */
class UserBindings extends AbstractModule {

  /**
    * A method where bindings should be defined.
    */
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl]).in(Scopes.SINGLETON)
  }
}