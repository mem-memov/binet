package net.mem_memov.binet.memory.specific.specificInventory.general.argument

trait CheckAndTrimPermissive[ARGUMENT]:

  def checkAndTrimAddressPermissive[
    ADDRESS
  ](
    argument: ARGUMENT,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimPermissive[
      ADDRESS
    ](
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      checkAndTrimAddressPermissive(argument, next, address)
