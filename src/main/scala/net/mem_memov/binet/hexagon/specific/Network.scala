package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory

case class Network(
  dictionary: Dictionary
)

object Network:

  given [ADDRESS, ARROW, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ADDRESS, ENTRY],
    general.factory.MakeArrow[FACTORY, ADDRESS, ARROW, ENTRY]
  )(using
    factory: FACTORY
  ): general.network.CreateArrow[Network, ARROW, ENTRY] with

    override
    def f(
      network: Network,
      entry: ENTRY
    ): Either[String, (Network, ARROW)] =

      for {
        appendResult <- network.dictionary.append(entry)
        (modifiedDictionary, address) = appendResult
      } yield
        val arrow = factory.makeArrow(address, entry)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, arrow)

  given [ADDRESS, ENTRY, FACTORY](using
    general.dictionary.Append[Dictionary, ADDRESS, ENTRY],
    general.dictionary.GetNextAddress[Dictionary, ADDRESS],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.factory.MakeDot[FACTORY, ADDRESS, Dot, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.network.CreateDot[Network, Dot] with

    override
    def f(
      network: Network
    ): Either[String, (Network, Dot)] =

      val entry = factory.emptyEntry()
        .setAddress1(network.dictionary.getNextAddress)

      for {
        appendResult <- network.dictionary.append(entry)
        (modifiedDictionary, address) = appendResult
      } yield
        val dot = factory.makeDot(address, entry)
        val modifiedNetwork = network.copy(dictionary = modifiedDictionary)
        (modifiedNetwork, dot)

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeArrow[FACTORY, Address, Arrow, ENTRY],
    memory.general.address.IsZero[Address],
    general.arrow.IsArrow[Arrow]
  )(using
    factory: FACTORY
  ): general.network.ReadArrow[Network, Address, Arrow] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Arrow] =

      for {
        entry <- network.dictionary.read(address)
        arrow <-
          val arrow = factory.makeArrow(address, entry)
          if arrow.isArrow then
            Right(arrow)
          else
            Left("Not an arrow")
      } yield arrow

  given [ENTRY, FACTORY](using
    general.dictionary.Read[Dictionary, Address, ENTRY],
    general.factory.MakeDot[FACTORY, Address, Dot, ENTRY],
    memory.general.address.IsZero[Address],
    general.dot.IsDot[Dot]
  )(using
    factory: FACTORY
  ): general.network.ReadDot[Network, Address, Dot] with

    override
    def f(
      network: Network,
      address: Address
    ): Either[String, Dot] =

      for {
        entry <- network.dictionary.read(address)
        dot <-
          val dot = factory.makeDot(address, entry)
          if dot.isDot then
            Right(dot)
          else
            Left("Not a dot")
      } yield dot

  given [ADDRESS, ENTRY](using
    general.dictionary.Update[Dictionary, ADDRESS, ENTRY],
    general.arrow.GetAddress[Arrow, ADDRESS],
    general.arrow.GetEntry[Arrow, ENTRY]
  ): general.network.UpdateArrow[Network, Arrow] with

    override
    def f(
      network: Network,
      arrow: Arrow
    ): Either[String, Network] =

      for {
        modifiedNeDictionary <- network.dictionary.update(arrow.getAddress, arrow.getEntry)
      } yield network.copy(dictionary = modifiedNeDictionary)

  given [ADDRESS, ENTRY](using
    general.dictionary.Update[Dictionary, ADDRESS, ENTRY],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.GetEntry[Dot, ENTRY],
  ): general.network.UpdateDot[Network, Dot] with

    override
    def f(
      network: Network,
      dot: Dot
    ): Either[String, Network] =

      for {
        modifiedNeDictionary <- network.dictionary.update(dot.getAddress, dot.getEntry)
      } yield network.copy(dictionary = modifiedNeDictionary)

