package net.mem_memov.binet.memory.specific.element.specific

object Preamble:

  given Reader = new Reader
  export Reader.given
  
  given Writer = new Writer
  export Writer.given
