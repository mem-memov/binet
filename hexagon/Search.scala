package net.mem_memov.binet.memory.hexagon

import zio.*

class Search(arrow: Arrow):

  def withSourceDot(searchedDot: Dot): Task[Option[Arrow]] =
    arrow.sourceDot.flatMap { dot =>
      if dot.address == searchedDot.address then
        ZIO.succeed(Some(arrow))
      else
        arrow.nextSourceArrow.flatMap {
          case None => ZIO.succeed(None)
          case Some(nextArrow) => nextArrow.search.withSourceDot(searchedDot)
        }
    }

  def withTargetDot(searchedDot: Dot): Task[Option[Arrow]] =
    arrow.targetDot.flatMap { dot =>
      if dot.address == searchedDot.address then
        ZIO.succeed(Some(arrow))
      else
        arrow.nextTargetArrow.flatMap {
          case None => ZIO.succeed(None)
          case Some(nextArrow) => nextArrow.search.withTargetDot(searchedDot)
        }
    }
