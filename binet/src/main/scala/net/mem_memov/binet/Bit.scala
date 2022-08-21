package net.mem_memov.binet

import scala.annotation.tailrec

sealed trait Bit

object True extends Bit:
  override def toString = "1"

object False extends Bit:
  override def toString = "0"

object Bit:

  def collect(char: Char): List[Bit] =

    @tailrec
    def inner(char: Char, n: Int, bits: List[Bit]): List[Bit] =
      if n > 16 then
        bits
      else
        val shifted: Int = ((char >> n) << n)
        if shifted == 0 then
          bits
        else
          val original = char.toInt
          if shifted == original then
            inner(shifted.toChar, n + 1, False :: bits)
          else
            inner(shifted.toChar, n + 1, True :: bits)

    inner(char, 1, List[Bit]())