package net.mem_memov.binet

import scala.annotation.tailrec

case class Binet[A](
  left: Option[Binet[A]],
  right: Option[Binet[A]],
  value: Option[A]
):

  def install(bits: List[Bit], newValue: A): Binet[A] =
    bits match
      case Nil => Binet(newValue)
      case head :: tail =>
        head match
          case False =>
            left match
              case Some(binet) =>
                Binet(Some(binet.install(tail, newValue)), right, value)
              case None =>
                Binet(Some(Binet.empty[A].install(tail, newValue)), right, value)
          case True =>
            right match
              case Some(binet) =>
                Binet(left, Some(binet.install(tail, newValue)), value)
              case None =>
                Binet(left, Some(Binet.empty[A].install(tail, newValue)), value)

  def draw: String =
    val l = left match
      case Some(binet) => binet.draw
      case None => ""
    val r = right match
      case Some(binet) => binet.draw
      case None => ""

    val v = s"${value.getOrElse("v")}"
    s"$l$v$r-\n"

object Binet:
  def empty[A]: Binet[A] =
    Binet[A](None, None, None)

  def apply[A](value: A): Binet[A] =
    Binet[A](None, None, Some(value))
