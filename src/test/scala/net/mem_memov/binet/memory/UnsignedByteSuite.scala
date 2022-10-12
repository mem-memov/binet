package net.mem_memov.binet.memory

class UnsignedByteSuite extends munit.FunSuite:

  test("UnsignedByte converts to Int") {
    (0 to 255).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val result = unsignedByte.toInt
      assert(result == i)
    }
  }

  test("UnsignedByte cah be checked for its maximum value") {
    (0 to 255).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val isAtMaximum = unsignedByte.atMaximum
      assert(if i == 255 then isAtMaximum else !isAtMaximum)
    }
  }

  test("UnsignedByte cah be checked for its minimum value") {
    (0 to 255).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val isAtMinimum = unsignedByte.atMinimum
      assert(if i == 0 then isAtMinimum else !isAtMinimum)
    }
  }

  test("UnsignedByte cah be created with the maximum value") {
    val unsignedByte = UnsignedByte.maximum
    assert(255 == unsignedByte.toInt)
  }

  test("UnsignedByte cah be created with the minimum value") {
    val unsignedByte = UnsignedByte.minimum
    assert(0 == unsignedByte.toInt)
  }

  test("UnsignedByte cah be incremented by one") {
    (0 to 254).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val incremented = unsignedByte.increment
      assert(incremented.toInt == (i + 1))
    }
  }

  test("UnsignedByte cah be decremented by one") {
    (1 to 255).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val decremented = unsignedByte.decrement
      assert(decremented.toInt == (i - 1))
    }
  }

  test("UnsignedByte overflows when incremented at its maximum value") {
    val unsignedByte = UnsignedByte.maximum
    val incremented = unsignedByte.increment
    assert(0 == incremented.toInt)
  }

  test("UnsignedByte underflows when decremented at its minimum value") {
    val unsignedByte = UnsignedByte.minimum
    val decremented = unsignedByte.decrement
    assert(255 == decremented.toInt)
  }

  test("UnsignedBytes can be compared for equality") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)
      val y = UnsignedByte.fromInt(i)
      assert(x == y)
    }
  }

  test("UnsignedBytes can be compared for inequality") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)
      val y = UnsignedByte.fromInt(i)
      assert(x != y.increment)
      assert(x != y.decrement)
    }
  }

  test("UnsignedBytes can be compared for greater-than relation") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)

      if i != 255 then
        assert(x.increment > x)
      else
        assert(x > x.increment)

      if i != 0 then
        assert(x > x.decrement)
      else
        assert(x.decrement > x)
    }
  }

  test("UnsignedBytes can be compared for less-than relation") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)

      if i != 255 then
        assert(x < x.increment)
      else
        assert(x.increment < x)

      if i != 0 then
        assert(x.decrement < x)
      else
        assert(x < x.decrement)
    }
  }

  test("UnsignedBytes can be compared for greater-than-or-equal relation") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)
      val y = UnsignedByte.fromInt(i)

      if i != 255 then
        assert(y.increment >= x)
        assert(y >= x)
      else
        assert(x >= y.increment)
        assert(x >= y)

      if i != 0 then
        assert(x >= y.decrement)
        assert(x >= y)
      else
        assert(y.decrement >= x)
        assert(y >= x)
    }
  }

  test("UnsignedBytes can be compared for less-than-or-equal relation") {
    (1 to 255).map { i =>
      val x = UnsignedByte.fromInt(i)
      val y = UnsignedByte.fromInt(i)

      if i != 255 then
        assert(x <= y.increment)
        assert(x <= y)
      else
        assert(y.increment <= x)
        assert(y <= x)

      if i != 0 then
        assert(y.decrement <= x)
        assert(y <= x)
      else
        assert(x <= y.decrement)
        assert(x <= y)
    }
  }

  test("UnsignedBytes created with over-sized integers overflow") {
    val unsignedByte = UnsignedByte.fromInt(256)
    assert(unsignedByte.toInt == 0)
  }

  test("UnsignedBytes created with negative integers underflow") {
    val unsignedByte = UnsignedByte.fromInt(-1)
    assert(unsignedByte.toInt == 255)
  }
