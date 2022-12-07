package net.mem_memov.binet.hexagon.general.entry

trait SetAddress5[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY,
    address: ADDRESS
  ): ENTRY

  extension (entry: ENTRY)

    def setAddress5(
      address: ADDRESS
    ): ENTRY =

      f(entry, address)
