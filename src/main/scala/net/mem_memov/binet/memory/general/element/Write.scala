package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general

trait Write[
  ELEMENT,
  PATH : general.path.IsEmpty : general.path.Shorten,
  CONTENT
]:

  def f(
    element: ELEMENT,
    destination: PATH,
    content: CONTENT
  ): Either[String, ELEMENT]

  extension (element: ELEMENT)

    def write(
      destination: PATH,
      content: CONTENT
    ): Either[String, ELEMENT] =

      f(element, destination, content)
