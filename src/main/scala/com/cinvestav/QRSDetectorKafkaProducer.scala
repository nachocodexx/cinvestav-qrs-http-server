package com.cinvestav

import cats.effect._
import cats.implicits._
import fs2.kafka._
import scala.concurrent.duration._

object QRSDetectorKafkaProducer {

  val producerSettings =
    ProducerSettings[IO, Option[String], String]
      .withBootstrapServers("localhost:9092")


  def createProducer()(implicit  cs:ContextShift[IO],timer:Timer[IO]) =
    producerStream[IO]
        .using(producerSettings)


}
