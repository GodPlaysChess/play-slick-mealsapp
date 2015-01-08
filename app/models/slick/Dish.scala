package models.slick

/**
 * Created by Gleb on 1/4/2015.
 */

case class Dish(id: Long, name: String, likes: Int = 0) {
  
//  def queryScores: Seq[Int] = DatabaseSetup.db withSession { implicit session =>
//    Tables.dishscores.filter(_.dishId === id).list
//  }
//
//  def avgScore: Double = DatabaseSetup.db withSession { implicit session =>
//    Tables.dishscores.filter(_.dishId === id).avg
//  }
//

}
