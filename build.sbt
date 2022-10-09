lazy val scala3Version = "3.1.3"
lazy val zioVersion = "2.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "binet",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "dev.zio" %% "zio" % zioVersion,
    libraryDependencies += "dev.zio" %% "zio-test-junit" % zioVersion,

    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
