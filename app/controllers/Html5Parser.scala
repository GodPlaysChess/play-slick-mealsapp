package controllers

import models.slick.Dish
import play.api.Play

import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{InputSource, SAXParser, Source}

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
    (htmlObject \\ "div")                                     // all divs
      .filter (n => (n \ "@class").text == "TafelBezei")      // with class TefelBezei
      .map(_.text)                                            // take the label
      .map(Dish(0, _))                                        // create objects for each
  }

}
