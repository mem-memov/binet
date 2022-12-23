package net.mem_memov.binet.hexagon.general.dotReference

trait ReadDot[DOT_REFERENCE, DICTIONARY, DOT]:

  def f(
    dotReference: DOT_REFERENCE,
    dictionary: DICTIONARY
  ): Either[String, DOT]

  extension (dotReference: DOT_REFERENCE)

    def readDot(
      dictionary: DICTIONARY
    ): Either[String, DOT] =

      f(dotReference, dictionary)
