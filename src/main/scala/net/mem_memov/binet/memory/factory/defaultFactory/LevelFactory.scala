package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.level.DefaultLevel

trait LevelFactory:

  def makeLevel(number: Int): Level

object LevelFactory:

  def apply()(using DepthFactory, StockFactory, StoreFactory): LevelFactory =
    new LevelFactory:
      override def makeLevel(number: Int): Level =
        given LevelFactory = this
        DefaultLevel(number)
