package net.mem_memov.binet.memory.specific.specificAddress.general.orderer

import net.mem_memov.binet.memory.general.address.AddressIndices

trait ComparingOrderer[ORDERER]:

  def compareAddresses[
    ADDRESS : AddressIndices
  ](
    oderer: ORDERER,
    left: ADDRESS,
    right: ADDRESS
  ): Int

  extension (orderer: ORDERER)

    def compare[
      ADDRESS : AddressIndices
    ](
      left: ADDRESS,
      right: ADDRESS
    ): Int =

      compareAddresses(orderer, left, right)
