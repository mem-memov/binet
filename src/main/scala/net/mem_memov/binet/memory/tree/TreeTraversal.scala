package net.mem_memov.binet.memory.tree

import scala.collection.immutable.Queue
import net.mem_memov.binet.memory._

case class TreeTraversal(queue: Queue[Element]) extends Traversal:

  override 
  def next: Option[(Element, Traversal)] =

    if queue.isEmpty then
      
      None

    else
      
      val (nextElement, dequeuedQueue) = queue.dequeue
      
      val enqueuedQueue = nextElement.enqueueStock(dequeuedQueue)

      Some((nextElement, TreeTraversal(enqueuedQueue)))
      
