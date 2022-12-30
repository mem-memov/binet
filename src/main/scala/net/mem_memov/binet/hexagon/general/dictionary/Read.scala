package net.mem_memov.binet.hexagon.general.dictionary

trait Read[DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    pathEntry: ENTRY
  ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)]

  extension (dictionary: DICTIONARY)

    def read(
      pathEntry: ENTRY
    ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)] =

      f(dictionary, pathEntry)
