package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.*

import scala.annotation.tailrec

case class TreeInventory(
  next: Address,
  root: Element,
  argument: Argument
)(using
  addressFactory: AddressFactory,
  traversalFactory: TraversalFactory
) extends Inventory:

  override
  def append(
    content: Address
  ): Either[String, TreeInventory] =

    for {
      trimmedContent <- argument.checkAndTrimPermissive(next, content)
      updatedRoot <- root.write(next.toPath, trimmedContent.toContent)
      newNext <- Right(next.increment)
    } yield this.copy(next = newNext, root = updatedRoot)

  override
  def update(
    destination: Address, 
    content: Address
  ): Either[String, TreeInventory] =

    for {
      trimmedDestination <- argument.checkAndTrimRestrictive(next, destination)
      trimmedContent <- argument.checkAndTrimRestrictive(next, content)
      updatedRoot <- root.write(trimmedDestination.toPath, trimmedContent.toContent)
    } yield this.copy(root = updatedRoot)

  override
  def read(
    origin: Address
  ): Either[String, Address] =

    for {
      trimmedOrigin <- argument.checkAndTrimRestrictive(next, origin)
      content <- root.read(trimmedOrigin)
    } yield content.trimBig

  override
  def foreachSlice(
    f: Array[Byte] => Unit
  ): Unit =

    val traversal = traversalFactory.createFirst(root, next)

    @tailrec
    def t(traversal: Traversal, f: Array[Byte] => Unit): Unit =

      traversal.next match
        case Left(message) => () // TODO: handle error
        case Right(None) => ()
        case Right(Some((content, modifiedTraversal))) =>
          t(modifiedTraversal, f)

    t(traversal, f)
      