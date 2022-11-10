package net.mem_memov.binet.memory.tree.treeElement

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeElement.Reader

trait UnusedReader(fail: String => Nothing) extends Reader:

  def readStore(
    storeOption: Option[Store],
    pathSplit: Path.Split
  ): Either[String, Content] =

    fail("unexpected")

  def readStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split
  ): Either[String, Content] =

    fail("unexpected")
