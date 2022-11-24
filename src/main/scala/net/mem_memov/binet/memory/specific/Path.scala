package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Path (
  indices: Vector[general.UnsignedByte]
)

object Path:

  given net_mem_memov_binet_memory_specific_Path_IsEmpty: general.path.IsEmpty[Path] with

    override
    def f(
      path: Path
    ): Boolean =

      path.indices.isEmpty

  given net_mem_memov_binet_memory_specific_Path_Shorten: general.path.Shorten[Path] with

    override
    def f(
      path: Path
    ): Either[String, general.Split[Path]] =

      if path.indices.nonEmpty then
        Right(
          general.Split(
            index = path.indices.head,
            rest = path.copy(indices = path.indices.tail)
          )
        )
      else
        Left("Path couldn't be used")