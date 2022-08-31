package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Connection, Dot, Entry, Network}
import net.mem_memov.binet.memory
import zio.Task

class SourceDotEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  targetDotSourceArrow: Arrow
) extends Connecting:

  override def connect: Task[Connection] =
    val entry = Entry(
      sourceDot.address,
      memory.Address.zero,
      memory.Address.zero,
      targetDot.address,
      targetDotSourceArrow.address,
      memory.Address.zero
    )
    for {
      newArrow <- network.createArrow(entry)
      newSourceDot <- sourceDot.setTargetArrow(newArrow)
      newTargetDot <- targetDot.setSourceArrow(newArrow)
      _ <- targetDotSourceArrow.setNextTargetArrow(newArrow)
    } yield Connection(newSourceDot, newTargetDot, newArrow)

