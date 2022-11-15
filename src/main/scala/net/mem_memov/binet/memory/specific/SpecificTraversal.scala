package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.traversal.NextTraversal
import net.mem_memov.binet.memory.general.address.{AddressToPathConverter, AddressIncrementer}
import net.mem_memov.binet.memory.general.element.ElementReader

case class SpecificTraversal(
  root: SpecificElement,
  nextPath: SpecificAddress,
  newPath: SpecificAddress
)

object SpecificTraversal:

  given (using
    ElementReader[SpecificElement]
  ): NextTraversal[SpecificTraversal] with

    override
    def nextTraversalStep[
      ADDRESS : Ordering : AddressToPathConverter : AddressIncrementer
    ](
      traversal: SpecificTraversal
    ): Either[String, Option[(SpecificAddress, SpecificTraversal)]] =

      if traversal.nextPath == traversal.newPath then

        Right(None)

      else

        for {
          content <- traversal.root.read(traversal.nextPath.toPath)
        } yield Some((content.toAddress, traversal.copy(nextPath = traversal.nextPath.increment)))
