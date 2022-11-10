package net.mem_memov.binet.memory.hexagon.connecting

import net.mem_memov.binet.hexagon.Connecting
import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.hexagon.{Arrow, Connecting, Dot, Entry, Network}
import net.mem_memov.binet.memory.tree
import net.mem_memov.binet.memory.tree.TreeFactory
import zio.*

class BothDotsEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot
) extends Connecting:

  override def connect: Task[Arrow] =
    val entry = Entry(
      sourceDot.address,
      TreeFactory.emptyAddress,
      tree.TreeFactory.emptyAddress,
      targetDot.address,
      tree.TreeFactory.emptyAddress,
      tree.TreeFactory.emptyAddress
    )
    for {
      newArrow <- network.createArrow(entry)
      _ <- sourceDot.setTargetArrow(newArrow)
      _ <- targetDot.setSourceArrow(newArrow)
    } yield newArrow
