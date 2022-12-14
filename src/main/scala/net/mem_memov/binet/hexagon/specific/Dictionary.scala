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
      dictionary.inventories(0).getNext()

  given [ADDRESS, ENTRY, FACTORY](using
    memory.general.inventory.Append[Inventory, ADDRESS],
    memory.general.inventory.GetNext[Inventory, ADDRESS],
    general.factory.MakeEntry[FACTORY, ADDRESS, ENTRY],
    general.factory.TakeZeroAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.dictionary.Append[Dictionary, ADDRESS, ENTRY] with

    override
    def f(
      dictionary: Dictionary,
      addressOptions: (Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS], Option[ADDRESS])
    ): Either[String, (Dictionary, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))] =

      val zeroAddress = factory.takeZeroAddress
      val path = dictionary.inventories(0).getNext()

      val content1 = addressOptions(0).getOrElse(zeroAddress)
      val content2 = addressOptions(1).getOrElse(zeroAddress)
      val content3 = addressOptions(2).getOrElse(zeroAddress)
      val content4 = addressOptions(3).getOrElse(zeroAddress)
      val content5 = addressOptions(4).getOrElse(zeroAddress)
      val content6 = addressOptions(5).getOrElse(zeroAddress)

      for {
        modifiedInventory1 <- dictionary.inventories(0).append(content1)
        modifiedInventory2 <- dictionary.inventories(1).append(content2)
        modifiedInventory3 <- dictionary.inventories(2).append(content3)
        modifiedInventory4 <- dictionary.inventories(3).append(content4)
        modifiedInventory5 <- dictionary.inventories(4).append(content5)
        modifiedInventory6 <- dictionary.inventories(5).append(content6)
      } yield

        val modifiedDictionary = dictionary.copy(
          inventories = Vector(
            modifiedInventory1,
            modifiedInventory2,
            modifiedInventory3,
            modifiedInventory4,
            modifiedInventory5,
            modifiedInventory6
          )
        )

        val entries = (
          factory.makeEntry(general.Position.One, path, content1),
          factory.makeEntry(general.Position.Two, path, content2),
          factory.makeEntry(general.Position.Three, path, content3),
          factory.makeEntry(general.Position.Four, path, content4),
          factory.makeEntry(general.Position.Five, path, content5),
          factory.makeEntry(general.Position.Six, path, content6)
        )

        (modifiedDictionary, entries)
        
  given [ADDRESS, ENTRY](using
    general.dictionary.Append[Dictionary, ADDRESS, ENTRY],
    general.dictionary.GetNextAddress[Dictionary, ADDRESS]
  ): general.dictionary.AppendDot[Dictionary, ENTRY] with

    override 
    def f(
      dictionary: Dictionary
    ): Either[String, (Dictionary, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY))] =

      val dotIdentifierAddress = dictionary.getNextAddress
      
      dictionary.append((Some(dotIdentifierAddress), None, None, None, None, None))
      
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
        modifiedInventory <- dictionary.inventories(entry.getPosition.getIndex).update(entry.getPath, entry.getContent)
      } yield
        val modifiedInventories = dictionary.inventories.updated(entry.getPosition.getIndex, modifiedInventory)
        dictionary.copy(inventories = modifiedInventories)
      
  given [ADDRESS, ENTRY, FACTORY](using
    memory.general.inventory.Read[Inventory, ADDRESS],
    general.factory.MakeEntry[FACTORY, ADDRESS, ENTRY],
    general.entry.GetContent[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.dictionary.Read[Dictionary, ENTRY] with

    override 
    def f(
      dictionary: Dictionary,
      pathEntry: ENTRY
    ): Either[String, (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)] =

      val path = pathEntry.getContent

      for {
        content1 <- dictionary.inventories(0).read(path)
        content2 <- dictionary.inventories(1).read(path)
        content3 <- dictionary.inventories(2).read(path)
        content4 <- dictionary.inventories(3).read(path)
        content5 <- dictionary.inventories(4).read(path)
        content6 <- dictionary.inventories(5).read(path)
      } yield (
        factory.makeEntry(general.Position.One, path, content1),
        factory.makeEntry(general.Position.Two, path, content2),
        factory.makeEntry(general.Position.Three, path, content3),
        factory.makeEntry(general.Position.Four, path, content4),
        factory.makeEntry(general.Position.Five, path, content5),
        factory.makeEntry(general.Position.Six, path, content6),
      )