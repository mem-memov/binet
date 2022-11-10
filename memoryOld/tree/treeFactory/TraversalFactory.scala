package net.mem_memov.binet.memoryOld.tree.treeFactory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.TreeTraversal

trait TraversalFactory[A]:

  def createFirst(
    element: Element, 
    newPath: A
  ): Traversal
