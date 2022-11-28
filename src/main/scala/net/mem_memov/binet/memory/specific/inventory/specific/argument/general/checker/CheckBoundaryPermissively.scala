package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker

trait CheckBoundaryPermissively[CHECKER, ADDRESS]:

  private[CheckBoundaryPermissively] 
  def f(
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]

  extension (checker: CHECKER)

    def checkBoundaryPermissively(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      f(next, address)
