package com.assignment

import play.api.libs.json._
import play.api.libs.functional.syntax._

import assignment.avro.Message

/**
 * Reads a Message from a JSON String. In case the JSON does not have username
 * and message, default messages are sent
 */
object MessageReader {

    val nameReads: Reads[String] = (JsPath \ "username").read[String]
    val msgReads: Reads[String] = (JsPath \ "message").read[String]

    def readFromText(jsonString: String): Message = {
        val json: JsValue = Json.parse(jsonString)

        val nameResult: JsResult[String] = json.validate[String](nameReads)
        val username = nameResult match {
            case s: JsSuccess[String] => s.get
            case e: JsError => s"Could not read usernamei from $jsonString"
        }

        val msgResult: JsResult[String] = json.validate[String](msgReads)
        val msg = msgResult match {
            case s: JsSuccess[String] => s.get
            case e: JsError => s"Could not read message from $jsonString"
        }
       new Message(username, msg)
    }
}
