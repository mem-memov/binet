package net.mem_memov.binet.memory.specific.inventory.general.argument

trait CheckAndTrimPermissive[ARGUMENT, ADDRESS]:

  def checkAndTrimAddressPermissive(
    argument: ARGUMENT,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimPermissive(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      checkAndTrimAddressPermissive(argument, next, address)
