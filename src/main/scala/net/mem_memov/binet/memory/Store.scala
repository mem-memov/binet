package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.{TreeBlock, TreeStore}

/**
 * Store is capable of storing addresses.
 */
trait Store[S, A : Address, C : Content]:

  def writeStore(
    store: S,
    destination: UnsignedByte,
    content: C
  ): S

  def readStore(
    store: S,
    origin: UnsignedByte
  ): A

  extension (store: S)

    def write(
      destination: UnsignedByte,
      content: C
    ): S =

      writeStore(store, destination, content)

    def read(
      origin: UnsignedByte
    ): A =

      readStore(store, origin)

