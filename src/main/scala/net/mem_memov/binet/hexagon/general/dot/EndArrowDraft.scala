package net.mem_memov.binet.hexagon.general.dot

trait EndArrowDraft[DOT, ARROW_DRAFT_BEGIN, ARROW_DRAFT_END]:

  def f(
    dot: DOT,
    arrowDraftBegin: ARROW_DRAFT_BEGIN
  ): ARROW_DRAFT_END

  extension (dot: DOT)

    def endArrowDraft(
      arrowDraftBegin: ARROW_DRAFT_BEGIN
    ): ARROW_DRAFT_END =

      f(dot, arrowDraftBegin)
