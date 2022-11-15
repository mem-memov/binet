package net.mem_memov.binet.memory.specific.specificStore.general.trimmer

import net.mem_memov.binet.memory.general.block.BlockEmptyChecker

trait TrimmingService[TRIMMER]:

  def trimBlocksRight[
    BLOCK : BlockEmptyChecker
  ](
    trimmer: TRIMMER,
    blocks: Vector[BLOCK]
  ): Vector[BLOCK]

  extension (trimmer: TRIMMER)

    def trimRight[
      BLOCK
    ](
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      trimBlocksRight(trimmer, blocks)