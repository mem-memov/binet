package net.mem_memov.binet.hexagon.general.dictionary

trait AppendDot[DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY
  ): Either[String, (DICTIONARY, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))]

  extension (dictionary: DICTIONARY)

    def appendDot: Either[String, (DICTIONARY, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))] =

      f(dictionary)
