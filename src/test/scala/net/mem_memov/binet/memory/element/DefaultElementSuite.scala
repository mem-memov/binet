package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

class DefaultElementSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given ElementFactory = new UnusedElementFactory(failMethod) {}
  given StockFactory = new UnusedStockFactory(failMethod) {}
  given StoreFactory = new UnusedStoreFactory(failMethod) {}

  test("Element writes address to store") {

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

    given StoreFactory = new UnusedStoreFactory(failMethod):
      override lazy val emptyStore: Store = store

    val element = DefaultElement(None, None)

    for {
      result <- element.write(destinationAddress, contentAddress)
    } yield assert(result.storeOption.contains(updatedStore))
  }
