package net.mem_memov.binet.memory.general.element

import net.mem_memov.binet.memory.general

trait Read[
  ELEMENT,
  PATH,
  CONTENT
]:

  def f(
    element: ELEMENT,
    origin: PATH
  ): Either[String, CONTENT]

  extension (element: ELEMENT)

    def read(
      origin: PATH
    ): Either[String, CONTENT] =

      f(element, origin)