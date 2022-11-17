package net.mem_memov.binet.memory.general.store

import net.mem_memov.binet.memory.general.UnsignedByte

trait Read[STORE, ADDRESS]:

  def f(
    store: STORE,
    origin: UnsignedByte
  ): ADDRESS

  extension (store: STORE)

    def read(
      origin: UnsignedByte
    ): ADDRESS =

      f(store, origin)
