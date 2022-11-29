package net.mem_memov.binet.hexagon.general.dictionary

trait Append[DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    entry: ENTRY
  ): Either[String, DICTIONARY]

  extension (dictionary: DICTIONARY)

    def append(
      entry: ENTRY
    ): Either[String, DICTIONARY] =

      f(dictionary, entry)
