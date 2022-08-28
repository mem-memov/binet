package net.mem_memov.binet.hexagon

import scala.annotation.tailrec

class Edge(
  private val network: Network,
  private val arrow: Arrow
):
  
  def findSource(f: Arrow => Boolean): Unit =
    var found = false
    var next = Option(arrow)
    while !found || next.isEmpty do
      found = next.exists(f)
      next = if !found then next.flatMap(_.nextSourceArrow) else next
      
  def hasSourceDot(source: Dot): Boolean =

    if arrow.hasSourceDot(source) then
      true
    else
      var has = false
      var next: Option[Arrow] = arrow.nextSourceArrow
      while !has || next.nonEmpty do
        has = next.exists(arrow => arrow.hasSourceDot(source))
      has

object Edge:

  def apply(network: Network, arrow: Arrow): Edge =
    new Edge(network, arrow)
