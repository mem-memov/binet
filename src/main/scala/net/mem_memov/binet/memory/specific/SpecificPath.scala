package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.path.{PathEmptyChecker, PathShortener}

case class SpecificPath (
  indices: Vector[UnsignedByte]
)

object SpecificPath:

  given PathEmptyChecker[SpecificPath] with

    override
    def isPathEmpty(
      path: SpecificPath
    ): Boolean =

      path.indices.isEmpty

  given PathShortener[SpecificPath] with

    override
    def shortenPath(
      path: SpecificPath
    ): Either[String, PathShortener.Split[SpecificPath]] =

      if path.indices.nonEmpty then
        Right(
          PathShortener.Split(
            index = path.indices.head,
            rest = path.copy(indices = path.indices.tail)
          )
        )
      else
        Left("Path couldn't be used")