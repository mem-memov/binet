package net.mem_memov.binet.memory.tree.defaultInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.defaultFactory._

trait UnusedArgumentChecking(fail: String => Nothing) extends ArgumentChecking:

  def checkAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    fail("unexpected")
