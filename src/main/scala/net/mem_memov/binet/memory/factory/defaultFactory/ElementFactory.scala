package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.element.DefaultElement

trait ElementFactory:

  def makeElement(level: Level): Element

object ElementFactory:
  
  def apply(): ElementFactory = DefaultElement(_, None, None)