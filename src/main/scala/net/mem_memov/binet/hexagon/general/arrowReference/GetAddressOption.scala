package net.mem_memov.binet.hexagon.general.arrowReference

trait GetAddressOption[ARROW_REFERENCE, ADDRESS]:

  def f(
    arrowReference: ARROW_REFERENCE
  ): Option[ADDRESS]

  extension (arrowReference: ARROW_REFERENCE)

    def getAddressOption: Option[ADDRESS] =

      f(arrowReference)
