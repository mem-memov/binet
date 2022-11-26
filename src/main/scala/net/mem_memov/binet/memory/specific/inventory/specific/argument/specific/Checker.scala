package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.checker.{CheckBoundaryPermissively, CheckBoundaryRestrictively}
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class Checker

object Checker:

  given [ADDRESS, FACTORY](using
    => Ordering[ADDRESS],
    => general.factory.ZeroAddress[FACTORY, ADDRESS]
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

  given [ADDRESS](using
    => Ordering[ADDRESS]
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
