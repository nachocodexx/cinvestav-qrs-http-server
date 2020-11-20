package com.cinvestav

import cats.effect._
import cats.implicits._
import org.http4s.HttpApp
import org.http4s.dsl.io._
import org.http4s.syntax.kleisli._
import org.http4s.server.{Router, Server}
import org.http4s.server.blaze.BlazeServerBuilder
import com.cinvestav.services.QRSDetectorService
import scala.concurrent.ExecutionContext.Implicits.global
import fs2.Stream

trait QRSServer[F[_]]{
  def run():F[Unit]
}

class QRSDetectorServerHttp[F[_]:ConcurrentEffect](port:Int,hostname:String) {
   def run(httpApp:HttpApp[F])(implicit cs:ContextShift[F],timer:Timer[F])
   : BlazeServerBuilder[F] =
    BlazeServerBuilder[F](global)
      .bindHttp(port,hostname)
      .withHttpApp(httpApp)
}
object
QRSDetectorServerHttp {
  def httpApp[F[_]:ConcurrentEffect:Timer]: HttpApp[F] =
    Router(
      "qrs" -> QRSDetectorService[F]().routes
    ).orNotFound

  def apply[F[_]:ConcurrentEffect:ContextShift:Timer](port:Int,hostname:String): Stream[F, ExitCode] = {
    BlazeServerBuilder[F](global)
      .bindHttp(port)
      .withHttpApp(httpApp[F])
      .serve
//      .compile
//      .drain

  }

}
