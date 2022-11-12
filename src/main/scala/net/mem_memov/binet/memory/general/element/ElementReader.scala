package net.mem_memov.binet.memory.general.element

trait ElementReader[ELEMENT, PATH, CONTENT]:

  def readElement(
    element: ELEMENT,
    origin: PATH
  ): Either[String, CONTENT]

  extension (element: ELEMENT)

    def read(
      origin: PATH
    ): Either[String, CONTENT] =

      readElement(element, origin)