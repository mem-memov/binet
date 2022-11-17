package net.mem_memov.binet.memory.general.path

import net.mem_memov.binet.memory.general.UnsignedByte

trait Shorten[PATH]:

  def f(
    path: PATH
  ): Either[String, Shorten.Split[PATH]]

  extension (path: PATH)

    def shorten(): Either[String, Shorten.Split[PATH]] =

      f(path)

object Shorten:

  case class Split[PATH](index: UnsignedByte, rest: PATH)
