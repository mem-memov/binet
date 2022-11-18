package net.mem_memov.binet.memory.specific.element.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.general.reader.{ReadStock, ReadStore}

class Reader

object Reader:

  given (using
    general.stock.Read[specific.Stock, specific.Content, specific.Path]
  ): ReadStock[
    Reader,
    specific.Content,
    general.Split[specific.Path],
    specific.Stock
  ] with

    override
    def f(
      reader: Reader,
      stockOption: Option[specific.Stock],
      pathSplit: general.Split[specific.Path]
    ): Either[String, specific.Content] =

      val presentStock = stockOption.getOrElse(specific.Stock.emptyStock())
      presentStock.read(pathSplit.index, pathSplit.rest)

  given [CONTENT](using
    general.store.Read[specific.Store, specific.Address],
    general.address.ToContent[specific.Address, CONTENT]
  ): ReadStore[
    Reader,
    CONTENT,
    general.Split[specific.Path],
    specific.Store
  ] with

    override
    def f(
      reader: Reader,
      storeOption: Option[specific.Store],
      pathSplit: general.Split[specific.Path]
    ): CONTENT =

      val presentStore = storeOption.getOrElse(specific.Store.emptyStore)
      presentStore.read(pathSplit.index).toContent
