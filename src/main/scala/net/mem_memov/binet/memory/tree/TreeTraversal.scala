package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._

case class TreeTraversal(root: Element, nextPath: Address, newPath: Address) extends Traversal:

  override 
  def next: Either[String, Option[(Address, Traversal)]] =

    if nextPath.isEqual(newPath) then
      
      Right(None)

    else

      for {
        content <- root.read(nextPath)
      } yield Some((content, this.copy(nextPath = nextPath.increment)))

      
