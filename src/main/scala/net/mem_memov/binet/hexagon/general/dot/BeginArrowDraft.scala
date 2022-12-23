package net.mem_memov.binet.hexagon.general.dot

trait BeginArrowDraft[DOT, ARROW_DRAFT_BEGIN]:

  def f(
    dot: DOT
  ): ARROW_DRAFT_BEGIN

  extension (dot: DOT)

    def beginArrowDraft: ARROW_DRAFT_BEGIN =

      f(dot)
