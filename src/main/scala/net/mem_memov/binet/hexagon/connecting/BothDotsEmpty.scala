package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Dot, Entry, Network}
import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.live
import net.mem_memov.binet.memory.live.DefaultFactory
import zio.*

class BothDotsEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot
) extends Connecting:

  override def connect: Task[Arrow] =
    val entry = Entry(
      sourceDot.address,
      DefaultFactory.emptyAddress,
      live.DefaultFactory.emptyAddress,
      targetDot.address,
      live.DefaultFactory.emptyAddress,
      live.DefaultFactory.emptyAddress
    )
    for {
      newArrow <- network.createArrow(entry)
      _ <- sourceDot.setTargetArrow(newArrow)
      _ <- targetDot.setSourceArrow(newArrow)
    } yield newArrow
