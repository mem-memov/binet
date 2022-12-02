package net.mem_memov.binet.hexagon.general.writer

trait UpdateEntry[WRITER, ADDRESS, DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    address: ADDRESS,
    entry: ENTRY
  ): Either[String, DICTIONARY]

  extension (writer: WRITER)

    def updateEntry(
      dictionary: DICTIONARY,
      address: ADDRESS,
      entry: ENTRY
    ): Either[String, DICTIONARY] =

      f(dictionary, address, entry)
