package net.mem_memov.binet.memory.specific.specificStore.general.trimmer

trait TrimmingService[TRIMMER, BLOCK]:

  def trimBlocksRight(
    trimmer: TRIMMER,
    blocks: Vector[BLOCK]
  ): Vector[BLOCK]

  extension (trimmer: TRIMMER)

    def trimRight(
      blocks: Vector[BLOCK]
    ): Vector[BLOCK] =

      trimBlocksRight(trimmer, blocks)