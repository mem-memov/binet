package net.mem_memov.binet.memory.tree.treeElement

import net.mem_memov.binet.memory._

trait Reader:

  def readStore(
    storeOption: Option[Store],
    pathSplit: Path.Split
  ): Either[String, Content]

  def readStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split
  ): Either[String, Content]
