package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.element.DefaultElement

trait ElementFactory:

  def makeElement(level: Level): Element

  lazy val rootElement: Element

object ElementFactory:
  
  def apply()(using levelFactory: LevelFactory): ElementFactory =
    
    new ElementFactory:
      
      override def makeElement(level: Level): Element = 
        DefaultElement(level, None, None)
        
      override lazy val rootElement: Element =
        DefaultElement(levelFactory.emptyLevel(this), None, None)



