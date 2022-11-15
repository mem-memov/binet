package net.mem_memov.binet.memory.specific.specificInventory.specific

import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.specificInventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker.{CheckType, CheckBoundaryRestrictively, CheckBoundaryPermissively}
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific.SpecificChecker
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer.Trim

class SpecificArgument

object SpecificArgument:

  given [
    CHECKER : CheckType : CheckBoundaryPermissively,
    TRIMMER : Trim
  ](using
    checker: CHECKER,
    trimmer: TRIMMER
  ): CheckAndTrimPermissive[SpecificArgument] with

    override
    def checkAndTrimAddressPermissive[
      ADDRESS
    ](
      argument: SpecificArgument,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, SpecificAddress] =

      for {
        _ <- checker.checkType(next, address)
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryPermissively(next, trimmedAddress)
      } yield trimmedAddress

  given [
    CHECKER : CheckType : CheckBoundaryRestrictively,
    TRIMMER : Trim
  ](using
    checker: CHECKER,
    trimmer: TRIMMER
  ): CheckAndTrimRestrictive[SpecificArgument] with

    override
    def checkAndTrimAddressRestrictive[
      ADDRESS
    ](
      argument: SpecificArgument,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, SpecificAddress] =

      for {
        _ <- checker.checkType(next, address)
        trimmedAddress <- Right(trimmer.trim(address))
        _ <- checker.checkBoundaryRestrictively(next, trimmedAddress)
      } yield trimmedAddress


