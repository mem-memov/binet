package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Arrow, Connecting, Connection, Dot, Entry, Network}
import net.mem_memov.binet.memory
import zio.*

class TargetDotEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot,
  sourceDotTargetArrow: Arrow
) extends Connecting:

  override def connect: Task[Connection] =
    val entry = Entry(
      sourceDot.address,
      sourceDotTargetArrow.address,
      memory.Address.zero,
      targetDot.address,
      memory.Address.zero,
      memory.Address.zero
    )
    for {
      newArrow <- network.createArrow(entry)
      newSourceDot <- sourceDot.setTargetArrow(newArrow)
      newTargetDot <- targetDot.setSourceArrow(newArrow)
      _ <- sourceDotTargetArrow.setNextSourceArrow(newArrow)
    } yield Connection(newSourceDot, newTargetDot, newArrow)
