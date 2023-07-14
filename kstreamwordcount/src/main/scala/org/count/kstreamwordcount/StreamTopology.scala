package org.count.kstreamwordcount

import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.{KStream, KTable}

object StreamTopology {

  def topology(): StreamsBuilder = {
    val builder: StreamsBuilder = new StreamsBuilder
    val textLines: KStream[String, String] =
      builder.stream[String, String](StreamSettings.inputTopic)

    println("Output kafka stream.")

    val wordCount: KTable[String, Long] = textLines
      .flatMapValues(words => words.toLowerCase.split("\\W+"))
      .groupBy((_, word) => word)
      .count()

    wordCount.toStream.print(Printed.toSysOut[String, Long])
    wordCount.toStream.to(StreamSettings.outputTopic)

    builder
  }
}