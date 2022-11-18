package net.mem_memov.binet.specific.element.specific.reader

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.specific.Reader
import net.mem_memov.binet.memory.specific.element.specific.Reader.given

class ReadStoreSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  test("Reader ") {

    val reader = new Reader
    val restPath = specific.Path(Vector())
    val store = specific.Store(Vector())

    val result = reader.readStore(
      Some(store),
      general.Split(b5, restPath)
    )
  }
