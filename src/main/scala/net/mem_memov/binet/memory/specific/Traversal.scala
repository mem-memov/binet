package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Traversal(
  root: Element,
  nextPath: Address,
  newPath: Address
)

object Traversal:

  given [CONTENT, PATH](using
    Ordering[Address],
    general.element.Read[Element, PATH, CONTENT],
    general.address.ToPath[Address, PATH],
    general.address.Increment[Address],
    general.content.ToAddress[CONTENT, Address]
  ): general.traversal.Next[Traversal, Address] with

    override
    def f(
      traversal: Traversal
    ): Either[String, Option[(Address, Traversal)]] =

      if traversal.nextPath == traversal.newPath then

        Right(None)

      else

        for {
          content <- traversal.root.read(traversal.nextPath.toPath)
        } yield Some((content.toAddress, traversal.copy(nextPath = traversal.nextPath.increment)))
