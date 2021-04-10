package models
import com.sun.xml.internal.bind.v2.TODO

import collection.mutable

object TaskList {
  private val users=mutable.Map[String,String]("aakash"->"pass")
  private val projects = mutable.Map[String, List[String]]("aakash"->List())
  def validateUser(username: String,password: String): Boolean= {
    users.get(username).map(_ == password).getOrElse(false)
  }
  def createUser(username: String, password: String): Boolean= {
    if(users.contains(username)) false else {
      users(username) = password
      true
    }
  }
  def getProject(username :String): Seq[String]= {
    projects.get(username).getOrElse(Nil)
  }

  def addProject(username: String, project: String) ={
    projects(username) = project :: projects.get(username).getOrElse(Nil)
  }

  def removeProject(username: String, index: Int): Boolean = {
    if(index<0 || projects.get(username).isEmpty || index >= projects(username).length) false
    else {
      projects(username) = projects(username).patch(index, Nil, 1)
      true
    }
  }

}
