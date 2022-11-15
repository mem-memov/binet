package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker

trait CheckBoundaryRestrictively[CHECKER]:

  def checkAddressBoundaryRestrictively[
    ADDRESS : Ordering
  ](
    checker: CHECKER,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]
  
  extension (checker: CHECKER)

    def checkBoundaryRestrictively[
      ADDRESS : Ordering
    ](
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      checkAddressBoundaryRestrictively(checker, next, address)
