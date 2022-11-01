package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._

case class TreePath(indices: Vector[UnsignedByte]) extends Path:

  override
  def isEmpty: Boolean =

    indices.isEmpty

  override
  def shorten: Option[(UnsignedByte, Path)] =

    if indices.nonEmpty then
      Some(indices.head -> this.copy(indices = indices.tail))
    else
      None
