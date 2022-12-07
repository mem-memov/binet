package net.mem_memov.binet.hexagon.general.entry

trait SetAddress2[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY,
    address: ADDRESS
  ): ENTRY

  extension (entry: ENTRY)

    def setAddress2(
      address: ADDRESS
    ): ENTRY =

      f(entry, address)
