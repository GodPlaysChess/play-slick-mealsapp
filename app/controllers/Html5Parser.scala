package controllers

import models.slick.Dish
import play.api.{Logger, Play}

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

    (htmlObject \\ "tr")                                          // all rows
      .filter(n => (n \ "@class").text == "clsTableItemBestell")  // corresponded to meal entry
      .map(parseFood)                                             // create object for every
  }

  def parseFood(node: NodeSeq): Dish = {
    val fullname = (node \\ "div").filter(n => (n \ "@class").text == "TafelBezei").text.trim
    val price = (node \\ "span").filter(n => (n \\ "@id").text.endsWith("Preiswert")).text.trim
    val weight = (node \\ "tr").filter(n => (n \ "@id").text.endsWith("trNWEinwaage")).\\("div").text.filter(Character.isDigit).toInt
    val fat = (node \\ "tr" \\ "div").filter(n => (n \ "@id").text.endsWith("lblFett")).head.text.filter(Character.isDigit).toInt
    val carbs = (node \\ "tr" \\ "div").filter(n => (n \ "@id").text.endsWith("lblKH")).head.text.filter(Character.isDigit).toInt
    val prot = (node \\ "tr" \\ "div").filter(n => (n \ "@id").text.endsWith("lblEI")).head.text.filter(Character.isDigit).toInt
    val cals =  (node \\ "tr" \\ "div").filter(n => (n \ "@id").text.endsWith("divBrennwert")).head.text.trim
    val (code, name) = fullname splitAt 6
    val d = Dish(0, name.trim, code, weight, cals, prot, carbs, fat, price)
    Logger.info("Parsed -> " + d)
    d
  }

}
