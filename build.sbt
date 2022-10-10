val scala3Version = "3.1.3"
val zioVersion = "2.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "binet",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "dev.zio" %% "zio" % zioVersion
  )
