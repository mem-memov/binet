package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.{Address, Inventory}

case class Dictionary(
  optionAddress: Option[Address],
  optionEntry: Option[Entry],
  inventory1: Inventory,
  inventory2: Inventory,
  inventory3: Inventory,
  inventory4: Inventory,
  inventory5: Inventory,
  inventory6: Inventory
)

object Dictionary:

  import net.mem_memov.binet.memory.Preamble.given

  given general.dictionary.GetNextAddress[Dictionary, Address] with

    override
    def f(
      dictionary: Dictionary
    ): Address =

      dictionary.inventory1.next

  given general.dictionary.GetAddress[Dictionary, Address] with

    override
    def f(dictionary: Dictionary): Either[String, Address] =

      dictionary.optionAddress match
        case Some(address) => Right(address)
        case None => Left("No address")

  given general.dictionary.GetEntry[Dictionary, Entry] with

    override
    def f(dictionary: Dictionary): Either[String, Entry] =

      dictionary.optionEntry match
        case Some(entry) => Right(entry)
        case None => Left("No entry")

  given general.dictionary.Append[Dictionary, Entry] with

    override
    def f(
      dictionary: Dictionary,
      entry: Entry
    ): Either[String, Dictionary] =

      val address = dictionary.inventory1.next

      for {
        modifiedInventory1 <- dictionary.inventory1.append(entry.address1)
        modifiedInventory2 <- dictionary.inventory2.append(entry.address2)
        modifiedInventory3 <- dictionary.inventory3.append(entry.address3)
        modifiedInventory4 <- dictionary.inventory4.append(entry.address4)
        modifiedInventory5 <- dictionary.inventory5.append(entry.address5)
        modifiedInventory6 <- dictionary.inventory6.append(entry.address6)
      } yield Dictionary(
        Some(address),
        Some(entry),
        modifiedInventory1,
        modifiedInventory2,
        modifiedInventory3,
        modifiedInventory4,
        modifiedInventory5,
        modifiedInventory6,
      )
      
  given general.dictionary.Update[Dictionary, Address, Entry] with

    override 
    def f(
      dictionary: Dictionary, 
      address: Address, 
      entry: Entry
    ): Either[String, Dictionary] =

      for {
        modifiedInventory1 <- dictionary.inventory1.update(address, entry.address1)
        modifiedInventory2 <- dictionary.inventory2.update(address, entry.address2)
        modifiedInventory3 <- dictionary.inventory3.update(address, entry.address3)
        modifiedInventory4 <- dictionary.inventory4.update(address, entry.address4)
        modifiedInventory5 <- dictionary.inventory5.update(address, entry.address5)
        modifiedInventory6 <- dictionary.inventory6.update(address, entry.address6)
      } yield Dictionary(
        Some(address),
        Some(entry),
        modifiedInventory1,
        modifiedInventory2,
        modifiedInventory3,
        modifiedInventory4,
        modifiedInventory5,
        modifiedInventory6,
      )
      
  given general.dictionary.Read[Dictionary, Address, Entry] with

    override 
    def f(
      dictionary: Dictionary, 
      address: Address
    ): Either[String, Entry] =

      for {
        address1 <- dictionary.inventory1.read(address)
        address2 <- dictionary.inventory2.read(address)
        address3 <- dictionary.inventory3.read(address)
        address4 <- dictionary.inventory4.read(address)
        address5 <- dictionary.inventory5.read(address)
        address6 <- dictionary.inventory6.read(address)
      } yield Entry(
        address1,
        address2,
        address3,
        address4,
        address5,
        address6
      )