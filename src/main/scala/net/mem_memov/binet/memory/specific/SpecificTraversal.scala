package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class SpecificTraversal(
  root: SpecificElement,
  nextPath: SpecificAddress,
  newPath: SpecificAddress
)

object SpecificTraversal:

  given (using
    Ordering[SpecificAddress],
    general.element.Read[SpecificElement, SpecificPath, SpecificContent],
    general.address.ToPath[SpecificAddress, SpecificPath],
    general.address.Increment[SpecificAddress]
  ): general.traversal.Next[SpecificTraversal, SpecificAddress] with

    override
    def nextTraversalStep(
      traversal: SpecificTraversal
    ): Either[String, Option[(SpecificAddress, SpecificTraversal)]] =

      if traversal.nextPath == traversal.newPath then

        Right(None)

      else

        for {
          content <- traversal.root.read(traversal.nextPath.toPath)
        } yield Some((content.toAddress, traversal.copy(nextPath = traversal.nextPath.increment)))
