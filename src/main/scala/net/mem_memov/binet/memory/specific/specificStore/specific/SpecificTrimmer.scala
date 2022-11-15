package net.mem_memov.binet.memory.specific.specificStore.specific

import net.mem_memov.binet.memory.specific.SpecificBlock
import net.mem_memov.binet.memory.specific.specificStore.general.trimmer.TrimmingService
import net.mem_memov.binet.memory.general.block.BlockEmptyChecker

import scala.annotation.tailrec

class SpecificTrimmer

object SpecificTrimmer:

  given TrimmingService[SpecificTrimmer] with

    @tailrec
    override
    def trimBlocksRight[
      BLOCK : BlockEmptyChecker
    ](
      trimmer: SpecificTrimmer,
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      if blocks.isEmpty then
        blocks
      else
        if blocks.last.isEmpty then
          trimBlocksRight(trimmer, blocks.dropRight(1))
        else
          blocks


