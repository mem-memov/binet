package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.{TreeBlock, TreeStore}

/**
 * Store is capable of storing addresses.
 */
trait Store[S]:

  def writeStore(
    store: S,
    destination: UnsignedByte,
    content: Content
  ): S

  def readStore(
    store: S,
    origin: UnsignedByte
  ): Address

  extension (store: S)

    def write(
      destination: UnsignedByte,
      content: Content
    ): S =

      writeStore(store, destination, content)

    def read(
      origin: UnsignedByte
    ): Address =

      readStore(store, origin)

