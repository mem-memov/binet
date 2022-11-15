package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general.path.{PathEmptyChecker, PathShortener}

trait ElementReader[ELEMENT]:

  def readElement[
    PATH : PathEmptyChecker : PathShortener,
    CONTENT
  ](
    element: ELEMENT,
    origin: PATH
  ): Either[String, CONTENT]

  extension (element: ELEMENT)

    def read[
      PATH : PathEmptyChecker : PathShortener,
      CONTENT
    ](
      origin: PATH
    ): Either[String, CONTENT] =

      readElement(element, origin)