package com.cinvestav
import cats.effect.{ExitCode, IO, IOApp}

object QRSDetectorServerMain extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    QRSDetectorServerHttp[IO](8080,"0.0.0.0")
      .compile
      .drain
      .as(ExitCode.Success)
}
