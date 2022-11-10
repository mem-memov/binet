package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeFactory.*

class TreeAddressIntegrationSuite extends munit.FunSuite:

  given BlockFactory = BlockFactory()
  given ContentFactory = ContentFactory()
  given PathFactory = PathFactory()
  given factory: AddressFactory = AddressFactory()

  val low = UnsignedByte.minimum
  val high = UnsignedByte.maximum

  test("Address gets incremented") {
    List(
      (
        factory.makeAddress(List(UnsignedByte.minimum)),
        factory.makeAddress(List(UnsignedByte.fromInt(1)))
      ),
      (
        factory.makeAddress(List(UnsignedByte.fromInt(254))),
        factory.makeAddress(List(UnsignedByte.fromInt(255)))
      ),
      (
        factory.makeAddress(List(UnsignedByte.fromInt(255))),
        factory.makeAddress(List(UnsignedByte.fromInt(1), UnsignedByte.minimum))
      ),
      (
        factory.makeAddress(List(UnsignedByte.fromInt(255), UnsignedByte.fromInt(255))),
        factory.makeAddress(List(UnsignedByte.fromInt(1), UnsignedByte.minimum, UnsignedByte.minimum))
      ),
    ).foreach{ case(original, expected) =>
      assert(
        original.increment == expected
      )
    }
  }

  test("Address provides its length") {
    (0 to 255)
      .map(n => List.empty[UnsignedByte]
      .padTo(n * 100, UnsignedByte.fromInt(n)))
      .foreach { indices =>
          assert(
            factory.makeAddress(indices).length == (indices.length)
          )
        }
  }

  test("Create another address with some zero bytes at the head") {

    List(
      (
        factory.makeAddress(List.empty),
        2,
        factory.makeAddress(List(low, low)),
        Option.empty[String]
      ),
      (
        factory.makeAddress(List(low)),
        3,
        factory.makeAddress(List(low, low ,low)),
        Option.empty[String]
      ),
      (
        factory.makeAddress(List(high)),
        1,
        factory.makeAddress(List(high)),
        Option.empty[String]
      ),
      (
        factory.makeAddress(List(high)),
        3,
        factory.makeAddress(List(low, low, high)),
        Option.empty[String]
      ),
      (
        factory.makeAddress(List(high, low)),
        3,
        factory.makeAddress(List(low, low, high, low)),
        Option.empty[String]
      ),
      (
        factory.makeAddress(List(high, high, high)),
        2,
        factory.makeAddress(List.empty),
        Option("Address not padded: already too long")
      )
    ).foreach { case (original, target, expected, failure) =>

      failure match
        case None =>
          original.padBig(target).map { padded =>
            assert(padded.isEqual(expected))
            assert(failure.isEmpty)
          }
        case Some(message) =>
          original.padBig(target) match {
            case Left(result) => assert(result == message)
            case Right(_) => assert(false)
          }
    }
  }

  test("Create another address without zero bytes at the head") {

    List(
      (
        factory.makeAddress(List()),
        factory.makeAddress(List())
      ),
      (
        factory.makeAddress(List(low, low, low, low)),
        factory.makeAddress(List(low))
      ),
      (
        factory.makeAddress(List(high)),
        factory.makeAddress(List(high))
      ),
      (
        factory.makeAddress(List(low, high)),
        factory.makeAddress(List(high))
      ),
      (
        factory.makeAddress(List(low, low, low, high)),
        factory.makeAddress(List(high))
      ),
      (
        factory.makeAddress(List(low, high, low)),
        factory.makeAddress(List(high, low))
      ),
      (
        factory.makeAddress(List(high, low, low)),
        factory.makeAddress(List(high, low, low))
      ),
      (
        factory.makeAddress(List()),
        factory.makeAddress(List())
      ),
      (
        factory.makeAddress(List(low, low, low)),
        factory.makeAddress(List())
      ),
    ).foreach { case (original, expected) =>
      assert(
        original.trimBig.isEqual(expected)
      )
    }
  }
