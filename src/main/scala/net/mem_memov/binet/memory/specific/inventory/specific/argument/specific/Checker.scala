package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class Checker

object Checker:

  given (using
    Ordering[Address]
  ): CheckBoundaryPermissively[Checker, Address] with

    override
    def checkAddressBoundaryPermissively(
      checker: Checker,
      next: Address,
      address: Address
    ): Either[String, Unit] =

      if next <= address && next != Address.zeroAddress then
        Left("Address out of permissive boundary")
      else
        Right(())

  given (using
    Ordering[Address]
  ): CheckBoundaryRestrictively[Checker, Address] with

    override
    def checkAddressBoundaryRestrictively(
      checker: Checker,
      next: Address,
      address: Address
    ): Either[String, Unit] =

      if next <= address then
        Left("Address out of restrictive boundary")
      else
        Right(())
