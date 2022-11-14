package net.mem_memov.binet.memory.specific.specificInventory.general.argument

trait CheckAndTrimRestrictive[ARGUMENT, ADDRESS]:

  def checkAndTrimAddressRestrictive(
    argument: ARGUMENT,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimRestrictive(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      checkAndTrimAddressRestrictive(argument, next, address)
