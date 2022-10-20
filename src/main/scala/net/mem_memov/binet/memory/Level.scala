package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.level.DefaultLevel

/**
 * Level corresponds to an index of an address.
 * Elements are organized in levels.
 */
trait Level:

  def createStore(): Store

  def createStock(): Stock
  
  def toDepth: Depth
