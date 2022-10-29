package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.TreeTraversal

import scala.collection.immutable.Queue

trait TraversalFactory:

  def createFirst(element: Element): Traversal

object TraversalFactory:

  def apply(): TraversalFactory =

    new TraversalFactory:

      override
      def createFirst(element: Element): Traversal =

        TreeTraversal(Queue(element))
