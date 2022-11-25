package net.mem_memov.binet.memory.specific.address.specific

object Preamble:

  given net_mem_memov_binet_memory_specific_address_specific_Orderer: Orderer = new Orderer
//  export Orderer.given

  given net_mem_memov_binet_memory_specific_address_specific_Padder: Padder = new Padder
//  export Padder.given

  given net_mem_memov_binet_memory_specific_address_specific_Resizer: Resizer = new Resizer
//  export Resizer.given

  given net_mem_memov_binet_memory_specific_address_specific_Trimmer: Trimmer = new Trimmer
//  export Trimmer.given
