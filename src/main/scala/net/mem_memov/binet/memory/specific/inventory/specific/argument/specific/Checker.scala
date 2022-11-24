package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class Checker

object Checker:

  given net_mem_memov_binet_memory_specific_inventory_specific_argument_specific_Checker_CheckBoundaryPermissively[ADDRESS, FACTORY](using
    Ordering[ADDRESS],
    general.factory.ZeroAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): CheckBoundaryPermissively[Checker, ADDRESS] with

    override
    def f(
      checker: Checker,
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      if next <= address && next != factory.zeroAddress() then
        Left("Address out of permissive boundary")
      else
        Right(())

  given net_mem_memov_binet_memory_specific_inventory_specific_argument_specific_Checker_CheckBoundaryRestrictively[ADDRESS](using
    Ordering[ADDRESS]
  ): CheckBoundaryRestrictively[Checker, ADDRESS] with

    override
    def f(
      checker: Checker,
      next: ADDRESS,
      address: ADDRESS
    ): Either[String, Unit] =

      if next <= address then
        Left("Address out of restrictive boundary")
      else
        Right(())
