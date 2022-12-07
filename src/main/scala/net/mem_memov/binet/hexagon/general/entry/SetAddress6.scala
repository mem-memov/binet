package net.mem_memov.binet.hexagon.general.entry

trait SetAddress6[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY,
    address: ADDRESS
  ): ENTRY

  extension (entry: ENTRY)

    def setAddress6(
      address: ADDRESS
    ): ENTRY =

      f(entry, address)
