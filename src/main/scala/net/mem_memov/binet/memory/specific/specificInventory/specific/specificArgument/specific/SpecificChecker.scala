package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class SpecificChecker

object SpecificChecker:

  given (using
    Ordering[SpecificAddress]
  ): CheckBoundaryPermissively[SpecificChecker, SpecificAddress] with

    override
    def checkAddressBoundaryPermissively(
      checker: SpecificChecker,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, Unit] =

      if next <= address && next != SpecificAddress.zeroAddress then
        Left("Address out of permissive boundary")
      else
        Right(())

  given (using
    Ordering[SpecificAddress]
  ): CheckBoundaryRestrictively[SpecificChecker, SpecificAddress] with

    override
    def checkAddressBoundaryRestrictively(
      checker: SpecificChecker,
      next: SpecificAddress,
      address: SpecificAddress
    ): Either[String, Unit] =

      if next <= address then
        Left("Address out of restrictive boundary")
      else
        Right(())
