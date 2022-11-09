package net.mem_memov.binet.memory.tree.treeFactory.traversalFactory

import net.mem_memov.binet.memory.{Address, Element, Traversal}
import net.mem_memov.binet.memory.tree.{TreeAddress, TreeTraversal}
import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, TraversalFactory}

object FactoryOfTraversals:

  given traversalFactory(using
    addressFactory: AddressFactory
  ): TraversalFactory[TreeAddress] =

    new TraversalFactory:

      override
      def createFirst(
        element: Element,
        newPath: TreeAddress
      ): Traversal =

        TreeTraversal(element, addressFactory.zeroAddress, newPath)
