package net.mem_memov.binet.memory.specific.store.general.trimmer

trait TrimRight[TRIMMER, BLOCK]:

  def f(
    trimmer: TRIMMER,
    blocks: Vector[BLOCK]
  ): Vector[BLOCK]

  extension (trimmer: TRIMMER)

    def trimRight(
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      f(trimmer, blocks)