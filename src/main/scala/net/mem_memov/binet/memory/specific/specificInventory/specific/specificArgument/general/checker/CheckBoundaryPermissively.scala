package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker

trait CheckBoundaryPermissively[CHECKER]:

  def checkAddressBoundaryPermissively[
    ADDRESS : Ordering
  ](
    checker: CHECKER,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]

  extension (checker: CHECKER)

    def checkBoundaryPermissively[
      ADDRESS : Ordering
    ](
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      checkAddressBoundaryPermissively(checker, next, address)
