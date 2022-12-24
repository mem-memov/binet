package net.mem_memov.binet.hexagon.general.dictionary

trait Update[DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    entry: ENTRY
  ): Either[String, DICTIONARY]

  extension (dictionary: DICTIONARY)

    def update(
      entry: ENTRY
    ): Either[String, DICTIONARY] =

      f(dictionary, entry)