name := "cats-sandbox"

version := "0.1"

scalaVersion := "2.12.8"

val circeVersion = "0.10.0"
val featherbedVersion = "0.3.3"
val finagleVersion = "19.5.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-free" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0",
  "com.vladkopanev" %% "cats-saga" % "0.2.3",
  "com.beachape" %% "enumeratum" % "1.5.13",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.twitter" %% "finagle-http" % finagleVersion,
  "io.github.finagle" % "featherbed-core_2.12" % featherbedVersion,
  "io.github.finagle" % "featherbed-circe_2.12" % featherbedVersion
)