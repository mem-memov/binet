package net.mem_memov.binet.memory.general.element

trait ElementWriter[ELEMENT, PATH, CONTENT]:

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
