package net.mem_memov.binet.memory

import scala.annotation.tailrec

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
trait Inventory:

  val next: Address

  def append(content: Address): Either[String, Inventory]

  def update(destination: Address, content: Address): Either[String, Inventory]

  def read(origin: Address): Either[String, Address]

object Inventory:

  val start: Address = Address.zero

  def apply(): Inventory = Inventory(Address.zero, Element.root)

  def apply(nextAddress: Address, root: Element): Inventory = new Inventory:

    val next: Address = nextAddress

    def append(content: Address): Either[String, Inventory] =
      val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
      if trimmedContent >= next && next != Address.zero then
        Left("Inventory not appended: content out of boundary")
      else
        for {
          updatedRoot <- root.write(next, trimmedContent)
          newNext <- Right(next.increment)
        } yield Inventory(newNext, updatedRoot)

    def update(destination: Address, content: Address): Either[String, Inventory] =
      val trimmedDestination = if content.isEmpty then Address.zero else destination.trimBig
      val trimmedContent = if content.isEmpty then Address.zero else content.trimBig
      if trimmedDestination >= next then
        Left("Inventory not appended: destination out of boundary")
      else
        if trimmedContent >= next then
          Left("Inventory not appended: content out of boundary")
        else
          for {
            updatedRoot <- root.write(next, content)
          } yield Inventory(next, updatedRoot)

    def read(origin: Address): Either[String, Address] =
      val trimmedOrigin = if origin.isEmpty then Address.zero else origin.trimBig
      for {
        content <- root.read(trimmedOrigin)
      } yield content.trimBig