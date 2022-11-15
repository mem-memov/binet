package net.mem_memov.binet.memory.specific.store.specific

import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimmingService

import scala.annotation.tailrec

class SpecificTrimmer

object SpecificTrimmer:

  given TrimmingService[SpecificTrimmer, Block] with

    @tailrec
    override
    def trimBlocksRight(
      trimmer: SpecificTrimmer,
      blocks: Vector[Block]
    ): Vector[Block] =

      if blocks.isEmpty then
        blocks
      else
        if blocks.last.isEmpty then
          trimBlocksRight(trimmer, blocks.dropRight(1))
        else
          blocks


