package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Dot, Entry, Network}
import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.tree
import net.mem_memov.binet.memory.tree.TreeFactory
import zio.*

class BothDotsNonEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  sourceDotTargetArrow: Arrow,
  targetDotSourceArrow: Arrow
) extends Connecting:

  override def connect: Task[Arrow] =
    val entry = Entry(
      sourceDot.address,
      sourceDotTargetArrow.address,
      TreeFactory.emptyAddress,
      targetDot.address,
      targetDotSourceArrow.address,
      tree.TreeFactory.emptyAddress
    )
    for {
      newArrow <- network.createArrow(entry)
      _ <- sourceDot.setTargetArrow(newArrow)
      _ <- targetDot.setSourceArrow(newArrow)
      _ <- sourceDotTargetArrow.setNextSourceArrow(newArrow)
      _ <- targetDotSourceArrow.setNextTargetArrow(newArrow)
    } yield newArrow