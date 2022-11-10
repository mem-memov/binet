package net.mem_memov.binet.specific.specificAddress.specific.specificOrderer

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.address.AddressIndices
import net.mem_memov.binet.memory.specific.specificAddress.general.formatter.TrimmingFormatter
import net.mem_memov.binet.memory.specific.specificAddress.specific.*
import net.mem_memov.binet.memory.specific.specificAddress.specific.SpecificOrderer.given

class ComparingSuite extends munit.FunSuite:

  class Stub
  given trimmingFormatter: Stub = new Stub
  val leftAddress = new Stub
  val rightAddress = new Stub
  val orderer = new SpecificOrderer

  test("Orderer confirms left is equal to right") {

    val leftIndices = List(UnsignedByte.maximum)
    val rightIndices = List(UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 0)
  }

  test("Orderer confirms left is greater then right") {

    val leftIndices = List(UnsignedByte.maximum)
    val rightIndices = List(UnsignedByte.minimum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 1)
  }

  test("Orderer confirms left is less then right") {

    val leftIndices = List(UnsignedByte.minimum)
    val rightIndices = List(UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        indices

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          leftIndices
        else if address.equals(rightAddress) then
          rightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == -1)
  }

  test("Orderer confirms left is equal to right after trimming") {

    val originalLeftIndices = List(UnsignedByte.minimum, UnsignedByte.maximum)
    val originalRightIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(UnsignedByte.maximum)
    val trimmedRightIndices = List(UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 0)
  }

  test("Orderer confirms left is greater than right after trimming") {

    val originalLeftIndices = List(UnsignedByte.minimum, UnsignedByte.maximum, UnsignedByte.maximum)
    val originalRightIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(UnsignedByte.maximum, UnsignedByte.maximum)
    val trimmedRightIndices = List(UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == 1)
  }

  test("Orderer confirms left is less than right after trimming") {

    val originalLeftIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)
    val originalRightIndices = List(UnsignedByte.minimum, UnsignedByte.maximum, UnsignedByte.maximum)

    given AddressIndices[Stub] with
      override def indicesOfAddress(address: Stub): List[UnsignedByte] =
        if address.equals(leftAddress) then
          originalLeftIndices
        else if address.equals(rightAddress) then
          originalRightIndices
        else
          fail("unexpected")

    val trimmedLeftIndices = List(UnsignedByte.maximum)
    val trimmedRightIndices = List(UnsignedByte.maximum, UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        if indices.equals(originalLeftIndices) then
          trimmedLeftIndices
        else if indices.equals(originalRightIndices) then
          trimmedRightIndices
        else
          fail("unexpected")

    val result = orderer.compare(leftAddress, rightAddress)

    assert(result == -1)
  }
