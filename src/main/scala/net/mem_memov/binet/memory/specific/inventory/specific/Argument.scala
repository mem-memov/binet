package net.mem_memov.binet.memory.specific.inventory.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker.{CheckBoundaryRestrictively, CheckBoundaryPermissively}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer.Trim

class Argument

object Argument:

  given [CHECKER, TRIMMER](using
    checker: CHECKER,
    trimmer: TRIMMER
  )(using
    CheckBoundaryPermissively[CHECKER, Address],
    Trim[TRIMMER, Address]
  ): CheckAndTrimPermissive[Argument, Address] with

    override
    def f(
      argument: Argument,
      next: Address,
      address: Address
    ): Either[String, Address] =

      for {
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryPermissively(next, trimmedAddress)
      } yield trimmedAddress

  given [CHECKER, TRIMMER](using
    checker: CHECKER,
    trimmer: TRIMMER
  )(using
    CheckBoundaryRestrictively[CHECKER, Address],
    Trim[TRIMMER, Address]
  ): CheckAndTrimRestrictive[Argument, Address] with

    override
    def f(
      argument: Argument,
      next: Address,
      address: Address
    ): Either[String, Address] =

      for {
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryRestrictively(next, trimmedAddress)
      } yield trimmedAddress


