package net.mem_memov.binet.memory.general.path

import net.mem_memov.binet.memory.general.Split

trait Shorten[PATH]:

  private[Shorten]
  def f(
    path: PATH
  ): Either[String, Split[PATH]]

  extension (path: PATH)

    def shorten(): Either[String, Split[PATH]] =

      f(path)

