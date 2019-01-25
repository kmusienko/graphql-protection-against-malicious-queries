package repositories

import models.User

import scala.concurrent.Future

trait UserRepository {

  def find(id: Long): Future[Option[User]]
}
