package modules

import com.google.inject.{AbstractModule, Provides, Scopes}
import models.Post
import repositories.{PostRepository, Repository}

import scala.concurrent.ExecutionContext

/**
  * The Guice module with bindings related to the Post entity.
  */
class PostBinding extends AbstractModule {

  /**
    * A method where bindings should be defined.
    */
  override def configure(): Unit = {}

  /**
    * Provides an implementation of the Repository[Post] trait.
    *
    * @param executionContext a thread pool to asynchronously execute operations
    * @return an instance of the PostRepository class that implements Repository[Post] trait
    */
  @Provides
  def providePostRepository(implicit executionContext: ExecutionContext): Repository[Post] = new PostRepository()
}