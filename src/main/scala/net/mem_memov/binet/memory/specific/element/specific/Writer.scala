package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.Split
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.general.writer.{WriteStock, WriteStore}

class Writer

object Writer:

  given net_mem_memov_binet_memory_specific_element_specific_Writer_WriteStock[CONTENT, FACTORY, PATH, STOCK](using
    general.factory.EmptyStock[FACTORY, STOCK],
    general.stock.Write[STOCK, CONTENT, PATH]
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

  given net_mem_memov_binet_memory_specific_element_specific_Writer_WriteStore[CONTENT, FACTORY, PATH, STORE](using
    general.factory.EmptyStore[FACTORY, STORE],
    general.store.Write[STORE, CONTENT]
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




