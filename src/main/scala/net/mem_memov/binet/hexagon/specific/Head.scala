package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Head(
  arrow: Arrow
)

object Head//:

//  given [NETWORK](using
//    general.arrow.GetNextTargetArrow[Arrow, NETWORK],
//    general.arrow.ToHead[Arrow, Head],
//    general.network.HasArrow[NETWORK],
//    general.network.RequireArrow[NETWORK,Arrow]
//  ): general.head.GetNext[Head, NETWORK] with
//
//    override
//    def f(
//      head: Head,
//      network: NETWORK
//    ): Either[String, Option[Head]] =
//
//      for {
//        networkWithArrow <- head.arrow.getNextTargetArrow(network)
//        _ <- if networkWithArrow.hasArrow then
//          for {
//            arrow <- networkWithArrow.requireArrow()
//          } yield Some(arrow)
//        else
//
//      } yield ???


