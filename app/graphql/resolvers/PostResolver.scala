package graphql.resolvers

import com.google.inject.Inject
import models.Post
import repositories.PostRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * A resolver that does actions on the Post entity.
  *
  * @param postRepository   a repository that provides basic operations for the Post entity
  * @param executionContext a thread pool to asynchronously execute operations
  */
class PostResolver @Inject()(val postRepository: PostRepository,
                             implicit val executionContext: ExecutionContext) {

  /**
    * Finds all posts.
    *
    * @return a list of a posts
    */
  def posts: Future[List[Post]] = postRepository.findAll()

  /**
    * Adds a post.
    *
    * @param title   a title of the post
    * @param content a content of the post
    * @return added post
    */
  def addPost(title: String, content: String, authorId: Long): Future[Post] =
    postRepository.create(Post(title = title, content = content, authorId = authorId))

  /**
    * Finds a post by id.
    *
    * @param id an id of the post
    * @return found post
    */
  def findPost(id: Long): Future[Option[Post]] = postRepository.find(id)

  /**
    * Updates a post.
    *
    * @param id      an id of the post
    * @param title   a title of the post
    * @param content a content of the post
    * @return updated post
    */
  def updatePost(id: Long, title: String, content: String, authorId: Long): Future[Post] =
    postRepository.update(Post(Some(id), title, content, authorId))

  /**
    * Deletes a post by id.
    *
    * @param id an id of the post
    * @return true if the post was deleted, else otherwise
    */
  def deletePost(id: Long): Future[Boolean] = postRepository.delete(id)
}