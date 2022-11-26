package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.Split
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.general.writer.{WriteStock, WriteStore}

class Writer

object Writer:

  given [CONTENT, FACTORY, PATH, STOCK](using
    => general.stock.Write[STOCK, CONTENT, PATH],
    => general.factory.EmptyStock[FACTORY, STOCK]
  )(using
    factory: FACTORY
  ): WriteStock[Writer, CONTENT, PATH, STOCK] with

    override
    def f(
      writer: Writer,
      stockOption: Option[STOCK],
      pathSplit: Split[PATH],
      content: CONTENT
    ): Either[String, STOCK] =

      val presentStock = stockOption.getOrElse(factory.emptyStock())
      presentStock.write(pathSplit.index, pathSplit.rest, content)

  given [CONTENT, FACTORY, PATH, STORE](using
    => general.store.Write[STORE, CONTENT],
    => general.factory.EmptyStore[FACTORY, STORE]
  )(using
    factory: FACTORY
  ): WriteStore[Writer, CONTENT, PATH, STORE] with

    override
    def f(
      writer: Writer,
      storeOption: Option[STORE],
      pathSplit: Split[PATH],
      content: CONTENT
    ): STORE =

      val presentStore = storeOption.getOrElse(factory.emptyStore())
      presentStore.write(pathSplit.index, content)




