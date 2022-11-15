package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Path (
  indices: Vector[general.UnsignedByte]
)

object Path:

  given general.path.IsEmpty[Path] with

    override
    def isPathEmpty(
      path: Path
    ): Boolean =

      path.indices.isEmpty

  given general.path.Shorten[Path] with

    override
    def shortenPath(
      path: Path
    ): Either[String, general.path.Shorten.Split[Path]] =

      if path.indices.nonEmpty then
        Right(
          general.path.Shorten.Split(
            index = path.indices.head,
            rest = path.copy(indices = path.indices.tail)
          )
        )
      else
        Left("Path couldn't be used")