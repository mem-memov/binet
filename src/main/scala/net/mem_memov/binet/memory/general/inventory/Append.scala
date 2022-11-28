package net.mem_memov.binet.memory.general.inventory

trait Append[INVENTORY, ADDRESS]:

  private[Append]
  def f(
    inventory: INVENTORY,
    content: ADDRESS
  ): Either[String, INVENTORY]

  extension (inventory: INVENTORY)

    def append(
      content: ADDRESS
    ): Either[String, INVENTORY] =

      f(inventory, content)
