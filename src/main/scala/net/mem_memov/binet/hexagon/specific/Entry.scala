package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.hexagon.general.Position
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Entry(
  position: Position,
  path: Address,
  content: Address
)

object Entry:

  given general.entry.GetPosition[Entry] with

    override
    def f(
      entry: Entry
    ): Position =

      entry.position

  given general.entry.GetPath[Entry, Address] with

    override
    def f(
      entry: Entry
    ): Address =

      entry.path

  given general.entry.GetContent[Entry, Address] with

    override
    def f(
      entry: Entry
    ): Address =

      entry.content

  given [NETWORK](using
    memory.general.address.Increment[Address],
    general.network.UpdateEntry[NETWORK, Entry]
  ): general.entry.IncrementContent[Entry, NETWORK] with

    override
    def f(
      entry: Entry,
      network: NETWORK
    ): Either[String, (NETWORK, Entry)] =

      val modifiedContent = entry.content.increment
      val modifiedEntry = entry.copy(content = modifiedContent)

      for {
        modifiedNetwork <- network.updateEntry(modifiedEntry)
      } yield (modifiedNetwork, modifiedEntry)

  given [NETWORK](using
    memory.general.address.Decrement[Address],
    general.network.UpdateEntry[NETWORK, Entry]
  ): general.entry.DecrementContent[Entry, NETWORK] with

    override
    def f(
      entry: Entry,
      network: NETWORK
    ): Either[String, (NETWORK, Entry)] =

      for {
        modifiedContent <- entry.content.decrement
        modifiedEntry = entry.copy(content = modifiedContent)
        modifiedDictionary <- network.updateEntry(modifiedEntry)
      } yield (modifiedDictionary, modifiedEntry)
  
  given [DICTIONARY](using
    general.dictionary.Update[DICTIONARY, Entry]
  ): general.entry.Save[Entry, DICTIONARY] with

    override
    def f(
      entry: Entry,
      dictionary: DICTIONARY
    ): Either[String, DICTIONARY] =

      dictionary.update(entry)

  given (using
    Ordering[Address]
  ): general.entry.ContentIsLarger[Entry] with

    override
    def f(
      entry: Entry,
      theOther: Entry
    ): Boolean =

      import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

      entry.content > theOther.content

  given (using
    memory.general.address.IsZero[Address]
  ): general.entry.IsContentEmpty[Entry] with

    override
    def f(
      entry: Entry
    ): Boolean =

      entry.content.isZero