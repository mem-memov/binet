package net.mem_memov.binet.hexagon.specific.target.specific

import net.mem_memov.binet.hexagon.{general, specific}

class ArrowEntry

object ArrowEntry:

  given [ADDRESS, DOT, ENTRY](using
    general.entry.SetAddress4[ENTRY, ADDRESS],
    general.entry.SetAddress5[ENTRY, ADDRESS],
    general.dot.GetAddress[DOT, ADDRESS],
    general.dot.GetSourceArrowAddress[DOT, ADDRESS]
  ): specific.target.general.arrowEntry.Update[ArrowEntry, DOT, ENTRY] with

    override
    def f(
      entry: ENTRY,
      dot: DOT
    ): ENTRY =

      val entry2 = entry.setAddress4(dot.getAddress)

      dot.getSourceArrowAddress match
        case None => entry2
        case Some(sourceArrowAddress) => entry2.setAddress5(sourceArrowAddress)

