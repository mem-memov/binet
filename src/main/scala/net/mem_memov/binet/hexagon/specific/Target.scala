package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Target(
  dotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Target:

  given [ADDRESS, HEAD, NETWORK, TAIL](using
    general.dotReference.GetAddress[DotReference, ADDRESS],
    general.arrowReference.GetAddressOption[ArrowReference, ADDRESS],
    general.counter.Increment[Counter, NETWORK],
    general.arrowReference.ReadHead[ArrowReference, HEAD, NETWORK],
    general.network.CreateHead[NETWORK, ADDRESS, HEAD],
    general.head.GetReferencedBy[HEAD, ArrowReference, NETWORK],
    general.head.Follow[HEAD, NETWORK],
    general.head.ToTail[HEAD, TAIL]
  ): general.target.CreateTailFromSource[Target, ADDRESS, NETWORK, TAIL] with

    override
    def f(
      target: Target,
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      network: NETWORK
    ): Either[String, (NETWORK, TAIL)] =

      for {

        nextHeadOption <- target.targetArrowReference.readHead(network)

        result1 <- network.createHead(sourceDotAddress, sourceArrowAddressOption, target.dotReference.getAddress, target.targetArrowReference.getAddressOption)
        (network1, head) = result1

        result2 <- head.getReferencedBy(target.targetArrowReference, network1)
        (network2, _) = result2

        result3 <- target.targetCounter.increment(network2)
        (network3, _) = result3

        modifiedNetwork <- nextHeadOption match
          case Some(nextHead) => nextHead.follow(head, network3)
          case None => Right(network3)

      } yield (modifiedNetwork, head.toTail)

  given [NETWORK, SOURCE, TAIL](using
    general.source.IsInTails[SOURCE, NETWORK, TAIL],
    general.arrowReference.ReadTail[ArrowReference, NETWORK, TAIL]
  ): general.target.HasSource[Target, NETWORK, SOURCE] with

    override
    def f(
      target: Target,
      source: SOURCE,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionTail <- target.targetArrowReference.readTail(network)
        hasTarget <- optionTail match
          case None => Right(false)
          case Some(tail) => source.isInTails(tail, network)
      } yield hasTarget

  given [HEAD, NETWORK](using
    general.head.ReferencesDot[HEAD, DotReference],
    general.head.GetNext[HEAD, NETWORK],
  ): general.target.IsInHeads[Target, HEAD, NETWORK] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      target: Target,
      head: HEAD,
      network: NETWORK
    ): Either[String, Boolean] =

      if head.referencesDot(target.dotReference) then
        Right(true)
      else
        val eitherOptionHead = head.getNext(network)
        eitherOptionHead match
          case Left(error) => Left(error)
          case Right(optionHead) =>
            optionHead match
              case None => Right(false)
              case Some(head) => f(target, head, network)

  given [ARROW, HEAD, NETWORK, SOURCE, TAIL](using
    general.tail.CollectSources[TAIL, NETWORK, SOURCE],
    general.arrowReference.ReadTail[ArrowReference, NETWORK, TAIL]
  ): general.target.ReadSources[Target, NETWORK, SOURCE] with

    override
    def f(
      target: Target,
      network: NETWORK
    ): Either[String, List[SOURCE]] =

      for {
        tailOption <- target.sourceArrowReference.readTail(network)
        sources <- tailOption match
          case Some(tail) => tail.collectSources(network, List.empty[SOURCE])
          case None => Right(List.empty[SOURCE])
      } yield sources

  given [VERTEX](using
    general.dotReference.ToVertex[DotReference, VERTEX]
  ): general.target.ToVertex[Target, VERTEX] with

    override
    def f(
      target: Target
    ): VERTEX =

      target.dotReference.toVertex

  given (using
    general.counter.IsLarger[Counter]
  ): general.target.HasMoreHeads[Target, Counter] with

    override def f(
      target: Target,
      counter: Counter
    ): Boolean =

      target.targetCounter.isLarger(counter)

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, DotReference, NETWORK]
  ): general.target.ReferenceHead[Target, DotReference, NETWORK] with

    override
    def f(
      target: Target,
      dotReference: DotReference,
      network: NETWORK
    ): Either[String, (NETWORK, Target)] =

      for {
        result <- target.targetArrowReference.referencePath(dotReference, network)
        (modifiedNetwork, modifiedArrowReference) = result
      } yield
        val modifiedTarget = target.copy(targetArrowReference = modifiedArrowReference)
        (modifiedNetwork, modifiedTarget)

  given [NETWORK](using
    general.arrowReference.Clear[ArrowReference, NETWORK]
  ): general.target.ClearArrowReference[Target, NETWORK] with

    override
    def f(
      target: Target,
      network: NETWORK
    ): Either[String, (NETWORK, Target)] =

      for {
        result <- target.targetArrowReference.clear(network)
        (modifiedNetwork, modifiedArrowReference) = result
      } yield
        val modifiedTarget = target.copy(targetArrowReference = modifiedArrowReference)
        (network, modifiedTarget)