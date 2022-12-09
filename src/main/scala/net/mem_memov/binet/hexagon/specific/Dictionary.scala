package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Inventory
import net.mem_memov.binet.memory

case class Dictionary(
  inventory1: Inventory,
  inventory2: Inventory,
  inventory3: Inventory,
  inventory4: Inventory,
  inventory5: Inventory,
  inventory6: Inventory
)

object Dictionary:

//  import net.mem_memov.binet.memory.Preamble.given

  given [ADDRESS](using
    memory.general.inventory.GetNext[Inventory, ADDRESS]
  ): general.dictionary.GetNextAddress[Dictionary, ADDRESS] with

    override
    def f(
      dictionary: Dictionary
    ): ADDRESS =

      dictionary.inventory1.getNext()

  given [ADDRESS, ENTRY](using
    memory.general.inventory.Append[Inventory, ADDRESS],
    memory.general.inventory.GetNext[Inventory, ADDRESS],
    general.entry.GetAddress1[ENTRY, ADDRESS],
    general.entry.GetAddress2[ENTRY, ADDRESS],
    general.entry.GetAddress3[ENTRY, ADDRESS],
    general.entry.GetAddress4[ENTRY, ADDRESS],
    general.entry.GetAddress5[ENTRY, ADDRESS],
    general.entry.GetAddress6[ENTRY, ADDRESS],
  ): general.dictionary.Append[Dictionary, ADDRESS, ENTRY] with

    override
    def f(
      dictionary: Dictionary,
      entry: ENTRY
    ): Either[String, (Dictionary, ADDRESS)] =

      val address = dictionary.inventory1.getNext()

      for {
        modifiedInventory1 <- dictionary.inventory1.append(entry.getAddress1)
        modifiedInventory2 <- dictionary.inventory2.append(entry.getAddress2)
        modifiedInventory3 <- dictionary.inventory3.append(entry.getAddress3)
        modifiedInventory4 <- dictionary.inventory4.append(entry.getAddress4)
        modifiedInventory5 <- dictionary.inventory5.append(entry.getAddress5)
        modifiedInventory6 <- dictionary.inventory6.append(entry.getAddress6)
      } yield

        val modifiedDictionary = Dictionary(
          modifiedInventory1,
          modifiedInventory2,
          modifiedInventory3,
          modifiedInventory4,
          modifiedInventory5,
          modifiedInventory6,
        )

        (modifiedDictionary, address)
      
  given [ADDRESS, ENTRY](using
    memory.general.inventory.Update[Inventory, ADDRESS],
    general.entry.GetAddress1[ENTRY, ADDRESS],
    general.entry.GetAddress2[ENTRY, ADDRESS],
    general.entry.GetAddress3[ENTRY, ADDRESS],
    general.entry.GetAddress4[ENTRY, ADDRESS],
    general.entry.GetAddress5[ENTRY, ADDRESS],
    general.entry.GetAddress6[ENTRY, ADDRESS],
  ): general.dictionary.Update[Dictionary, ADDRESS, ENTRY] with

    override 
    def f(
      dictionary: Dictionary, 
      address: ADDRESS, 
      entry: ENTRY
    ): Either[String, Dictionary] =

      for {
        modifiedInventory1 <- dictionary.inventory1.update(address, entry.getAddress1)
        modifiedInventory2 <- dictionary.inventory2.update(address, entry.getAddress2)
        modifiedInventory3 <- dictionary.inventory3.update(address, entry.getAddress3)
        modifiedInventory4 <- dictionary.inventory4.update(address, entry.getAddress4)
        modifiedInventory5 <- dictionary.inventory5.update(address, entry.getAddress5)
        modifiedInventory6 <- dictionary.inventory6.update(address, entry.getAddress6)
      } yield Dictionary(
        modifiedInventory1,
        modifiedInventory2,
        modifiedInventory3,
        modifiedInventory4,
        modifiedInventory5,
        modifiedInventory6,
      )
      
  given [ADDRESS, ENTRY, FACTORY](using
    memory.general.inventory.Read[Inventory, ADDRESS],
    general.factory.MakeEntry[FACTORY, ADDRESS, ENTRY]
  )(using
    factory: FACTORY
  ): general.dictionary.Read[Dictionary, ADDRESS, ENTRY] with

    override 
    def f(
      dictionary: Dictionary, 
      address: ADDRESS
    ): Either[String, ENTRY] =

      for {
        address1 <- dictionary.inventory1.read(address)
        address2 <- dictionary.inventory2.read(address)
        address3 <- dictionary.inventory3.read(address)
        address4 <- dictionary.inventory4.read(address)
        address5 <- dictionary.inventory5.read(address)
        address6 <- dictionary.inventory6.read(address)
      } yield factory.makeEntry(
        address1,
        address2,
        address3,
        address4,
        address5,
        address6
      )