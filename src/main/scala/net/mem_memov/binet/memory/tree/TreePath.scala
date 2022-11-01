package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._

case class TreePath(indices: Vector[UnsignedByte]) extends Path:

  override
  def isEmpty: Boolean =

    indices.isEmpty

  override
  def shorten: Either[String, Path.Split] =

    if indices.nonEmpty then
      Right(
        Path.Split(
          index = indices.head,
          rest = this.copy(indices = indices.tail)
        )
      )
    else
      Left("Path couldn't be used")
