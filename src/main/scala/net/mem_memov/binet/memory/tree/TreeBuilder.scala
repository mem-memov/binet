package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

import scala.collection.immutable.Queue

case class TreeBuilder(
  storeOption: Option[Store],
  rootOption: Option[Element],
  next: UnsignedByte,
  queue: Queue[Element]
)(using
  elementFactory: ElementFactory,
  stockFactory: StockFactory,
  storeFactory: StoreFactory
) extends Builder:

  override
  def addSlice(
    slice: Array[Byte]
  ): Builder =

    val store = storeOption.getOrElse(storeFactory.emptyStore)
    val indices = slice.toVector.map(UnsignedByte(_))
    val modifiedStore = store.write(next, indices)

    if rootOption.isEmpty then
      val stock = stockFactory.makeStock()
      val modifiedQueue = stock.enqueueElements(queue)
      val element = elementFactory.create(store, stock)
      this.copy(
        storeOption = None,
        rootOption = Some(element),
        next = UnsignedByte.minimum,
        queue = modifiedQueue
      )
    else
      if next == UnsignedByte.maximum then
        if queue.isEmpty then
          this
        else
          val (element, shorterQueue) = queue.dequeue
          val modifiedElement = element.withStore(store)
          val longerQueue = modifiedElement.enqueueStock(shorterQueue)
          this.copy(
            storeOption = None,
            next = UnsignedByte.minimum,
            queue = longerQueue
          )
      else
        this.copy(
          storeOption = Some(modifiedStore),
          next = next.increment
        )

    
