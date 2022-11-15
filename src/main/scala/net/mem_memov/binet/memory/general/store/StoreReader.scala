package net.mem_memov.binet.memory.general.store

import net.mem_memov.binet.memory.general.UnsignedByte

trait StoreReader[STORE]:

  def readStore[
    ADDRESS
  ](
    store: STORE,
    origin: UnsignedByte
  ): ADDRESS

  extension (store: STORE)

    def read[
      ADDRESS
    ](
      origin: UnsignedByte
    ): ADDRESS =

      readStore(store, origin)
