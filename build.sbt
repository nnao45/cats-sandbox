name := "cats-sandbox"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "com.beachape" %% "enumeratum" % "1.5.13",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-free" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0",
  "com.vladkopanev" %% "cats-saga" % "0.2.3"
)