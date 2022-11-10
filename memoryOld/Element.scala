package net.mem_memov.binet.memoryOld

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 * Data represented by addresses is kept in a store.
 * The number of addresses in the store and the number of references to other elements in the stock are the same.
 * The level determines the number of parts of addresses in the store.
 */
trait Element[E, C : Content, P : Path]:

  def writeElement(
    element: E,
    destination: P,
    content: C
  ): Either[String, E]

  def readElement(
    element: E,
    origin: P
  ): Either[String, C]

  extension (element: E)

    def write(
      destination: P,
      content: C
    ): Either[String, E] =

      writeElement(element, destination, content)

    def read(
      origin: P
    ): Either[String, C] =

      readElement(element, origin)


