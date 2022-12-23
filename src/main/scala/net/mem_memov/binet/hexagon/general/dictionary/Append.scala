package net.mem_memov.binet.hexagon.general.dictionary

trait Append[DICTIONARY, ADDRESS, ENTRY]:

  def f(
    dictionary: DICTIONARY,
    addressOptions: (Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS])
  ): Either[String, (DICTIONARY, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))]

  extension (dictionary: DICTIONARY)

    def append(
      addressOptions: (Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS])
    ): Either[String, (DICTIONARY, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))] =

      f(dictionary, addressOption)
