package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general.path.{IsEmpty, Shorten}

trait Read[
  ELEMENT,
  PATH : IsEmpty : Shorten,
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