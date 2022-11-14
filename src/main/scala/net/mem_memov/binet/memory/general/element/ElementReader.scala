package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general.path.{PathEmptyChecker, PathShortener}

trait ElementReader[
  ELEMENT,
  PATH : PathEmptyChecker : PathShortener,
  CONTENT
]:

  def readElement(
    element: ELEMENT,
    origin: PATH
  ): Either[String, CONTENT]

  extension (element: ELEMENT)

    def read(
      origin: PATH
    ): Either[String, CONTENT] =

      readElement(element, origin)