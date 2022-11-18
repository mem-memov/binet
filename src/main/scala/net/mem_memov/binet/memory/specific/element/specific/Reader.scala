package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.general.reader.{ReadStock, ReadStore}

class Reader

object Reader:

  given [CONTENT, FACTORY, PATH, STOCK](using
    general.factory.EmptyStock[FACTORY, STOCK],
    general.stock.Read[STOCK, CONTENT, PATH]
  )(using
    factory: FACTORY
  ): ReadStock[Reader, CONTENT, PATH, STOCK] with

    override
    def f(
      reader: Reader,
      stockOption: Option[STOCK],
      pathSplit: general.Split[PATH]
    ): Either[String, CONTENT] =

      val presentStock = stockOption.getOrElse(factory.emptyStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given [ADDRESS, CONTENT, FACTORY, PATH, STORE](using
    general.factory.EmptyStore[FACTORY, STORE],
    general.store.Read[STORE, ADDRESS],
    general.address.ToContent[ADDRESS, CONTENT]
  )(using
    factory: FACTORY
  ): ReadStore[Reader, CONTENT, PATH, STORE] with

    override
    def f(
      reader: Reader,
      storeOption: Option[STORE],
      pathSplit: general.Split[PATH]
    ): CONTENT =

      val presentStore = storeOption.getOrElse(factory.emptyStore())
      presentStore.read(pathSplit.index).toContent
