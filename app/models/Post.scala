package models
import play.api.libs.json.Json
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID

case class Post(title: String, body: String)

object JsonFormarts {
  implicit val postFormat =Json.format[Post]
}
