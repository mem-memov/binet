package net.mem_memov.binet.memoryOld.tree.treeElement.service

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeElement.Writer
import net.mem_memov.binet.memory.tree.treeFactory._

class WriterService()(using
  elementFactory: ElementFactory,
  storeFactory: StoreFactory,
  stockFactory: StockFactory
) extends Writer:

  override
  def writeStore(
    storeOption: Option[Store],
    pathSplit: Path.Split,
    content: Content
  ): Store =

    val presentStore = storeOption.getOrElse(storeFactory.emptyStore)
    presentStore.write(pathSplit.index, content)

  override
  def writeStock(
    stockOption: Option[Stock],
    pathSplit: Path.Split,
    content: Content
  ): Either[String, Stock] =

    val presentStock = stockOption.getOrElse(stockFactory.makeStock())
    presentStock.write(pathSplit.index, pathSplit.rest, content)