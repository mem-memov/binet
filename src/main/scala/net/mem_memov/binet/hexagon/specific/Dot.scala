package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Dot(
  address: Address,
  entry: Entry
)

object Dot//:

//  given [ARROW, FACTORY, NETWORK](using
//    general.network.ReadArrow[NETWORK, Address],
//    general.factory.CreateArrow[FACTORY, Address, ARROW, Entry]
//  )(using
//    factory: FACTORY
//  ): general.dot.GetParentArrow[Dot, NETWORK] with
//
//    override
//    def f(
//      dot: Dot,
//      network: NETWORK
//    ): Either[String, NETWORK] =
//
//      val arrowAddress = dot.entry.address1
//
//      for {
//        modifiedNetwork <- network.readArrow(arrowAddress)
//        arrow <- network.
//      } yield
//        val arrow = factory.makeArrow(arrowAddress, arrowEntry)

