package net.mem_memov.binet.memory.general.store

import net.mem_memov.binet.memory.general.UnsignedByte

trait StoreWriter[STORE, CONTENT]:

  def writeStore(
    store: STORE,
    destination: UnsignedByte,
    content: CONTENT
  ): STORE

  extension (store: STORE)

    def write(
      destination: UnsignedByte,
      content: CONTENT
    ): STORE =

      writeStore(store, destination, content)
