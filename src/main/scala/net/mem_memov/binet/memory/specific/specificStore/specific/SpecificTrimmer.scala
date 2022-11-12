package net.mem_memov.binet.memory.specific.specificStore.specific

import net.mem_memov.binet.memory.specific.SpecificBlock
import net.mem_memov.binet.memory.specific.specificStore.general.trimmer.TrimmingService

import scala.annotation.tailrec

class SpecificTrimmer

object SpecificTrimmer:

  given TrimmingService[SpecificTrimmer, SpecificBlock] with

    @tailrec
    override
    def trimBlocksRight(
      trimmer: SpecificTrimmer,
      blocks: Vector[SpecificBlock]
    ): Vector[SpecificBlock] =

      if blocks.isEmpty then
        blocks
      else
        if blocks.last.isEmpty then
          trimBlocksRight(trimmer, blocks.dropRight(1))
        else
          blocks


