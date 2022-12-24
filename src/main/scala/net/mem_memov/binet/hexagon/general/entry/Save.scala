package net.mem_memov.binet.hexagon.general.entry

trait Save[ENTRY, DICTIONARY]:

  def f(
    entry: ENTRY,
    dictionary: DICTIONARY
  ): Either[String, DICTIONARY]

  extension (entry: ENTRY)

    def save(
      dictionary: DICTIONARY
    ): Either[String, DICTIONARY] =

      f(entry, dictionary)