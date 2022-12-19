package net.mem_memov.binet.hexagon.specific.source.specific

import net.mem_memov.binet.hexagon.{general, specific}

class ArrowEntry

object ArrowEntry:

  given [ADDRESS, DOT, ENTRY, FACTORY](using
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS],
    general.entry.SetAddress2[ENTRY, ADDRESS],
    general.dot.GetAddress[DOT, ADDRESS],
    general.dot.GetTargetArrowAddress[DOT, ADDRESS]
  )(using
    factory: FACTORY
  ): specific.source.general.arrowEntry.Create[ArrowEntry, DOT, ENTRY] with

    override
    def f(
      dot: DOT
    ): ENTRY =

      val entry = factory.emptyEntry().setAddress1(dot.getAddress)

      dot.getTargetArrowAddress match {
        case Some(targetArrowAddress) => entry.setAddress2(targetArrowAddress)
        case None => entry
      }
