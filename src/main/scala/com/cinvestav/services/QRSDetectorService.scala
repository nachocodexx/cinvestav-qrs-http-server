package com.cinvestav.services

import java.sql.Timestamp
import java.util.UUID

import cats.Monad
import cats.implicits._
import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Effect, IO, Timer}
import fs2.concurrent.Queue
import fs2.Pipe
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.server._
import org.http4s.dsl.io._
import io.circe._
import io.circe.literal._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.server.websocket.WebSocketBuilder
import org.http4s.websocket.WebSocketFrame
import org.http4s.websocket.WebSocketFrame._
import com.cinvestav.encoders.QRSDetectorEncoder._

import fs2.{Stream,Pipe}
import scala.concurrent.duration._
import scala.language.postfixOps
case class SensorPayload(data:String)
case class SensorInformation(sensorId:String, payload:SensorPayload, timestamp:Timestamp)

class QRSDetectorService[F[_]:Effect](implicit F:ConcurrentEffect[F], timer:Timer[F]) extends Http4sDsl[F]  {
  def routes(implicit timer:Timer[F]):HttpRoutes[F]= {
    Router[F](
      "/"-> rootRoutes
    )
  }

  def rootRoutes()(implicit  timer:Timer[F]):HttpRoutes[F] =
      HttpRoutes.of[F]{
        case GET -> Root / "hello" =>
          Ok("Hello world.")
//        case  GET -> Root =>
//          val sensorId= UUID.randomUUID().toString
//          val timestamp = new Timestamp(System.currentTimeMillis)
//          val payload = SensorPayload("FAKE DATA")
//          val sensorPayload = SensorInformation(sensorId,payload,timestamp)
//          Ok(sensorPayload.asJson)
        case GET -> Root / "ws" =>
          val toClient: Stream[F, WebSocketFrame] =
            Stream.awakeEvery[F](1.seconds).map(d => Text(s"Ping! $d"))
          val fromClient: Pipe[F, WebSocketFrame, Unit] = _.evalMap {
            case Text(t, _) => F.delay(println(t))
            case f => F.delay(println(s"Unknown type: $f"))
          }
          WebSocketBuilder[F].build(toClient, fromClient)

      }
}
object QRSDetectorService {
  def apply[F[_]:ConcurrentEffect:Timer](): QRSDetectorService[F] =
    new QRSDetectorService[F]()

}
