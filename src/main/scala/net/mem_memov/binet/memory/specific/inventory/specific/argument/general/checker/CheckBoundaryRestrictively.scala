package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker

trait CheckBoundaryRestrictively[CHECKER, ADDRESS]:

  def checkAddressBoundaryRestrictively(
    checker: CHECKER,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]
  
  extension (checker: CHECKER)

    def checkBoundaryRestrictively(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      checkAddressBoundaryRestrictively(checker, next, address)
