package net.mem_memov.binet.hexagon.general.arrowReference

trait ReadArrow[ARROW_REFERENCE, ARROW, DICTIONARY]:

  def f(
    arrowReference: ARROW_REFERENCE,
    dictionary: DICTIONARY
  ): Either[String, Option[ARROW]]

  extension (arrowReference: ARROW_REFERENCE)

    def readArrow(
      dictionary: DICTIONARY
    ): Either[String, Option[ARROW]] =

      f(arrowReference, dictionary)
