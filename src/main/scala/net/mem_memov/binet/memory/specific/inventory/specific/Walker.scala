package net.mem_memov.binet.memory.specific.inventory.specific

import net.mem_memov.binet.memory.{general, specific}
import scala.annotation.tailrec
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class Walker

object Walker:

  given [ADDRESS, INVENTORY](using
    => general.address.Increment[ADDRESS],
    => Ordering[ADDRESS],
    => general.inventory.Read[INVENTORY, ADDRESS],
    => general.inventory.Next[INVENTORY, ADDRESS]
  )(using
    inventory: INVENTORY
  ): specific.inventory.general.walker.Travel[Walker, ADDRESS] with

    @tailrec
    override
    final // enabling tail-call optimisation
    def f[RESULT](
      walker: Walker,
      result: RESULT,
      origin: ADDRESS,
      process: (RESULT, general.Item[ADDRESS]) => RESULT
    ): Either[String, RESULT] =

      if origin equiv inventory.next() then
        Right(result)
      else
        val contentEither = inventory.read(origin)
        contentEither match
          case Left(error) =>
            Left(error)
          case Right(content) =>
            val newResult = process(
              result,
              general.Item(
                path = origin,
                content = content
              )
            )
            f(walker, newResult, origin.increment(), process)
