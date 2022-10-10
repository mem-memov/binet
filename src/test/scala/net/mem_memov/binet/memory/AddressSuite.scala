package net.mem_memov.binet.memory

class AddressSuite extends munit.FunSuite:

  test("Address gets incremented") {
    List(
      (
        new Address(List(UnsignedByte.minimum)),
        new Address(List(UnsignedByte.fromInt(1)))
      ),
      (
        new Address(List(UnsignedByte.fromInt(254))),
        new Address(List(UnsignedByte.fromInt(255)))
      ),
      (
        new Address(List(UnsignedByte.fromInt(255))),
        new Address(List(UnsignedByte.fromInt(1), UnsignedByte.minimum))
      ),
      (
        new Address(List(UnsignedByte.fromInt(255), UnsignedByte.fromInt(255))),
        new Address(List(UnsignedByte.fromInt(1), UnsignedByte.minimum, UnsignedByte.minimum))
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
            new Address(indices).hasLength(indices.length)
          )
        }
  }

  test("Create another address with some zero bytes at the head") {

    val low = UnsignedByte.minimum
    val high = UnsignedByte.maximum

    List(
      (
        new Address(List.empty),
        2,
        new Address(List(low, low)),
        Option.empty[String]
      ),
      (
        new Address(List(low)),
        3,
        new Address(List(low, low ,low)),
        Option.empty[String]
      ),
      (
        new Address(List(high)),
        1,
        new Address(List(high)),
        Option.empty[String]
      ),
      (
        new Address(List(high)),
        3,
        new Address(List(low, low, high)),
        Option.empty[String]
      ),
      (
        new Address(List(high, low)),
        3,
        new Address(List(low, low, high, low)),
        Option.empty[String]
      ),
      (
        new Address(List(high, high, high)),
        2,
        new Address(List.empty),
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
        new Address(List()),
        new Address(List())
      ),
      (
        new Address(List(low, low, low, low)),
        new Address(List(low))
      ),
      (
        new Address(List(high)),
        new Address(List(high))
      ),
      (
        new Address(List(low, high)),
        new Address(List(high))
      ),
      (
        new Address(List(low, low, low, high)),
        new Address(List(high))
      ),
      (
        new Address(List(low, high, low)),
        new Address(List(high, low))
      ),
      (
        new Address(List(high, low, low)),
        new Address(List(high, low, low))
      ),
      (
        new Address(List()),
        new Address(List())
      ),
      (
        new Address(List(low, low, low)),
        new Address(List())
      ),
    ).foreach { case (original, expected) =>
      assert(
        original.trimBig == expected
      )
    }
  }
