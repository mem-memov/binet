package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.address.DefaultAddress

trait AddressFactory:

  lazy val zeroAddress: Address

object AddressFactory:

  val cachedFactory: Option[AddressFactory] = None

  def apply(): AddressFactory = cachedFactory.getOrElse {
    
    new AddressFactory:
      
      override lazy val zeroAddress: Address =
        DefaultAddress(List(UnsignedByte.minimum))
  }

