package net.mem_memov.binet.hexagon.general.dictionary

trait Read[DICTIONARY, ADDRESS, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    address: ADDRESS
  ): Either[String, ENTRY]

  extension (dictionary: DICTIONARY)

    def read(
      address: ADDRESS
    ): Either[String, ENTRY] =

      f(dictionary, address)
