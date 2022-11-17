package net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker

trait CheckType[CHECKER, ADDRESS]:

  def f(
    checker: CHECKER,
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, Unit]

  extension (checker: CHECKER)

    def checkType(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      f(checker, next, address)
