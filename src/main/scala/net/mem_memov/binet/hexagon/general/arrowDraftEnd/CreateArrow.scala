package net.mem_memov.binet.hexagon.general.arrowDraftEnd

trait CreateArrow[ARROW_DRAFT_END, ARROW, DICTIONARY]:

  def f(
    arrowDraftEnd: ARROW_DRAFT_END,
    dictionary: DICTIONARY
  ): Either[String, (DICTIONARY, ARROW)]

  extension (arrowDraftEnd: ARROW_DRAFT_END)

    def createArrow(
      dictionary: DICTIONARY
    ): Either[String, (DICTIONARY, ARROW)] =

      f(arrowDraftEnd, dictionary)