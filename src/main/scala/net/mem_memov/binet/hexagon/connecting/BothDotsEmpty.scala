package net.mem_memov.binet.hexagon.connecting

import net.mem_memov.binet.hexagon.{Connecting, Connection, Dot, Entry, Network}
import net.mem_memov.binet.memory
import zio.*

class BothDotsEmpty(
  network: Network,
  sourceDot: Dot,
  targetDot: Dot
) extends Connecting:

  override def connect: Task[Connection] =
    val entry = Entry(
      sourceDot.address,
      memory.Address.zero,
      memory.Address.zero,
      targetDot.address,
      memory.Address.zero,
      memory.Address.zero
    )
    for {
      newArrow <- network.createArrow(entry)
      newSourceDot <- sourceDot.setTargetArrow(newArrow)
      newTargetDot <- targetDot.setSourceArrow(newArrow)
    } yield Connection(newSourceDot, newTargetDot, newArrow)
