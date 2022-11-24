package net.mem_memov.binet.memory.specific.element.specific

object Preamble:

  given net_mem_memov_binet_memory_specific_element_specific_Reader: Reader = new Reader
  export Reader.given
  
  given net_mem_memov_binet_memory_specific_element_specific_Writer: Writer = new Writer
  export Writer.given
