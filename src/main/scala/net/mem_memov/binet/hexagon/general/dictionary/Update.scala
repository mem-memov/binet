package net.mem_memov.binet.hexagon.general.dictionary

trait Update[DICTIONARY, ADDRESS, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    address: ADDRESS,
    entry: ENTRY
  ): Either[String, DICTIONARY]

  extension (dictionary: DICTIONARY)

    def update(
      address: ADDRESS,
      entry: ENTRY
    ): Either[String, DICTIONARY] =

      f(dictionary, address, entry)