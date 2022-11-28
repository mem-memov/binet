package net.mem_memov.binet.memory.specific.inventory.general.argument

trait CheckAndTrimRestrictive[ARGUMENT, ADDRESS]:

  private[CheckAndTrimRestrictive]
  def f(
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimRestrictive(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      f(next, address)
