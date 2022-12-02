package net.mem_memov.binet.hexagon.general.writer

trait GetEntry[WRITER, ADDRESS, DICTIONARY, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    address: ADDRESS
  ): Either[String, Option[ENTRY]]

  extension (writer: WRITER)

    def getEntry(
      dictionary: DICTIONARY,
      address: ADDRESS
    ): Either[String, Option[ENTRY]] =

      f(dictionary, address)
