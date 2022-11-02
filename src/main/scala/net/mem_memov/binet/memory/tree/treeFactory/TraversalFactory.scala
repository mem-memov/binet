package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.TreeTraversal

trait TraversalFactory:

  def createFirst(
    element: Element, 
    newPath: Address
  ): Traversal

object TraversalFactory:

  def apply()(using 
    addressFactory: AddressFactory
  ): TraversalFactory =

    new TraversalFactory:

      override
      def createFirst(
        element: Element, 
        newPath: Address
      ): Traversal =

        TreeTraversal(element, addressFactory.zeroAddress, newPath)
