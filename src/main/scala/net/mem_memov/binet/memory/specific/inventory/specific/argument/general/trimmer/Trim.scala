package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer

trait Trim[TRIMMER, ADDRESS]:

  private[Trim]
  def f(
    address: ADDRESS
  ): ADDRESS

  extension (trimmer: TRIMMER)

    def trim(
      address: ADDRESS
    ): ADDRESS =

      f(address)

