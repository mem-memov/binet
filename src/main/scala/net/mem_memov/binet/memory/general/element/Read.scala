package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general

trait Read[
  ELEMENT,
  PATH : general.path.IsEmpty : general.path.Shorten,
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