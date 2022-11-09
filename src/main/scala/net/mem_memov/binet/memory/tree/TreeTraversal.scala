package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._

case class TreeTraversal(
  root: Element,
  nextPath: Address,
  newPath: Address
)

object TreeTraversal:

  given Traversal[TreeTraversal] with

    override
    def nextTraversal(
      traversal: TreeTraversal
    ): Either[String, Option[(Address, TreeTraversal)]] =

      if traversal.nextPath.isEqual(traversal.newPath) then

        Right(None)

      else

        for {
          content <- traversal.root.read(traversal.nextPath.toPath)
        } yield Some((content.toAddress, traversal.copy(nextPath = traversal.nextPath.increment)))

      
