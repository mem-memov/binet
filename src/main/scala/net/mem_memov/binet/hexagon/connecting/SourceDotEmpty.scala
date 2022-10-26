package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Dot, Entry, Network}
import net.mem_memov.binet.memory
import zio.Task

class SourceDotEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  targetDotSourceArrow: Arrow
) extends Connecting:

  override def connect: Task[Arrow] =
    val entry = Entry(
      sourceDot.address,
      memory.factory.DefaultFactory.emptyAddress,
      memory.factory.DefaultFactory.emptyAddress,
      targetDot.address,
      targetDotSourceArrow.address,
      memory.factory.DefaultFactory.emptyAddress
    )
    for {
      newArrow <- network.createArrow(entry)
      _ <- sourceDot.setTargetArrow(newArrow)
      _ <- targetDot.setSourceArrow(newArrow)
      _ <- targetDotSourceArrow.setNextTargetArrow(newArrow)
    } yield newArrow

