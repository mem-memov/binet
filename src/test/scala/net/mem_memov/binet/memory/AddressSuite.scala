package net.mem_memov.binet.memory

class AddressSuite extends munit.FunSuite:

  test("Address gets incremented") {
    List(
      (
        Address(List(UnsignedByte.minimum)),
        Address(List(UnsignedByte.fromInt(1)))
      ),
      (
        Address(List(UnsignedByte.fromInt(254))),
        Address(List(UnsignedByte.fromInt(255)))
      ),
      (
        Address(List(UnsignedByte.fromInt(255))),
        Address(List(UnsignedByte.fromInt(1), UnsignedByte.minimum))
      ),
      (
        Address(List(UnsignedByte.fromInt(255), UnsignedByte.fromInt(255))),
        Address(List(UnsignedByte.fromInt(1), UnsignedByte.minimum, UnsignedByte.minimum))
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
            Address(indices).hasLength(indices.length)
          )
        }
  }

  test("Create another address with some zero bytes at the head") {

    val low = UnsignedByte.minimum
    val high = UnsignedByte.maximum

    List(
      (
        Address(List.empty),
        2,
        Address(List(low, low)),
        Option.empty[String]
      ),
      (
        Address(List(low)),
        3,
        Address(List(low, low ,low)),
        Option.empty[String]
      ),
      (
        Address(List(high)),
        1,
        Address(List(high)),
        Option.empty[String]
      ),
      (
        Address(List(high)),
        3,
        Address(List(low, low, high)),
        Option.empty[String]
      ),
      (
        Address(List(high, low)),
        3,
        Address(List(low, low, high, low)),
        Option.empty[String]
      ),
      (
        Address(List(high, high, high)),
        2,
        Address(List.empty),
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
        Address(List()),
        Address(List())
      ),
      (
        Address(List(low, low, low, low)),
        Address(List(low))
      ),
      (
        Address(List(high)),
        Address(List(high))
      ),
      (
        Address(List(low, high)),
        Address(List(high))
      ),
      (
        Address(List(low, low, low, high)),
        Address(List(high))
      ),
      (
        Address(List(low, high, low)),
        Address(List(high, low))
      ),
      (
        Address(List(high, low, low)),
        Address(List(high, low, low))
      ),
      (
        Address(List()),
        Address(List())
      ),
      (
        Address(List(low, low, low)),
        Address(List())
      ),
    ).foreach { case (original, expected) =>
      assert(
        original.trimBig == expected
      )
    }
  }
