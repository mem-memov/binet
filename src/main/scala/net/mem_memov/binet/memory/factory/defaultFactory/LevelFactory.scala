package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.level.DefaultLevel

trait LevelFactory:

  def makeLevel(number: Int): Level

  lazy val emptyLevel: Level

object LevelFactory:

  val cachedFactory: Option[LevelFactory] = None

  def apply()(using StockFactory, StoreFactory): LevelFactory = cachedFactory.getOrElse {

    new LevelFactory:

      override def makeLevel(number: Int): Level =
        given LevelFactory = this
        DefaultLevel(number)

      override lazy val emptyLevel: Level = makeLevel(0)
  }


