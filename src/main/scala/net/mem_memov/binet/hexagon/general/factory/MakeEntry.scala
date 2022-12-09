package net.mem_memov.binet.hexagon.general.factory

trait MakeEntry[FACTORY, ADDRESS, ENTRY]:

  def f(
    address1: ADDRESS,
    address2: ADDRESS,
    address3: ADDRESS,
    address4: ADDRESS,
    address5: ADDRESS,
    address6: ADDRESS
  ): ENTRY

  extension (factory: FACTORY)

    def makeEntry(
      address1: ADDRESS,
      address2: ADDRESS,
      address3: ADDRESS,
      address4: ADDRESS,
      address5: ADDRESS,
      address6: ADDRESS
    ): ENTRY =

      f(
        address1,
        address2,
        address3,
        address4,
        address5,
        address6
      )

