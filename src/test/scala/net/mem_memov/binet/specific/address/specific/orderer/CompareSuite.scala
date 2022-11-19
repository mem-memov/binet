package net.mem_memov.binet.specific.address.specific.orderer

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.address.Indices
import net.mem_memov.binet.memory.specific.address.general.formatter.TrimBig
import net.mem_memov.binet.memory.specific.address.specific.*
import net.mem_memov.binet.memory.specific.address.specific.Orderer.given

class CompareSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum

  class Stub
  given trimmingFormatter: Stub = new Stub

  val leftAddress = new Stub
  val rightAddress = new Stub

  test("Orderer confirms left is equal to right") {

    val leftIndices = List(max)
    val rightIndices = List(max)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 0)
  }

  test("Orderer confirms left is greater then right") {

    val leftIndices = List(max)
    val rightIndices = List(min)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 1)
  }

  test("Orderer confirms left is less then right") {

    val leftIndices = List(min)
    val rightIndices = List(max)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == -1)
  }

  test("Orderer confirms left is equal to right after trimming") {

    val originalLeftIndices = List(min, max)
    val originalRightIndices = List(min, min, max)

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(max)
    val trimmedRightIndices = List(max)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 0)
  }

  test("Orderer confirms left is greater than right after trimming") {

    val originalLeftIndices = List(min, max, max)
    val originalRightIndices = List(min, min, min, max)

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(max, max)
    val trimmedRightIndices = List(max)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 1)
  }

  test("Orderer confirms left is less than right after trimming") {

    val originalLeftIndices = List(min, min, min, max)
    val originalRightIndices = List(min, max, max)

    given Indices[Stub] with
      override def f(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(max)
    val trimmedRightIndices = List(max, max)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val orderer = new Orderer

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == -1)
  }
