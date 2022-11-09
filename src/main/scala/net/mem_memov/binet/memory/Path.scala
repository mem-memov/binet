package net.mem_memov.binet.memory

trait Path[P]:

  def isPathEmpty(
    path: P
  ): Boolean

  def shortenPath(
    path: P
  ): Either[String, Path.Split]

  extension (path: P)

    def isEmpty: Boolean =

      isPathEmpty(path)

    def shorten: Either[String, Path.Split] =

      shortenPath(P)

object Path:

  case class Split(index: UnsignedByte, rest: Path)