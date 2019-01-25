package repositories

import models.Post

import scala.collection.mutable
import scala.concurrent.Future

//TODO: update docs
/**
  * A repository trait that determines basic CRUD operations.
  *
  */
trait PostRepository {

  /**
    * Creates a Post.
    *
    * @param post a new post
    * @return created post
    */
  def create(post: Post): Future[Post]

  /**
    * Returns a post by id.
    *
    * @param id an id of the post
    * @return found post
    */
  def find(id: Long): Future[Option[Post]]

  def findByAuthorId(authorId: Long): Future[mutable.ArrayBuffer[Post]]

  /**
    * Returns a list of posts.
    *
    * @return list of posts
    */
  def findAll(): Future[List[Post]]

  /**
    * Updates an existing post.
    *
    * @param post new post
    * @return updated post
    */
  def update(post: Post): Future[Post]

  /**
    * Delete an existing post by id.
    *
    * @param id an id of some post
    * @return true/false result of deleting
    */
  def delete(id: Long): Future[Boolean]
}
