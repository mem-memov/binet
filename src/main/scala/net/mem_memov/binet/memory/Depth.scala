package net.mem_memov.binet.memory

/**
 * Depth indicates the level a store was written at.
 * It facilitates store expansion.
 */
trait Depth:
  
  def expandStore(store: Store): Store
