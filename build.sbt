
lazy val Fs2Stream = "co.fs2" %% "fs2-core" % "2.4.4"
lazy val CatsCore = "org.typelevel" %% "cats-core" % "2.1.1"
lazy val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.2"
lazy val Logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
//
lazy val circeVersion = "0.13.0"
lazy val Circe = Seq(
"io.circe" %% "circe-generic" ,
"io.circe" %% "circe-literal" ,
).map(_ % circeVersion)

lazy val http4sVersion = "0.21.8"
lazy val fs2Kafka = "com.github.fd4s" %% "fs2-kafka" % "1.1.0"
lazy val Http4s = Seq (
  "org.http4s" %% "http4s-dsl" ,
  "org.http4s" %% "http4s-blaze-server" ,
  "org.http4s" %% "http4s-blaze-client",
  "org.http4s" %% "http4s-circe"
).map(_% http4sVersion)


lazy val qrsDetectorServer = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "cinvestav-qrs-dector-server",
    version := "0.1",
    scalaVersion := "2.12.10",
    libraryDependencies ++= Seq(
      Fs2Stream,
      CatsCore,
      Logback,
      fs2Kafka
    ) ++ Http4s ++ Circe,
    libraryDependencies += ScalaTest % Test
  )

