package net.mem_memov.binet.memory.tree.treeElement

import net.mem_memov.binet.memory._

trait Writer:

  def writeStore(
    storeOption: Option[Store],
    pathSplit: Path.Split,
    content: Content
  ): Store

  def writeStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split,
    content: Content
  ): Either[String, Stock]
