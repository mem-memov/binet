package net.mem_memov.binet.hexagon.general.factory

trait MakeArrow[FACTORY, ARROW, ENTRY]:

  def f(
    sourceDotEntry: ENTRY,
    previousSourceArrowEntry: ENTRY,
    nextSourceArrowEntry: ENTRY,
    targetDotEntry: ENTRY,
    previousTargetArrowEntry: ENTRY,
    nextTargetArrowEntry: ENTRY
  ): ARROW

  extension (factory: FACTORY)

    def makeArrow(
      sourceDotEntry: ENTRY,
      previousSourceArrowEntry: ENTRY,
      nextSourceArrowEntry: ENTRY,
      targetDotEntry: ENTRY,
      previousTargetArrowEntry: ENTRY,
      nextTargetArrowEntry: ENTRY
    ): ARROW =

      f(sourceDotEntry, previousSourceArrowEntry, nextSourceArrowEntry, targetDotEntry, previousTargetArrowEntry, nextTargetArrowEntry)

