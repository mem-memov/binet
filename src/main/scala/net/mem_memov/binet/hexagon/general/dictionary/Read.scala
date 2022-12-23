package net.mem_memov.binet.hexagon.general.dictionary

trait Read[DICTIONARY, ADDRESS, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    path: ADDRESS
  ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)]

  extension (dictionary: DICTIONARY)

    def read(
      path: ADDRESS
    ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)] =

      f(dictionary, path)
