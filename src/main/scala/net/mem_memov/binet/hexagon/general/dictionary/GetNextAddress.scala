package net.mem_memov.binet.hexagon.general.dictionary

trait GetNextAddress[DICTIONARY, ADDRESS]:

  def f(
    dictionary: DICTIONARY
  ): ADDRESS

  extension (dictionary: DICTIONARY)

    def getNextAddress: ADDRESS =

      f(dictionary)
