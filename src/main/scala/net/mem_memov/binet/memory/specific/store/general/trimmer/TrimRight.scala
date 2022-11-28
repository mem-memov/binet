package net.mem_memov.binet.memory.specific.store.general.trimmer

trait TrimRight[TRIMMER, BLOCK]:

  private[TrimRight]
  def f(
    blocks: Vector[BLOCK]
  ): Vector[BLOCK]

  extension (trimmer: TRIMMER)

    def trimRight(
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      f(blocks)