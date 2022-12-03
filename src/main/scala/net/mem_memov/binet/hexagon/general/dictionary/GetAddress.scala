package net.mem_memov.binet.hexagon.general.dictionary

trait GetAddress[DICTIONARY, ADDRESS]:

  def f(
    dictionary: DICTIONARY
  ): Either[String, ADDRESS]

  extension (dictionary: DICTIONARY)

    def getAddress: Either[String, ADDRESS] =

      f(dictionary)
