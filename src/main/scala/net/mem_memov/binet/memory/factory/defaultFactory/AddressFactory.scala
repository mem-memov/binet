package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.address.DefaultAddress

trait AddressFactory:

  lazy val zeroAddress: Address

object AddressFactory:

  def apply(): AddressFactory = 
    
    new AddressFactory:
      
      override lazy val zeroAddress: Address =
        DefaultAddress(List(UnsignedByte.minimum))


