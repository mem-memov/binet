package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

class Factory

object Factory:

  given general.factory.EmptyDictionary[Factory, Dictionary] with

    import net.mem_memov.binet.memory.Preamble.given

    lazy
    val dictionary: Dictionary =
      Dictionary(
        None,
        None,
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory(),
        factory.emptyInventory()
      )

    override def f(): Dictionary = dictionary

  given general.factory.EmptyEntry[Factory, Entry] with

    import net.mem_memov.binet.memory.Preamble.given

    lazy
    val zero = factory.zeroAddress()

    lazy
    val entry = Entry(zero, zero, zero, zero, zero, zero)

    override def f(): Entry = entry
