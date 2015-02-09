package controllers

import models.slick.Dish
import play.api.Play

import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{NodeSeq, InputSource, SAXParser, Source}

class Html5Parser extends NoBindingFactoryAdapter {

  val test = Source.fromString(
    """<html><head/><body><div class='main'><span>test1</span></div>
  <div class='main'><span>test2</span></div></body></html>"""
  )

  override def loadXML(source: InputSource, _p: SAXParser) = {
    loadXML(source)
  }

  def loadXML(source: InputSource) = {
    import nu.validator.htmlparser.common.XmlViolationPolicy
    import nu.validator.htmlparser.sax.HtmlParser

    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }

  //yet from file for testing purposes
  def parseHtml: Seq[Dish] = {
    import play.api.Play.current
    val file = Play.application.classloader.getResource("testSource.html").getFile
    val source = Source.fromFile(file)
    val htmlObject = loadXML(source)

    val a: NodeSeq = (htmlObject \\ "tr")
//    val b: NodeSeq = a.filter(n => (n \\ "@class").text == "clsTableItemBestell")
    val b = a.filter(n => (n \ "@class").text == "clsTableItemBestell")
      b.map(parseFood)
   /*
    (htmlObject \\ "div")                                     // all divs
      .filter (n => (n \ "@class").text == "TafelBezei")      // with class TefelBezei
      .map(_.text)                                            // take the label
      .map(Dish(0, _))                                        // create objects for each
      */
  }

  def parseFood(node: NodeSeq): Dish = {
    val fullname = (node \\ "div").filter(n => (n \ "@class").text == "TafelBezei").text
    val price = (node \\ "span").filter(n => (n \\ "@id").text == "grdTafel__ctl15_labPreiswert").text
    val weight = (node \\ "tr").filter(n => (n \ "@id").text.endsWith("trNWEinwaage")).\\("div").text.filter(Character.isDigit).toInt
    val fat = (node \\ "tr").filter(n => (n \ "@id").text.endsWith("lblFett")).text.filter(Character.isDigit).toInt
    val carbs = (node \\ "tr").filter(n => (n \\ "@id").text.endsWith("lblKH")).text.filter(Character.isDigit).toInt
    val prot = (node \\ "tr").filter(n => (n \\ "@id").text.endsWith("lblEI")).text.filter(Character.isDigit).toInt
    val cals = (node \\ "tr").filter(n => (n \\ "@id").text.endsWith("divBrennwert")).text
    val (code, name) = fullname splitAt 6
    Dish(0, name, code, weight, cals, prot, carbs, fat, price)
  }

}
