package repositories

import com.google.inject.Inject
import models.User

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class UserRepositoryImpl @Inject()(implicit val executionContext: ExecutionContext) extends UserRepository {

  val userCollection: mutable.ArrayBuffer[User] = mutable.ArrayBuffer(
    User(id = Some(1), name = "Konstantin")
  )

  /** @inheritdoc*/
  override def find(id: Long): Future[Option[User]] = Future.successful {
    userCollection.find(_.id.contains(id))
  }
}