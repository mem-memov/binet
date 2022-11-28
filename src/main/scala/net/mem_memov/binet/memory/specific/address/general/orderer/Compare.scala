package net.mem_memov.binet.memory.specific.address.general.orderer

trait Compare[ORDERER, ADDRESS]:

  private[Compare]
  def f(
    left: ADDRESS,
    right: ADDRESS
  ): Int

  extension (orderer: ORDERER)

    def compare(
      left: ADDRESS,
      right: ADDRESS
    ): Int =

      f(left, right)
