package net.mem_memov.binet.memory

import zio.*

import scala.annotation.tailrec

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
class Inventory(
  val next: Address,
  val root: Element
):

  def append(content: Address): Task[Inventory] =
    val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
    for {
      _ <- if trimmedContent >= next then ZIO.fail(Exception("Inventory not appended: content out of boundary")) else ZIO.succeed(trimmedContent)
      updatedRoot <- root.write(next, trimmedContent)
      newNext <- next.increment
    } yield Inventory(newNext, updatedRoot)

  def update(destination: Address, content: Address): Task[Inventory] =
    val trimmedDestination = if content.isEmpty then Address.zero else destination.trimBig
    val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
    for {
      _ <- if trimmedDestination >= next then ZIO.fail(Exception("Inventory not appended: destination out of boundary")) else ZIO.succeed(trimmedDestination)
      _ <- if trimmedContent >= next then ZIO.fail(Exception("Inventory not appended: content out of boundary")) else ZIO.succeed(trimmedContent)
      updatedRoot <- root.write(next, content)
    } yield Inventory(next, updatedRoot)

  def read(origin: Address): Task[Address] =
    val trimmedOrigin = if origin.isEmpty then Address.zero else origin.trimBig
    for {
      content <- root.read(trimmedOrigin)
    } yield content.trimBig

object Inventory:

  val start: Address = Address.zero

  def apply: Inventory =
    new Inventory(Address.zero, Element.root)

  def apply(next: Address, root: Element): Inventory =
    new Inventory(next, root)
