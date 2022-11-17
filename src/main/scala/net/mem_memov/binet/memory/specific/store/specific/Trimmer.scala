package net.mem_memov.binet.memory.specific.store.specific

import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight

import scala.annotation.tailrec

class Trimmer

object Trimmer:

  given TrimRight[Trimmer, Block] with

    @tailrec
    override
    def f(
      trimmer: Trimmer,
      blocks: Vector[Block]
    ): Vector[Block] =

      if blocks.isEmpty then
        blocks
      else
        if blocks.last.isEmpty then
          f(trimmer, blocks.dropRight(1))
        else
          blocks


