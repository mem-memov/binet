package net.mem_memov.binet.memory.specific.specificInventory.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.specificInventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker.{CheckType, CheckBoundaryRestrictively, CheckBoundaryPermissively}
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific.SpecificChecker
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer.Trim

class SpecificArgument

object SpecificArgument:

  given [CHECKER, TRIMMER](using
    checker: CHECKER,
    trimmer: TRIMMER
  )(using
    CheckType[CHECKER, Address],
    CheckBoundaryPermissively[CHECKER, Address],
    Trim[TRIMMER, Address]
  ): CheckAndTrimPermissive[SpecificArgument, Address] with

    override
    def checkAndTrimAddressPermissive(
      argument: SpecificArgument,
      next: Address,
      address: Address
    ): Either[String, Address] =

      for {
        _ <- checker.checkType(next, address)
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryPermissively(next, trimmedAddress)
      } yield trimmedAddress

  given [CHECKER, TRIMMER](using
    checker: CHECKER,
    trimmer: TRIMMER
  )(using
    CheckType[CHECKER, Address],
    CheckBoundaryRestrictively[CHECKER, Address],
    Trim[TRIMMER, Address]
  ): CheckAndTrimRestrictive[SpecificArgument, Address] with

    override
    def checkAndTrimAddressRestrictive(
      argument: SpecificArgument,
      next: Address,
      address: Address
    ): Either[String, Address] =

      for {
        _ <- checker.checkType(next, address)
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryRestrictively(next, trimmedAddress)
      } yield trimmedAddress


