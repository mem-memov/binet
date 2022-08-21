import net.mem_memov.binet._

@main def hello: Unit =
  val binet = Binet.empty[String]
  val aBits = Bit.collect('a')
  val a = binet.install(aBits, "A")
  val bBits = Bit.collect('b')
  val b = binet.install(bBits, "B")
  val ab = a.install(bBits, "AB case")
  val aa = a.install(aBits, "AA case")
  val ba = b.install(aBits, "BA case")
  val bb = b.install(bBits, "BB case")
  val d = binet.install(List(), "W")
  println(d)
  println(s"${d.draw}")










