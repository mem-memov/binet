package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.UnsignedByte

class DefaultAddressSuite extends munit.FunSuite:

  test("Address gets incremented") {
    List(
      (
        DefaultAddress(List(UnsignedByte.minimum)),
        DefaultAddress(List(UnsignedByte.fromInt(1)))
      ),
      (
        DefaultAddress(List(UnsignedByte.fromInt(254))),
        DefaultAddress(List(UnsignedByte.fromInt(255)))
      ),
      (
        DefaultAddress(List(UnsignedByte.fromInt(255))),
        DefaultAddress(List(UnsignedByte.fromInt(1), UnsignedByte.minimum))
      ),
      (
        DefaultAddress(List(UnsignedByte.fromInt(255), UnsignedByte.fromInt(255))),
        DefaultAddress(List(UnsignedByte.fromInt(1), UnsignedByte.minimum, UnsignedByte.minimum))
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
            DefaultAddress(indices).length == (indices.length)
          )
        }
  }

  test("Create another address with some zero bytes at the head") {

    val low = UnsignedByte.minimum
    val high = UnsignedByte.maximum

    List(
      (
        DefaultAddress(List.empty),
        2,
        DefaultAddress(List(low, low)),
        Option.empty[String]
      ),
      (
        DefaultAddress(List(low)),
        3,
        DefaultAddress(List(low, low ,low)),
        Option.empty[String]
      ),
      (
        DefaultAddress(List(high)),
        1,
        DefaultAddress(List(high)),
        Option.empty[String]
      ),
      (
        DefaultAddress(List(high)),
        3,
        DefaultAddress(List(low, low, high)),
        Option.empty[String]
      ),
      (
        DefaultAddress(List(high, low)),
        3,
        DefaultAddress(List(low, low, high, low)),
        Option.empty[String]
      ),
      (
        DefaultAddress(List(high, high, high)),
        2,
        DefaultAddress(List.empty),
        Option("Address not padded: already too long")
      )
    ).foreach { case (original, target, expected, failure) =>

      failure match
        case None =>
          original.padBig(target).map { padded =>
            assert(padded == expected)
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

    val low = UnsignedByte.minimum
    val high = UnsignedByte.maximum

    List(
      (
        DefaultAddress(List()),
        DefaultAddress(List())
      ),
      (
        DefaultAddress(List(low, low, low, low)),
        DefaultAddress(List(low))
      ),
      (
        DefaultAddress(List(high)),
        DefaultAddress(List(high))
      ),
      (
        DefaultAddress(List(low, high)),
        DefaultAddress(List(high))
      ),
      (
        DefaultAddress(List(low, low, low, high)),
        DefaultAddress(List(high))
      ),
      (
        DefaultAddress(List(low, high, low)),
        DefaultAddress(List(high, low))
      ),
      (
        DefaultAddress(List(high, low, low)),
        DefaultAddress(List(high, low, low))
      ),
      (
        DefaultAddress(List()),
        DefaultAddress(List())
      ),
      (
        DefaultAddress(List(low, low, low)),
        DefaultAddress(List())
      ),
    ).foreach { case (original, expected) =>
      assert(
        original.trimBig == expected
      )
    }
  }
