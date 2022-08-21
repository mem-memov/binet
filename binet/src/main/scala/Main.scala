import net.mem_memov.binet._

@main def hello: Unit =
  val binet = Binet.empty[String]
  val aBits = Bit.collect('a')
  val a = binet.install(aBits, "A case")
  val bBits = Bit.collect('b')
  val b = binet.install(bBits, "B case")
  val ab = a.install(bBits, "AB case")
  val aa = a.install(aBits, "AA case")
  val ba = b.install(aBits, "BA case")
  val bb = b.install(bBits, "BB case")
  println(s"$a")
  println(s"$b")
  println(s"$ab")
  println(s"$ba")










