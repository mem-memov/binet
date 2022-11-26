package net.mem_memov.binet.memory.specific.inventory.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker.{CheckBoundaryRestrictively, CheckBoundaryPermissively}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer.Trim

class Argument

object Argument:

  given [ADDRESS, CHECKER, TRIMMER](using
    => CheckBoundaryPermissively[CHECKER, ADDRESS],
    => Trim[TRIMMER, ADDRESS]
  )(using
    checker: CHECKER,
    trimmer: TRIMMER
  ): CheckAndTrimPermissive[Argument, ADDRESS] with

    override
    def f(
      argument: Argument,
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      for {
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryPermissively(next, trimmedAddress)
      } yield trimmedAddress

  given [ADDRESS, CHECKER, TRIMMER](using
    => CheckBoundaryRestrictively[CHECKER, ADDRESS],
    => Trim[TRIMMER, ADDRESS]
  )(using
    checker: CHECKER,
    trimmer: TRIMMER
  ): CheckAndTrimRestrictive[Argument, ADDRESS] with

    override
    def f(
      argument: Argument,
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, ADDRESS] =

      for {
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryRestrictively(next, trimmedAddress)
      } yield trimmedAddress


