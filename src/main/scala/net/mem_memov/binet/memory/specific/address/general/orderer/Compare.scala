package net.mem_memov.binet.memory.specific.address.general.orderer

trait Compare[ORDERER, ADDRESS]:

  def f(
    oderer: ORDERER,
    left: ADDRESS,
    right: ADDRESS
  ): Int

  extension (orderer: ORDERER)

    def compare(
      left: ADDRESS,
      right: ADDRESS
    ): Int =

      f(orderer, left, right)
