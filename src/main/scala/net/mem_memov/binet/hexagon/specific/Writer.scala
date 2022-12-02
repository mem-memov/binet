package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.memory
import net.mem_memov.binet.hexagon.general

class Writer

object Writer:
  
  given net_mem_memov_binet_hexagon_specific_Writer: Writer = new Writer

  given [ADDRESS, DICTIONARY, ENTRY](using
    memory.general.address.IsZero[ADDRESS],
    general.dictionary.Read[DICTIONARY, ADDRESS, ENTRY]
  ): general.writer.GetEntry[Writer, ADDRESS, DICTIONARY, ENTRY] with

    override
    def f(
      dictionary: DICTIONARY,
      address: ADDRESS
    ): Either[String, Option[ENTRY]] =

      if address.isZero then
        Right(None)
      else
        for {
          foundEntry <- dictionary.read(address)
        } yield Some(foundEntry)

  given [ADDRESS, DICTIONARY, ENTRY](using
    general.dictionary.Update[DICTIONARY, ADDRESS, ENTRY]
  ): general.writer.UpdateEntry[Writer, ADDRESS, DICTIONARY, ENTRY] with

    override
    def f(
      dictionary: DICTIONARY,
      address: ADDRESS,
      entry: ENTRY
    ): Either[String, DICTIONARY] =

      for {
        dictionary <- dictionary.update(address, entry)
      } yield dictionary
