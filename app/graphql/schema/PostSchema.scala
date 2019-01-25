package graphql.schema

import com.google.inject.Inject
import graphql.resolvers.PostResolver
import models.errors.NotFound
import models.{Post, User}
import repositories.{PostRepository, PostRepositoryImpl, UserRepository, UserRepositoryImpl}
import sangria.macros.derive.{AddFields, ObjectTypeName, ReplaceField, deriveObjectType}
import sangria.schema._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Defines GraphQL schema for the Post entity.
  */
class PostSchema @Inject()(postResolver: PostResolver,
                           userRepository: UserRepository,
                           postRepository: PostRepository,
                           implicit val executionContext: ExecutionContext) {

  /**
    * Sangria's representation of the Post type.
    * It's necessary to convert Post object into Sangria's GraphQL object to represent it in the GraphQL format.
    */
  implicit val PostType: ObjectType[Unit, Post] = deriveObjectType[Unit, Post](ObjectTypeName("Post"),
    ReplaceField(
      fieldName = "authorId",
      field =
        Field(
          name = "author",
          fieldType = AuthorType,
          resolve = ctx => {
            val authorId = ctx.value.authorId
            userRepository.find(authorId).flatMap {
              case Some(author) => Future.successful(author)
              case _ => Future.failed(NotFound(s"User with id = $authorId"))
            }
          }
        )
    )
  )

  implicit val AuthorType: ObjectType[Unit, User] = deriveObjectType[Unit, User](ObjectTypeName("Author"),
    AddFields(
      Field(
        name = "posts",
        fieldType = ListType(PostType),
        resolve = ctx => {
          //TODO: get rid of 'get' on Option
          val authorId = ctx.value.id.get
          postRepository.findByAuthorId(authorId)
        }
      )
    )
  )

  /**
    * List of GraphQL queries defined for the Post type.
    */
  val Queries: List[Field[Unit, Unit]] = List(
    Field(
      name = "posts",
      fieldType = ListType(PostType),
      resolve = _ => postResolver.posts
    ),
    Field(
      name = "findPost",
      fieldType = OptionType(PostType),
      arguments = List(
        Argument("id", LongType)
      ),
      resolve =
        sangriaContext =>
          postResolver.findPost(sangriaContext.args.arg[Long]("id"))
    )
  )

  /**
    * List of GraphQL mutations defined for the Post type.
    */
  val Mutations: List[Field[Unit, Unit]] = List(
    Field(
      name = "addPost",
      fieldType = PostType,
      arguments = List(
        Argument("title", StringType),
        Argument("content", StringType),
        Argument("authorId", LongType)
      ),
      resolve = sangriaContext =>
        postResolver.addPost(
          sangriaContext.args.arg[String]("title"),
          sangriaContext.args.arg[String]("content"),
          sangriaContext.args.arg[Long]("authorId")
        )
    ),
    Field(
      name = "updatePost",
      fieldType = PostType,
      arguments = List(
        Argument("id", LongType),
        Argument("title", StringType),
        Argument("content", StringType),
        Argument("authorId", LongType)
      ),
      resolve = sangriaContext =>
        postResolver.updatePost(
          sangriaContext.args.arg[Long]("id"),
          sangriaContext.args.arg[String]("title"),
          sangriaContext.args.arg[String]("content"),
          sangriaContext.args.arg[Long]("authorId")
        )
    ),
    Field(
      name = "deletePost",
      fieldType = BooleanType,
      arguments = List(
        Argument("id", LongType)
      ),
      resolve =
        sangriaContext =>
          postResolver.deletePost(sangriaContext.args.arg[Long]("id"))
    )
  )
}