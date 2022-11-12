package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeElement._
import net.mem_memov.binet.memory.tree.treeFactory.*

class TreeElementSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given ElementFactory = new UnusedElementFactory(failMethod) {}
  given StockFactory = new UnusedStockFactory(failMethod) {}
  given StoreFactory = new UnusedStoreFactory(failMethod) {}

  val writer: Writer = new UnusedWriter(failMethod) {}
  val reader: Reader = new UnusedReader(failMethod) {}

  test("Element writes address to a new store") {

    val destinationIndex = UnsignedByte.fromInt(0)

    val restPath = new UnusedPath(failMethod):
      override def isEmpty = true

    val destinationPath = new UnusedPath(failMethod):
      override def shorten = Right(Path.Split(destinationIndex, restPath))

    val updatedStore = new UnusedStore(failMethod) {}

    val writtenContent = new UnusedContent(failMethod) {}

    val writer: Writer = new UnusedWriter(failMethod):
      override def writeStore(storeOption: Option[Store], pathSplit: Path.Split, content: Content): Store =
        assert(storeOption.isEmpty)
        assert(pathSplit.index == destinationIndex && pathSplit.rest == restPath)
        assert(content == writtenContent)
        updatedStore

    val element = TreeElement(None, None, writer, reader)

    for {
      result <- element.write(destinationPath, writtenContent)
    } yield assert(result.storeOption.contains(updatedStore))
  }

  test("Element writes content to the available store") {

    val destinationIndex = UnsignedByte.fromInt(0)

    val restPath = new UnusedPath(failMethod):
      override def isEmpty = true

    val destinationPath = new UnusedPath(failMethod):
      override def shorten = Right(Path.Split(destinationIndex, restPath))

    val updatedStore = new UnusedStore(failMethod) {}

    val writtenContent = new UnusedContent(failMethod) {}

    val store = new UnusedStore(failMethod) {}

    val writer: Writer = new UnusedWriter(failMethod):
      override def writeStore(storeOption: Option[Store], pathSplit: Path.Split, content: Content): Store =
        assert(storeOption.contains(store))
        assert(pathSplit.index == destinationIndex && pathSplit.rest == restPath)
        assert(content == writtenContent)
        updatedStore

    val element = TreeElement(Some(store), None, writer, reader)

    for {
      result <- element.write(destinationPath, writtenContent)
    } yield assert(result.storeOption.contains(updatedStore))
  }

  test("Element doesn't write content if destination empty") {

    val destinationPath = new UnusedPath(failMethod):
      override def shorten = Left("Path couldn't be used")

    val writtenContent = new UnusedContent(failMethod) {}

    val element = TreeElement(None, None, writer, reader)

    val result = element.write(destinationPath, writtenContent)

    assert(result == Left("Path couldn't be used"))
  }

  test("Element writes content to a new stock if destination not reached") {

    val writtenContent = new UnusedContent(failMethod) {}

    val destinationIndex = UnsignedByte.fromInt(0)

    val restPath = new UnusedPath(failMethod):
      override def isEmpty = false

    val destinationPath = new UnusedPath(failMethod):
      override def shorten = Right(Path.Split(destinationIndex, restPath))

    val updatedStock = new UnusedStock(failMethod) {}

    val writer: Writer = new UnusedWriter(failMethod):
      override def writeStock(stockOption: Option[Stock], pathSplit: Path.Split, content: Content): Either[String, Stock] =
        assert(stockOption.isEmpty)
        assert(pathSplit.index == destinationIndex && pathSplit.rest == restPath)
        assert(content == writtenContent)
        Right(updatedStock)

    val element = TreeElement(None, None, writer, reader)

    for {
      result <- element.write(destinationPath, writtenContent)
    } yield assert(result.stockOption.contains(updatedStock))
  }

  test("Element writes content to the available stock if destination not reached") {

    val writtenContent = new UnusedContent(failMethod) {}

    val destinationIndex = UnsignedByte.fromInt(0)

    val restPath = new UnusedPath(failMethod):
      override def isEmpty = false

    val destinationPath = new UnusedPath(failMethod):
      override def shorten = Right(Path.Split(destinationIndex, restPath))

    val updatedStock = new UnusedStock(failMethod) {}
    val stock = new UnusedStock(failMethod) {}

    val writer: Writer = new UnusedWriter(failMethod):
      override def writeStock(stockOption: Option[Stock], pathSplit: Path.Split, content: Content): Either[String, Stock] =
        assert(stockOption.contains(stock))
        assert(pathSplit.index == destinationIndex && pathSplit.rest == restPath)
        assert(content == writtenContent)
        Right(updatedStock)

    val element = TreeElement(None, Some(stock), writer, reader)

    for {
      result <- element.write(destinationPath, writtenContent)
    } yield assert(result.stockOption.contains(updatedStock))
  }

