name := "cats-sandbox"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.beachape" %% "enumeratum" % "1.5.13",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-free" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0"
)