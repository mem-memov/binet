package net.mem_memov.binet.memory.general.address

trait PadBig[ADDRESS]:

  def padBigAddress(
    address: ADDRESS,
    target: Int
  ): Either[String, ADDRESS]

  extension (address: ADDRESS)

    def padBig(
      target: Int
    ): Either[String, ADDRESS] =

      padBigAddress(address, target)
