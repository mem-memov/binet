package net.mem_memov.binet.memory.general.address

trait PadBig[ADDRESS]:

  private[PadBig]
  def f(
    address: ADDRESS,
    target: Int
  ): Either[String, ADDRESS]

  extension (address: ADDRESS)

    def padBig(
      target: Int
    ): Either[String, ADDRESS] =

      f(address, target)
