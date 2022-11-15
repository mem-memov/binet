package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general.path.{IsEmpty, Shorten}

trait Write[
  ELEMENT,
  PATH : IsEmpty : Shorten,
  CONTENT
]:

  def writeElement(
    element: ELEMENT,
    destination: PATH,
    content: CONTENT
  ): Either[String, ELEMENT]

  extension (element: ELEMENT)

    def write(
      destination: PATH,
      content: CONTENT
    ): Either[String, ELEMENT] =

      writeElement(element, destination, content)
