package graphql

import com.google.inject.{Inject, Singleton}
import graphql.schema.PostSchema
import models.errors.TooComplexQueryError
import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}
import sangria.schema.{ObjectType, fields}

/**
  * Defines the global GraphQL-related objects of the application.
  */
@Singleton
class GraphQL @Inject()(val postSchema: PostSchema) {

  /**
    * The constant that signifies the maximum allowed depth of query.
    */
  val maxQueryDepth = 15

  /**
    * The constant that signifies the maximum allowed complexity of query.
    */
  val maxQueryComplexity = 1000

  /**
    * The GraphQL schema of the application.
    */
  val Schema = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        postSchema.Queries: _*
      )
    ),
    mutation = Some(
      ObjectType("Mutation",
        fields(
          postSchema.Mutations: _*
        )
      )
    )
  )

  /**
    * The exception handler that defines custom error handling mechanism.
    */
  val exceptionHandler = ExceptionHandler(
    onException = {
      case (_, error: TooComplexQueryError) => HandledException(error.getMessage)
      case (_, error: MaxQueryDepthReachedError) => HandledException(error.getMessage)
    }
  )
}