package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.level.DefaultLevel

/**
 * Level corresponds to an index of an address.
 * Elements are organized in levels.
 */
trait Level:

  def createStore(): Store

  def createStock(): Stock

  def padBig(content: Address): Either[String, Address]
  
  def toDepth: Depth

object Level:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1

  val top: Level = Level(0)

  def apply(number: Int): Level = DefaultLevel(number)