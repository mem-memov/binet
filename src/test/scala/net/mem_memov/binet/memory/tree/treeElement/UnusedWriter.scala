package net.mem_memov.binet.memory.tree.treeElement

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeElement.Writer

trait UnusedWriter(fail: String => Nothing) extends Writer:

  override
  def writeStore(
    storeOption: Option[Store],
    pathSplit: Path.Split,
    content: Content
  ): Store =

    fail("unexpected")

  override
  def writeStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split,
    content: Content
  ): Either[String, Stock] =

    fail("unexpected")