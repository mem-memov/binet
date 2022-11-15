package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class SpecificPath (
  indices: Vector[general.UnsignedByte]
)

object SpecificPath:

  given general.path.IsEmpty[SpecificPath] with

    override
    def isPathEmpty(
      path: SpecificPath
    ): Boolean =

      path.indices.isEmpty

  given general.path.Shorten[SpecificPath] with

    override
    def shortenPath(
      path: SpecificPath
    ): Either[String, general.path.Shorten.Split[SpecificPath]] =

      if path.indices.nonEmpty then
        Right(
          general.path.Shorten.Split(
            index = path.indices.head,
            rest = path.copy(indices = path.indices.tail)
          )
        )
      else
        Left("Path couldn't be used")