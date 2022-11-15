package net.mem_memov.binet.memory.general.path

import net.mem_memov.binet.memory.general.UnsignedByte

trait Shorten[PATH]:

  def shortenPath(
    path: PATH
  ): Either[String, Shorten.Split[PATH]]

  extension (path: PATH)

    def shorten(): Either[String, Shorten.Split[PATH]] =

      shortenPath(path)

object Shorten:

  case class Split[PATH](index: UnsignedByte, rest: PATH)
