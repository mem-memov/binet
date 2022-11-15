package net.mem_memov.binet.memory.specific.address.general.orderer

trait ComparingOrderer[ORDERER, ADDRESS]:

  def compareAddresses(
    oderer: ORDERER,
    left: ADDRESS,
    right: ADDRESS
  ): Int

  extension (orderer: ORDERER)

    def compare(
      left: ADDRESS,
      right: ADDRESS
    ): Int =

      compareAddresses(orderer, left, right)
