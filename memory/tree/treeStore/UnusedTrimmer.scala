package net.mem_memov.binet.memory.tree.treeStore
import net.mem_memov.binet.memory.Block

trait UnusedTrimmer(fail: String => Nothing) extends Trimmer:

  override
  def trimRight(
    blocks: Vector[Block]
  ): Vector[Block] =

    fail("unexpected")
