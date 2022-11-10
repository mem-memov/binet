package net.mem_memov.binet.memoryOld.tree.treeElement.service

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeElement.*
import net.mem_memov.binet.memory.tree.treeFactory._

class ReaderService()(using
  elementFactory: ElementFactory,
  storeFactory: StoreFactory,
  stockFactory: StockFactory
) extends Reader:

  override
  def readStore(
    storeOption: Option[Store],
    pathSplit: Path.Split
  ): Either[String, Content] =

    val presentStore = storeOption.getOrElse(storeFactory.emptyStore)
    Right(presentStore.read(pathSplit.index).toContent)

  override
  def readStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split
  ): Either[String, Content] =

    val presentStock = stockOption.getOrElse(stockFactory.makeStock())
    presentStock.read(pathSplit.index, pathSplit.rest)
