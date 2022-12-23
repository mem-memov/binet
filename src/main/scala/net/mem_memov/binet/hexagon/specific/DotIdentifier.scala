package net.mem_memov.binet.hexagon.specific

case class DotIdentifier(
  entry: Entry
)

object DotIdentifier//:



//  given (using
//    general.record.IsArrow[Record]
//  ): general.dotIdentifier.InArrow[DotIdentifier] with
//
//    override
//    def f(
//      dotIdentifier: DotIdentifier
//    ): Boolean =
//
//      dotIdentifier.record.isArrow
//
//  given (using
//    general.record.IsDot[Record]
//  ): general.dotIdentifier.InDot[DotIdentifier] with
//
//    override
//    def f(
//      dotIdentifier: DotIdentifier
//    ): Boolean =
//
//      dotIdentifier.record.isDot