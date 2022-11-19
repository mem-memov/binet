package net.mem_memov.binet.memory.specific.store.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight

import scala.annotation.tailrec

class Trimmer

object Trimmer:

  given [BLOCK](using
    general.block.IsEmpty[BLOCK]
  ): TrimRight[Trimmer, BLOCK] with

    // private function needed for tail-recursion optimization
    @tailrec
    private def trim(
      trimmer: Trimmer,
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      if blocks.isEmpty then
        blocks
      else
        if blocks.last.isEmpty then
          trim(trimmer, blocks.dropRight(1))
        else
          blocks

    override
    def f(
      trimmer: Trimmer,
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      trim(trimmer, blocks)


