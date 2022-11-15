package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general.path.{PathEmptyChecker, PathShortener}

trait ElementWriter[ELEMENT]:

  def writeElement[
    PATH : PathEmptyChecker : PathShortener,
    CONTENT
  ](
    element: ELEMENT,
    destination: PATH,
    content: CONTENT
  ): Either[String, ELEMENT]

  extension (element: ELEMENT)

    def write[
      PATH : PathEmptyChecker : PathShortener,
      CONTENT
    ](
      destination: PATH,
      content: CONTENT
    ): Either[String, ELEMENT] =

      writeElement(element, destination, content)
