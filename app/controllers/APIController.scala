package controllers

import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.MongoController
import play.api._
import play.api.mvc._
import play.modules.reactivemongo.MongoController
import javax.inject.{Inject, Singleton}

@Singleton
class APIController @Inject()(cc: ControllerComponents)  extends AbstractController(cc) {
  def HRLogin = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.HRLogin())
  }
  def ValidateLoginPost =Action { implicit request=>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args=>
      val username = args("username").head
      val password = args("password").head
      if(models.TaskList.validateUser(username,password)) {
        Redirect(routes.APIController.Projectlist()).withSession("username" -> username)
      }else {
        Redirect(routes.APIController.HRLogin())
      }
    }.getOrElse(Redirect(routes.APIController.HRLogin()))
  }

  def createUser = Action { implicit request=>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args=>
      val username = args("username").head
      val password = args("password").head
      if(models.TaskList.createUser(username,password)) {
        Redirect(routes.APIController.Projectlist()).withSession("username" -> username)
      }else {
        Redirect(routes.APIController.HRLogin())
      }
    }.getOrElse(Redirect(routes.APIController.HRLogin()))
  }

  def Projectlist = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val Projects = models.TaskList.getProject(username)
      Ok(views.html.ProjectList(username,Projects))
    }.getOrElse(Redirect(routes.APIController.HRLogin()))
  }

  def logout = Action {
    Redirect(routes.APIController.HRLogin()).withNewSession
  }

  def addProject = Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption.map { username =>
        val postVals = request.body.asFormUrlEncoded
        postVals.map { args=>
          val project= args("newProject").head
          models.TaskList.addProject(username, project);
          Redirect(routes.APIController.Projectlist())
      }.getOrElse(Redirect(routes.APIController.Projectlist()))
    }.getOrElse(Redirect(routes.APIController.HRLogin()))
  }

  def deleteProject = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args=>
        val index= args("index").head.toInt
        models.TaskList.removeProject(username, index);
        Redirect(routes.APIController.Projectlist())
      }.getOrElse(Redirect(routes.APIController.Projectlist()))
    }.getOrElse(Redirect(routes.APIController.HRLogin()))
  }
}
