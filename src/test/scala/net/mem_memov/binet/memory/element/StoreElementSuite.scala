package net.mem_memov.binet.memory.element

import net.mem_memov.binet.memory.{Address, Level, ShrinkableAddress, Stock, Store, UnsignedByte}

class StoreElementSuite extends munit.FunSuite:

  test("StoreElement writes an address into its store") {

    val level = new Level {
      override def createStore(): Store = ???
      override def createStock(store: Store): Stock = ???
      override def padBig(content: Address): Either[String, Address] = ???
      override def increment(): Level = ???
    }

    val store = new Store {
      override def write(destination: UnsignedByte, content: Address): Either[String, Store] = ???
      override def read(origin: UnsignedByte): Address = ???
      override def enlarge(): Store = ???
    }

    val destination = new ShrinkableAddress {
      override private[memory] def shorten = ???

      override private[memory] def isEmpty = ???
    }

    val destination = new Address {
      override val indices: List[UnsignedByte] = List(UnsignedByte.fromInt(5))
      override lazy val length: Int = indices.length

      override def hasLength(length: Int): Boolean = ???

      override def increment: Address = ???

      override def decrement: Either[String, Address] = ???

      override def isZero: Boolean = ???

      override private[memory] def isEmpty = ???

      override private[memory] def trimBig = ???

      override private[memory] def padBig(target: Int) = ???

      override private[memory] def shorten = Some((indices.head, newList.empty[UnsignedByte]))

      override def zipIndices[A](elements: Vector[A]): Vector[(UnsignedByte, A)] = ???

      override def compare(that: Address): Int = ???
    }
    
    

    val content = new Address {
      override val indices: List[UnsignedByte] = List(UnsignedByte.fromInt(10))
      override lazy val length: Int = indices.length

      override def hasLength(length: Int): Boolean = ???

      override def increment: Address = ???

      override def decrement: Either[String, Address] = ???

      override def isZero: Boolean = ???

      override private[memory] def isEmpty = ???

      override private[memory] def trimBig = ???

      override private[memory] def padBig(target: Int) = ???

      override private[memory] def shorten = ???

      override def zipIndices[A](elements: Vector[A]): Vector[(UnsignedByte, A)] = ???

      override def compare(that: Address): Int = ???
    }

    val storeElement = new StoreElement(level, store)
    storeElement.write(destination, content)
  }