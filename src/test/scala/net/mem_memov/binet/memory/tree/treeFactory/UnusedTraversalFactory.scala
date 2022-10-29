package net.mem_memov.binet.memory.tree.treeFactory

import net.mem_memov.binet.memory._

trait UnusedTraversalFactory(fail: String => Nothing) extends TraversalFactory:

  override
  def createFirst(element: Element): Traversal =

    fail("unexpected")
