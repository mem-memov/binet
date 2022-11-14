package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import net.mem_memov.binet.memory.general.address.AddressLessThanOrEqualChecker
import net.mem_memov.binet.memory.general.address.AddressEqualToChecker

class SpecificChecker

object SpecificChecker:

  given (using
    AddressLessThanOrEqualChecker[SpecificAddress],
    AddressEqualToChecker[SpecificAddress]
  ): CheckBoundaryPermissively[SpecificChecker, SpecificAddress] with

    override
    def checkAddressBoundaryPermissively(
      checker: SpecificChecker,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, Unit] =

      if next.isLessOrEqual(address) && !next.isEqual(SpecificAddress.zeroAddress) then
        Left("Address out of permissive boundary")
      else
        Right(())

  given (using
    AddressLessThanOrEqualChecker[SpecificAddress]
  ): CheckBoundaryRestrictively[SpecificChecker, SpecificAddress] with

    override
    def checkAddressBoundaryRestrictively(
      checker: SpecificChecker,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, Unit] =

      if next.isLessOrEqual(address) then
        Left("Address out of restrictive boundary")
      else
        Right(())
