package net.mem_memov.binet.memory.general.path

import net.mem_memov.binet.memory.general.UnsignedByte

trait PathShortener[PATH]:

  def shortenPath(
    path: PATH
  ): Either[String, PathShortener.Split[PATH]]

  extension (path: PATH)

    def shorten(): Either[String, PathShortener.Split[PATH]] =

      shortenPath(path)

object PathShortener:

  case class Split[PATH](index: UnsignedByte, rest: PATH)
