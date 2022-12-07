package net.mem_memov.binet.hexagon.general.entry

trait SetAddress4[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY,
    address: ADDRESS
  ): ENTRY

  extension (entry: ENTRY)

    def setAddress4(
      address: ADDRESS
    ): ENTRY =

      f(entry, address)
