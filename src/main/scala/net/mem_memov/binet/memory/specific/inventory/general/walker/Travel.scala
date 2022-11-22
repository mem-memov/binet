package net.mem_memov.binet.memory.specific.inventory.general.walker

import net.mem_memov.binet.memory.general

trait Travel[WALKER, ADDRESS]:

  def f[RESULT](
    walker: WALKER,
    result: RESULT,
    origin: ADDRESS,
    process: (RESULT, general.Item[ADDRESS]) => RESULT
  ): Either[String, RESULT]

  extension (walker: WALKER)

    def travel[RESULT](
      result: RESULT,
      origin: ADDRESS,
      process: (RESULT, general.Item[ADDRESS]) => RESULT
    ): Either[String, RESULT] =

      f(walker, result, origin, process)
