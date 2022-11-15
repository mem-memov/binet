package net.mem_memov.binet.memory.specific.specificInventory.general.argument

trait CheckAndTrimRestrictive[ARGUMENT]:

  def checkAndTrimAddressRestrictive[
    ADDRESS
  ](
    argument: ARGUMENT,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimRestrictive[
      ADDRESS
    ](
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      checkAndTrimAddressRestrictive(argument, next, address)