//  test("Element reads content from a new store") {
//
//    val contentAddress = new UnusedAddress(failMethod) {}
//
//    val originIndex = UnsignedByte.fromInt(0)
//
//    val restAddress = new UnusedAddress(failMethod):
//      override def isEmpty = true
//
//    val originAddress = new UnusedAddress(failMethod):
//      override def shorten = Some(originIndex -> restAddress)
//
//    val store = new UnusedStore(failMethod):
//      override def read(origin: UnsignedByte): Address =
//        assert(origin == originIndex)
//        contentAddress
//
//    given StoreFactory = new UnusedStoreFactory(failMethod):
//      override lazy val emptyStore: Store =
//        store
//
//    val element = TreeElement(None, None, writer, reader)
//
//    for {
//      result <- element.read(originAddress)
//    } yield assert(result.equals(contentAddress))
//  }
//
//  test("Element reads content from the available store") {
//
//    val contentAddress = new UnusedAddress(failMethod) {}
//
//    val originIndex = UnsignedByte.fromInt(0)
//
//    val restAddress = new UnusedAddress(failMethod):
//      override def isEmpty = true
//
//    val originAddress = new UnusedAddress(failMethod):
//      override def shorten = Some(originIndex -> restAddress)
//
//    val store = new UnusedStore(failMethod):
//      override def read(origin: UnsignedByte): Address =
//        assert(origin == originIndex)
//        contentAddress
//
//    val element = TreeElement(Some(store), None, writer, reader)
//
//    for {
//      result <- element.read(originAddress)
//    } yield assert(result.equals(contentAddress))
//  }
//
//  test("Element reads content from a new stock") {
//
//    val contentAddress = new UnusedAddress(failMethod) {}
//
//    val originIndex = UnsignedByte.fromInt(0)
//
//    val restAddress = new UnusedAddress(failMethod):
//      override def isEmpty = false
//
//    val originAddress = new UnusedAddress(failMethod):
//      override def shorten = Some(originIndex -> restAddress)
//
//    val stock = new UnusedStock(failMethod):
//      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
//        Right(contentAddress)
//
//    given StockFactory = new UnusedStockFactory(failMethod):
//      override def makeStock()(using elementFactory: ElementFactory): Stock =
//        stock
//
//    val element = TreeElement(None, None, writer, reader)
//
//    for {
//      result <- element.read(originAddress)
//    } yield assert(result.equals(contentAddress))
//  }
//
//  test("Element reads content from a new stock") {
//
//    val contentAddress = new UnusedAddress(failMethod) {}
//
//    val originIndex = UnsignedByte.fromInt(0)
//
//    val restAddress = new UnusedAddress(failMethod):
//      override def isEmpty = false
//
//    val originAddress = new UnusedAddress(failMethod):
//      override def shorten = Some(originIndex -> restAddress)
//
//    val stock = new UnusedStock(failMethod):
//      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
//        Right(contentAddress)
//
//    given StockFactory = new UnusedStockFactory(failMethod):
//      override def makeStock()(using elementFactory: ElementFactory): Stock =
//        stock
//
//    val element = TreeElement(None, None, writer, reader)
//
//    for {
//      result <- element.read(originAddress)
//    } yield assert(result.equals(contentAddress))
//  }
//
//  test("Element reads content from the available stock") {
//
//    val contentAddress = new UnusedAddress(failMethod) {}
//
//    val originIndex = UnsignedByte.fromInt(0)
//
//    val restAddress = new UnusedAddress(failMethod):
//      override def isEmpty = false
//
//    val originAddress = new UnusedAddress(failMethod):
//      override def shorten = Some(originIndex -> restAddress)
//
//    val stock = new UnusedStock(failMethod):
//      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
//        Right(contentAddress)
//
//    val element = TreeElement(None, Some(stock), writer, reader)
//
//    for {
//      result <- element.read(originAddress)
//    } yield assert(result.equals(contentAddress))
//  }
//
//  test("Element doesn't read content if origin empty") {
//
//    val originAddress = new UnusedAddress(failMethod):
//      private[memory]
//      override def shorten = None
//
//    val element = TreeElement(None, None, writer, reader)
//
//    val result = element.read(originAddress)
//
//    assert(result == Left("Origin not read"))
//  }