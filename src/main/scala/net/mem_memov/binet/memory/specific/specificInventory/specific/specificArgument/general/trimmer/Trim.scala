package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer

trait Trim[TRIMMER]:

  def trimAddress[
    ADDRESS
  ](
    trimmer: TRIMMER,
    address: ADDRESS
  ): ADDRESS

  extension (trimmer: TRIMMER)

    def trim[
      ADDRESS
    ](
      address: ADDRESS
    ): ADDRESS =

      trimAddress(trimmer, address)

