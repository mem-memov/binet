package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general.dotReference.InArrow
import net.mem_memov.binet.hexagon.{general, specific}
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  tailDotReference: DotReference,
  previousTailArrowReference: ArrowReference,
  nextTailArrowReference: ArrowReference,
  headDotReference: DotReference,
  previousHeadArrowReference: ArrowReference,
  nextHeadArrowReference: ArrowReference
)

object Arrow:

  given (using
    general.dotReference.InArrow[DotReference]
  ): general.arrow.IsArrow[Arrow] with

    override def f(arrow: Arrow): Boolean =

      arrow.tailDotReference.inArrow

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, DotReference, NETWORK]
  ): general.arrow.SetReference[Arrow, ArrowReference, NETWORK] with

    override
    def f(
      arrow: Arrow,
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, (NETWORK, ArrowReference)] =

      arrowReference.referencePath(arrow.tailDotReference, network)

  given [FACTORY, TAIL](using
    general.factory.MakeTail[FACTORY, ArrowReference, DotReference, TAIL]
  )(using
    factory: FACTORY
  ): general.arrow.ToTail[Arrow, TAIL] with

    override
    def f(
      arrow: Arrow
    ): TAIL =

      factory.makeTail(
        arrow.tailDotReference,
        arrow.previousTailArrowReference,
        arrow.nextTailArrowReference,
        arrow.headDotReference,
        arrow.previousHeadArrowReference,
        arrow.nextHeadArrowReference
      )

  given [FACTORY, HEAD](using
    general.factory.MakeHead[FACTORY, ArrowReference, DotReference, HEAD]
  )(using
    factory: FACTORY
  ): general.arrow.ToHead[Arrow, HEAD] with

    override
    def f(
      arrow: Arrow
    ): HEAD =

      factory.makeHead(
        arrow.tailDotReference,
        arrow.previousTailArrowReference,
        arrow.nextTailArrowReference,
        arrow.headDotReference,
        arrow.previousHeadArrowReference,
        arrow.nextHeadArrowReference
      )
