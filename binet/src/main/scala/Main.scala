@main def hello: Unit = 
  println("Hello world!")
  println(msg)

def msg = "I was compiled by Scala 3. :)"

sealed trait Binet

sealed trait Bit
object Zero extends Bit
object One extends Bit

case class Sequence[A](previous: Option[A], next: Option[A])

case class Number(bit: Bit, sequence: Sequence[Number])
object Number:
  def fromChar(char: Char) =
    if ((char >> 1) << 1) == char then Zero else One

//case class Word(characters: List[Character])
//
//case class Phrase(words: List[Word])




