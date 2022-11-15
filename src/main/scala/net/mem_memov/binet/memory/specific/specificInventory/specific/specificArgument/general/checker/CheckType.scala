package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker

trait CheckType[CHECKER, ADDRESS]:

  def checkAddressType(
    checker: CHECKER,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]

  extension (checker: CHECKER)

    def checkType(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      checkAddressType(checker, next, address)
