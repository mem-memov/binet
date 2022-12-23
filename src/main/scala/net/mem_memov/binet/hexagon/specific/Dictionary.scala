package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Inventory
import net.mem_memov.binet.memory

case class Dictionary(
  inventories: Vector[Inventory]
):
  require(inventories.length == 6)

object Dictionary:

//  import net.mem_memov.binet.memory.Preamble.given

  given [ADDRESS](using
    memory.general.inventory.GetNext[Inventory, ADDRESS]
  ): general.dictionary.GetNextAddress[Dictionary, ADDRESS] with

    override
    def f(
      dictionary: Dictionary
    ): ADDRESS =
      dictionary.inventory(0).getNext()

  given [ADDRESS, ENTRY, FACTORY](using
    memory.general.inventory.Append[Inventory, ADDRESS],
    memory.general.inventory.GetNext[Inventory, ADDRESS],
    general.factory.MakeEntry[FACTORY, ADDRESS, ENTRY],
    general.factory.ZeroAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.dictionary.Append[Dictionary, ADDRESS, ENTRY] with

    override
    def f(
      dictionary: Dictionary,
      addressOptions: (Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS])
    ): Either[String, (Dictionary, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))] =

      val zeroAddress = factory.zeroAddress
      val path = dictionary.inventories(0).getNext()

      for {
        result1 <- dictionary.inventories(0).append(addressOptions(0).getOrElse(zeroAddress))
        (modifiedInventory1, content) = result1
        result2 <- dictionary.inventories(1).append(addressOptions(1).getOrElse(zeroAddress))
        (modifiedInventory2, _) = result2
        result3 <- dictionary.inventories(2).append(addressOptions(2).getOrElse(zeroAddress))
        (modifiedInventory3, _) = result3
        result4 <- dictionary.inventories(3).append(addressOptions(3).getOrElse(zeroAddress))
        (modifiedInventory4, _) = result4
        result5 <- dictionary.inventories(4).append(addressOptions(4).getOrElse(zeroAddress))
        (modifiedInventory5, _) = result5
        result6 <- dictionary.inventories(5).append(addressOptions(5).getOrElse(zeroAddress))
        (modifiedInventory6, _) = result6
      } yield

        val modifiedDictionary = dictionary.copy(
          inventories = (
            modifiedInventory1,
            modifiedInventory2,
            modifiedInventory3,
            modifiedInventory4,
            modifiedInventory5,
            modifiedInventory6
          )
        )

        val entries = (
          factory.makeEntry(general.Position.One, path, content),
          factory.makeEntry(general.Position.Two, path, content),
          factory.makeEntry(general.Position.Three, path, content),
          factory.makeEntry(general.Position.Four, path, content),
          factory.makeEntry(general.Position.Five, path, content),
          factory.makeEntry(general.Position.Six, path, content)
        )

        (modifiedDictionary, entries)
      
  given [ADDRESS, ENTRY](using
    memory.general.inventory.Update[Inventory, ADDRESS],
    general.entry.GetPosition[ENTRY],
    general.entry.GetPath[ENTRY, ADDRESS],
    general.entry.GetContent[ENTRY, ADDRESS]
  ): general.dictionary.Update[Dictionary, ENTRY] with

    override 
    def f(
      dictionary: Dictionary,
      entry: ENTRY
    ): Either[String, Dictionary] =

      for {
        modifiedInventory <- dictionary.inventories(entry.getPosition).update(entry.getPath, entry.getContent)
      } yield
        val modifiedInventories = dictionary.inventories.update(entry.getPosition, modifiedInventory)
        dictionary.copy(inventories = modifiedInventories)
      
  given [ADDRESS, ENTRY, FACTORY](using
    memory.general.inventory.Read[Inventory, ADDRESS],
    general.factory.MakeEntry[FACTORY, ADDRESS, ENTRY]
  )(using
    factory: FACTORY
  ): general.dictionary.Read[Dictionary, ADDRESS, ENTRY] with

    override 
    def f(
      dictionary: Dictionary,
      path: ADDRESS
    ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)] =

      for {
        content1 <- dictionary.inventories(0).read(address)
        content2 <- dictionary.inventories(1).read(address)
        content3 <- dictionary.inventories(2).read(address)
        content4 <- dictionary.inventories(3).read(address)
        content5 <- dictionary.inventories(4).read(address)
        content6 <- dictionary.inventories(5).read(address)
      } yield (
        factory.makeEntry(general.Position.One, path, content1),
        factory.makeEntry(general.Position.Two, path, content2),
        factory.makeEntry(general.Position.Three, path, content3),
        factory.makeEntry(general.Position.Four, path, content4),
        factory.makeEntry(general.Position.Five, path, content5),
        factory.makeEntry(general.Position.Six, path, content6),
      )