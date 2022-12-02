package net.mem_memov.binet.hexagon.general.weaver

trait GetArrow[WEAVER, ADDRESS, ARROW, DICTIONARY]:

  def f(
    dictionary: DICTIONARY,
    address: ADDRESS
  ): Either[String, ARROW]

  extension (weaver: WEAVER)

    def getArrow(
      dictionary: DICTIONARY,
      address: ADDRESS
    ): Either[String, ARROW] =

      f(dictionary, address)
