package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.level.DefaultLevel

trait LevelFactory:

  def makeLevel(number: Int)(using elementFactory: ElementFactory): Level

  def emptyLevel(elementFactory: ElementFactory): Level

object LevelFactory:

  def apply()(using StockFactory, StoreFactory): LevelFactory = 
    
    new LevelFactory:

      override def makeLevel(number: Int)(using elementFactory: ElementFactory): Level =
        given LevelFactory = this
        DefaultLevel(number)

      override def emptyLevel(using elementFactory: ElementFactory): Level = makeLevel(0)



