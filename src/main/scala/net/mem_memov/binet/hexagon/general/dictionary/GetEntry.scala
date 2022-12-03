package net.mem_memov.binet.hexagon.general.dictionary

trait GetEntry[DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY
  ): Either[String, ENTRY]

  extension (dictionary: DICTIONARY)

    def getEntry: Either[String, ENTRY] =

      f(dictionary)
