package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.*

import scala.annotation.tailrec

case class TreeInventory(
  next: Address,
  root: Element,
  argument: Argument,
  addressFactory: AddressFactory,
  traversalFactory: TraversalFactory
)

object TreeInventory:

  given Inventory[TreeInventory, Address] with

    override
    def nextInInventory(
      inventory: TreeInventory
    ): Address =

      inventory.next

    override
    def appendToInventory(
      inventory: TreeInventory,
      content: Address
    ): Either[String, TreeInventory] =

      for {
        trimmedContent <- inventory.argument.checkAndTrimPermissive(inventory.next, content)
        updatedRoot <- inventory.root.write(inventory.next.toPath, trimmedContent.toContent)
        newNext <- Right(inventory.next.increment)
      } yield inventory.copy(next = newNext, root = updatedRoot)

    override
    def updateInventory(
      inventory: TreeInventory,
      destination: Address,
      content: Address
    ): Either[String, TreeInventory] =

      for {
        trimmedDestination <- inventory.argument.checkAndTrimRestrictive(inventory.next, destination)
        trimmedContent <- inventory.argument.checkAndTrimRestrictive(inventory.next, content)
        updatedRoot <- inventory.root.write(trimmedDestination.toPath, trimmedContent.toContent)
      } yield inventory.copy(root = updatedRoot)

    override
    def readInventory(
      inventory: TreeInventory,
      origin: Address
    ): Either[String, Address] =

      for {
        trimmedOrigin <- inventory.argument.checkAndTrimRestrictive(inventory.next, origin)
        content <- inventory.root.read(trimmedOrigin.toPath)
      } yield content.toAddress.trimBig

    override
    def foreachSliceInInventory(
      inventory: TreeInventory,
      f: Array[Byte] => Unit
    ): Unit =

      val traversal = inventory.traversalFactory.createFirst(inventory.root, inventory.next)

      @tailrec
      def t(traversal: Traversal, f: Array[Byte] => Unit): Unit =

        traversal.next match
          case Left(message) => () // TODO: handle error
          case Right(None) => ()
          case Right(Some((content, modifiedTraversal))) =>
            t(modifiedTraversal, f)

      t(traversal, f)
