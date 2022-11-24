package net.mem_memov.binet.memory.address.specific.orderer

import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig
import net.mem_memov.binet.memory.specific.address.specific.Orderer
import net.mem_memov.binet.memory.specific.address.specific.Orderer.given

class CompareSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum

  class TrimmerStub
  given trimmer: TrimmerStub = new TrimmerStub

  test("Orderer confirms left is equal to right") {

    val leftIndices = List(max)
    val rightIndices = List(max)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(leftIndices),
      specific.Address(rightIndices)
    )

    assert(result == 0)
  }

  test("Orderer confirms left is greater then right") {

    val leftIndices = List(max)
    val rightIndices = List(min)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(leftIndices),
      specific.Address(rightIndices)
    )

    assert(result == 1)
  }

  test("Orderer confirms left is less then right") {

    val leftIndices = List(min)
    val rightIndices = List(max)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(leftIndices),
      specific.Address(rightIndices)
    )

    assert(result == -1)
  }

  test("Orderer confirms left is equal to right after trimming") {

    val originalLeftIndices = List(min, max)
    val originalRightIndices = List(min, min, max)

    val trimmedLeftIndices = List(max)
    val trimmedRightIndices = List(max)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(originalLeftIndices),
      specific.Address(originalRightIndices)
    )

    assert(result == 0)
  }

  test("Orderer confirms left is greater than right after trimming") {

    val originalLeftIndices = List(min, max, max)
    val originalRightIndices = List(min, min, min, max)

    val trimmedLeftIndices = List(max, max)
    val trimmedRightIndices = List(max)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(originalLeftIndices),
      specific.Address(originalRightIndices)
    )

    assert(result == 1)
  }

  test("Orderer confirms left is less than right after trimming") {

    val originalLeftIndices = List(min, min, min, max)
    val originalRightIndices = List(min, max, max)

    val trimmedLeftIndices = List(max)
    val trimmedRightIndices = List(max, max)

    given TrimBig[TrimmerStub] with
      override def f(trimmer: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(
      specific.Address(originalLeftIndices),
      specific.Address(originalRightIndices)
    )

    assert(result == -1)
  }
