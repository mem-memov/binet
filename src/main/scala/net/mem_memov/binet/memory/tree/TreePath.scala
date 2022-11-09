package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._

case class TreePath(
  indices: Vector[UnsignedByte]
)

object TreePath:

  given Path[TreePath] with

    override
    def isPathEmpty(
      path: TreePath
    ): Boolean =

      path.indices.isEmpty

    override
    def shortenPath(
      path: TreePath
    ): Either[String, Path.Split] =

      if path.indices.nonEmpty then
        Right(
          Path.Split(
            index = path.indices.head,
            rest = this.copy(indices = path.indices.tail)
          )
        )
      else
        Left("Path couldn't be used")
