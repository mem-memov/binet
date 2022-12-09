package net.mem_memov.binet.hexagon.general.dictionary

trait Append[DICTIONARY, ADDRESS, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    entry: ENTRY
  ): Either[String, (DICTIONARY, ADDRESS)]

  extension (dictionary: DICTIONARY)

    def append(
      entry: ENTRY
    ): Either[String, (DICTIONARY, ADDRESS)] =

      f(dictionary, entry)
