package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeStore.trimmer.TrimmerService

class TreeStoreIntegrationSuite extends munit.FunSuite:

  given BlockFactory = BlockFactory()
  given ContentFactory = ContentFactory()
  given PathFactory = PathFactory()
  given factory: AddressFactory = AddressFactory()
  
  test("Store keeps addresses at indices") {

    val blockFactory = BlockFactory()
    (
      for {
        size <- (1 to 10)
        store = TreeStore(
          Vector.fill[Block](size)(blockFactory.emptyBlock),
          new TrimmerService
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          factory.makeAddress(List.fill(size)(UnsignedByte.maximum)).toContent,
          factory.makeAddress(List.fill(size)(UnsignedByte.maximum.decrement)).toContent,
          factory.makeAddress(List.fill(size)(UnsignedByte.minimum)).toContent,
          factory.makeAddress(List.fill(size)(UnsignedByte.minimum.increment)).toContent,
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      val modifiedStore = store.write(destination, content)
      val result = modifiedStore.read(destination)
      assert(result.isEqual(content.toAddress))

    }
  }
