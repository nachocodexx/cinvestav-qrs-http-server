package com.cinvestav.encoders
import java.sql.Timestamp

import com.cinvestav.services.{SensorInformation, SensorPayload}
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.circe.Encoder

object QRSDetectorEncoder {


//    implicit val sensorInformationEncoder:Encoder[SensorDa] =
    implicit val sensorInformationEncoder:Encoder[SensorInformation] =
        Encoder.forProduct3("sensor-id","payload","timestamp") {
            case SensorInformation(sensorId, payload, timestamp)=> (sensorId,payload,timestamp.toString)
        }
//  implicit val sensorInformationEncoder:Encoder[SensorInformation] = Encoder.instance{
//    x=>
//      json"""{
//            "sensor-id":${x.sensorId},
//            "payload": ${x.payload},
//            "timestamp":${x.timestamp.toString}}
//      """
//  }

}
