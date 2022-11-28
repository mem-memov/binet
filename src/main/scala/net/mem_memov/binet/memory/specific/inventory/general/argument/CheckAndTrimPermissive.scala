package net.mem_memov.binet.memory.specific.inventory.general.argument

trait CheckAndTrimPermissive[ARGUMENT, ADDRESS]:

  private[CheckAndTrimPermissive]
  def f(
    next: ADDRESS,
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (argument: ARGUMENT)

    def checkAndTrimPermissive(
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      f(next, address)
