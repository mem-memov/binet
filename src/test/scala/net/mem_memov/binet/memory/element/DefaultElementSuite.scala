package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

class DefaultElementSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given ElementFactory = new UnusedElementFactory(failMethod) {}
  given StockFactory = new UnusedStockFactory(failMethod) {}
  given StoreFactory = new UnusedStoreFactory(failMethod) {}

  test("Element writes address to a new store") {

    val destinationIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = true

    val destinationAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(destinationIndex -> restAddress)

    val updatedStore = new UnusedStore(failMethod) {}

    val expandedStore = new UnusedStore(failMethod):
      override def write(destination: UnsignedByte, content: Address): Either[String, Store] =
        assert(destination == destinationIndex)
        Right(updatedStore)

    val contentAddress = new UnusedAddress(failMethod):
      override def expandStore(store: Store): Store = expandedStore

    val store = new UnusedStore(failMethod) {}

    given StoreFactory = new UnusedStoreFactory(failMethod):
      override lazy val emptyStore: Store = store

    val element = DefaultElement(None, None)

    for {
      result <- element.write(destinationAddress, contentAddress)
    } yield assert(result.storeOption.contains(updatedStore))
  }

  test("Element writes content to the available store") {

    val index = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = true

    val destinationAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(index -> restAddress)

    val updatedStore = new UnusedStore(failMethod) {}

    val expandedStore = new UnusedStore(failMethod):
      override def write(destination: UnsignedByte, content: Address): Either[String, Store] =
        assert(destination == index)
        Right(updatedStore)

    val contentAddress = new UnusedAddress(failMethod):
      override def expandStore(store: Store): Store = expandedStore

    val store = new UnusedStore(failMethod) {}

    val element = DefaultElement(Some(store), None)

    for {
      result <- element.write(destinationAddress, contentAddress)
    } yield assert(result.storeOption.contains(updatedStore))
  }

  test("Element doesn't write content if destination empty") {

    val destinationAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = None

    val contentAddress = new UnusedAddress(failMethod) {}

    val element = DefaultElement(None, None)

    val result = element.write(destinationAddress, contentAddress)

    assert(result == Left("Destination not written"))
  }

  test("Element writes content to a new stock if destination not reached") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val destinationIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false

    val destinationAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(destinationIndex -> restAddress)

    val updatedStock = new UnusedStock(failMethod) {}

    val stock = new UnusedStock(failMethod):
      override def write(index: UnsignedByte, destination: Address, content: Address): Either[String, Stock] =
        assert(index == destinationIndex)
        assert(destination == restAddress)
        assert(content == contentAddress)
        Right(updatedStock)

    given StockFactory = new UnusedStockFactory(failMethod):
      override def makeStock()(using elementFactory: ElementFactory): Stock =
        stock

    val element = DefaultElement(None, None)

    for {
      result <- element.write(destinationAddress, contentAddress)
    } yield assert(result.stockOption.contains(updatedStock))
  }

  test("Element writes content to the available stock if destination not reached") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val destinationIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false

    val destinationAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(destinationIndex -> restAddress)

    val updatedStock = new UnusedStock(failMethod) {}

    val stock = new UnusedStock(failMethod):
      override def write(index: UnsignedByte, destination: Address, content: Address): Either[String, Stock] =
        assert(index == destinationIndex)
        assert(destination == restAddress)
        assert(content == contentAddress)
        Right(updatedStock)

    val element = DefaultElement(None, Some(stock))

    for {
      result <- element.write(destinationAddress, contentAddress)
    } yield assert(result.stockOption.contains(updatedStock))
  }

  test("Element reads content from a new store") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val originIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = true

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(originIndex -> restAddress)

    val store = new UnusedStore(failMethod):
      override def read(origin: UnsignedByte): Address =
        assert(origin == originIndex)
        contentAddress

    given StoreFactory = new UnusedStoreFactory(failMethod):
      override lazy val emptyStore: Store =
        store

    val element = DefaultElement(None, None)

    for {
      result <- element.read(originAddress)
    } yield assert(result.equals(contentAddress))
  }

  test("Element reads content from the available store") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val originIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = true

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(originIndex -> restAddress)

    val store = new UnusedStore(failMethod):
      override def read(origin: UnsignedByte): Address =
        assert(origin == originIndex)
        contentAddress

    val element = DefaultElement(Some(store), None)

    for {
      result <- element.read(originAddress)
    } yield assert(result.equals(contentAddress))
  }

  test("Element reads content from a new stock") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val originIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(originIndex -> restAddress)

    val stock = new UnusedStock(failMethod):
      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
        Right(contentAddress)

    given StockFactory = new UnusedStockFactory(failMethod):
      override def makeStock()(using elementFactory: ElementFactory): Stock =
        stock

    val element = DefaultElement(None, None)

    for {
      result <- element.read(originAddress)
    } yield assert(result.equals(contentAddress))
  }

  test("Element reads content from a new stock") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val originIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(originIndex -> restAddress)

    val stock = new UnusedStock(failMethod):
      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
        Right(contentAddress)

    given StockFactory = new UnusedStockFactory(failMethod):
      override def makeStock()(using elementFactory: ElementFactory): Stock =
        stock

    val element = DefaultElement(None, None)

    for {
      result <- element.read(originAddress)
    } yield assert(result.equals(contentAddress))
  }

  test("Element reads content from the available stock") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val originIndex = UnsignedByte.fromInt(0)

    val restAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = Some(originIndex -> restAddress)

    val stock = new UnusedStock(failMethod):
      override def read(index: UnsignedByte, origin: Address): Either[String, Address] =
        Right(contentAddress)

    val element = DefaultElement(None, Some(stock))

    for {
      result <- element.read(originAddress)
    } yield assert(result.equals(contentAddress))
  }

  test("Element doesn't read content if origin empty") {

    val originAddress = new UnusedAddress(failMethod):
      private[memory]
      override def shorten = None

    val element = DefaultElement(None, None)

    val result = element.read(originAddress)

    assert(result == Left("Origin not read"))
  }