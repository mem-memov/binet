package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer

trait Trim[TRIMMER, ADDRESS]:

  def trimAddress(
    trimmer: TRIMMER,
    address: ADDRESS
  ): ADDRESS

  extension (trimmer: TRIMMER)

    def trim(
      address: ADDRESS
    ): ADDRESS =

      trimAddress(trimmer, address)

