package models.slick

import models.slick.Tables.Users
import security.PasswordHashing
import scala.slick.driver.H2Driver.simple._
import DatabaseSetup.db


import scala.slick.lifted.TableQuery

case class User(id: Long, name: String, password: String) {}

object UserDao {
  val users: TableQuery[Users] = TableQuery[Users]

  def findByName(name: String): Option[User] = {
    db withSession { implicit session =>
      val q1 = for (u <- users if u.username === name) yield u
      q1.list.headOption
    }
  }
  
  def create(name: String, password: String) = {
    db withSession { implicit session =>
      users += User(0l, name, PasswordHashing.encryptPassword(password))
    }
  }

  def authenticate(name: String, password: String): Boolean = {
    db withSession { implicit session =>
      val q1 = for (u <- users if u.username === name && u.password === PasswordHashing.encryptPassword(password)) yield u
      println("^^^^^^^^" + Query(q1.length).first)
      Query(q1.length).first == 1
    }
  }

}
