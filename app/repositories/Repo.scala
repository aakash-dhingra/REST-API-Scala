package repositories

import scala.concurrent.ExecutionContext
import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
//import play.modules.reactivemongo.json._
//import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.{ReadPreference,Cursor}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection


import scala.concurrent.{ExecutionContext, Future}

trait Repo {
  def find()(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(selector: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
}

//class WidgetRepoImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends Repo {
//
//  def collection = reactiveMongoApi.db.collection[JSONCollection]("widgets");
//
//  override def find()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
//    val genericQueryBuilder = collection.find(Json.obj());
//    val cursor = genericQueryBuilder.cursor[JsObject](ReadPreference.Primary);
//    cursor.collect[List]()
//  }
//
//  override def select(selector: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
//    collection.find(selector).one[JsObject]
//  }
//
//  override def update(selector: BSONDocument, update: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
//    collection.update(selector, update)
//  }
//
//  override def remove(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
//    collection.remove(document)
//  }
//
//  override def save(document: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
//    collection.update(BSONDocument("_id" -> document.get("_id").getOrElse(BSONObjectID.generate)), document, upsert = true)
//  }
//
//}
