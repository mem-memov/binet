package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class SpecificChecker

object SpecificChecker:

  given (using
    Ordering[Address]
  ): CheckBoundaryPermissively[SpecificChecker, Address] with

    override
    def checkAddressBoundaryPermissively(
      checker: SpecificChecker,
      next: Address,
      address: Address
    ): Either[String, Unit] =

      if next <= address && next != Address.zeroAddress then
        Left("Address out of permissive boundary")
      else
        Right(())

  given (using
    Ordering[Address]
  ): CheckBoundaryRestrictively[SpecificChecker, Address] with

    override
    def checkAddressBoundaryRestrictively(
      checker: SpecificChecker,
      next: Address,
      address: Address
    ): Either[String, Unit] =

      if next <= address then
        Left("Address out of restrictive boundary")
      else
        Right(())
