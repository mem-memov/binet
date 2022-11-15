package net.mem_memov.binet.memory.general.store

import net.mem_memov.binet.memory.general.UnsignedByte

trait StoreReader[STORE, ADDRESS]:

  def readStore(
    store: STORE,
    origin: UnsignedByte
  ): ADDRESS

  extension (store: STORE)

    def read(
      origin: UnsignedByte
    ): ADDRESS =

      readStore(store, origin)
