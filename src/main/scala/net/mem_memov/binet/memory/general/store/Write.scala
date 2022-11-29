package net.mem_memov.binet.memory.general.store

import net.mem_memov.binet.memory.general.UnsignedByte

trait Write[STORE, CONTENT]:

  def f(
    store: STORE,
    destination: UnsignedByte,
    content: CONTENT
  ): STORE

  extension (store: STORE)

    def write(
      destination: UnsignedByte,
      content: CONTENT
    ): STORE =

      f(store, destination, content)
