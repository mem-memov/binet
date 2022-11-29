package net.mem_memov.binet.memory.general.inventory

import net.mem_memov.binet.memory.general.Item

trait Fold[INVENTORY, ADDRESS]:

  def f[RESULT](
    initial: RESULT
  )(
    inventory: INVENTORY,
    process: (RESULT, Item[ADDRESS]) => RESULT
  ): Either[String, RESULT]

  extension (inventory: INVENTORY)

    def fold[RESULT](
      initial: RESULT
    )(
      process: (RESULT, Item[ADDRESS]) => RESULT
    ): Either[String, RESULT] =

      f(initial)(inventory, process)
