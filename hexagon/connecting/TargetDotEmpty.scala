package net.mem_memov.binet.memory.hexagon.connecting

import net.mem_memov.binet.hexagon.Connecting
import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.hexagon.{Arrow, Connecting, Dot, Entry, Network}
import net.mem_memov.binet.memory.tree
import net.mem_memov.binet.memory.tree.TreeFactory
import zio.*

class TargetDotEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  sourceDotTargetArrow: Arrow
) extends Connecting:

  override def connect: Task[Arrow] =
    val entry = Entry(
      sourceDot.address,
      sourceDotTargetArrow.address,
      TreeFactory.emptyAddress,
      targetDot.address,
      tree.TreeFactory.emptyAddress,
      tree.TreeFactory.emptyAddress
    )
    for {
      newArrow <- network.createArrow(entry)
      _ <- sourceDot.setTargetArrow(newArrow)
      _ <- targetDot.setSourceArrow(newArrow)
      _ <- sourceDotTargetArrow.setNextSourceArrow(newArrow)
    } yield newArrow
