package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer

trait Trim[TRIMMER, ADDRESS]:

  def f(
    trimmer: TRIMMER,
    address: ADDRESS
  ): ADDRESS

  extension (trimmer: TRIMMER)

    def trim(
      address: ADDRESS
    ): ADDRESS =

      f(trimmer, address)

