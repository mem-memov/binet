package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class SpecificTraversal(
  root: Element,
  nextPath: Address,
  newPath: Address
)

object SpecificTraversal:

  given (using
    Ordering[Address],
    general.element.Read[Element, Path, Content],
    general.address.ToPath[Address, Path],
    general.address.Increment[Address]
  ): general.traversal.Next[SpecificTraversal, Address] with

    override
    def nextTraversalStep(
      traversal: SpecificTraversal
    ): Either[String, Option[(Address, SpecificTraversal)]] =

      if traversal.nextPath == traversal.newPath then

        Right(None)

      else

        for {
          content <- traversal.root.read(traversal.nextPath.toPath)
        } yield Some((content.toAddress, traversal.copy(nextPath = traversal.nextPath.increment)))
