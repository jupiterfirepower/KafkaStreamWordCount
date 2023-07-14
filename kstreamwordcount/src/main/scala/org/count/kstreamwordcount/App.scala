package org.count.kstreamwordcount

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.apache.kafka.streams.scala.Serdes._

/**
  * A kafka streams application that reads records words from an input topic and counts the occurrence of each word
  * and outputs this count to a different topic
  *
  * Before running this application,
  * start your kafka cluster and create the required topics
  *
  * kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic input-topic
  * kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-wordcount-output
  *
  */
object WordCountApp extends App {

  val config = new Properties()
  // setting offset reset to earliest so that we can re-run the app with same data
  config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, StreamSettings.autoResetConfig)
  config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, StreamSettings.bootstrapServers)
  config.put(StreamsConfig.APPLICATION_ID_CONFIG, StreamSettings.appID)
  // max cache buffering set to 0
  // preferable during development, update value for production use
  config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0L: java.lang.Long)

  //config.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
  //config.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)

  println("Running kafka stream.")

  val wordStream = new KafkaStreams(StreamTopology.topology().build(), config)

  wordStream.cleanUp()
  wordStream.start()

  println(wordStream.toString)

  // attach shutdown handler to catch control-c
  sys.ShutdownHookThread {
    println("Shutting down stream.");
    wordStream.close(10, TimeUnit.SECONDS)
  }
}
