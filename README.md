# KafkaStreamWordCount<br>
1) Download Kafka<br>
https://kafka.apache.org/downloads<br>
https://downloads.apache.org/kafka/3.5.0/kafka_2.13-3.5.0.tgz<br>
# extract<br>
tar -xzf kafka_2.13-3.5.0.tgz -C ~/KAFKA/kafka_2.13-3.5.0/<br>
cd ~/KAFKA/kafka_2.13-3.5.0/<br>
<br>
## Start Zookeeper and Kafka Services<br>
# Start the ZooKeeper service<br>
bin/zookeeper-server-start.sh config/zookeeper.properties<br>
<br>
# Start the Kafka broker service<br>
bin/kafka-server-start.sh config/server.properties<br>
<br>
# List topics to verify that the services are running. This command should return no results<br>
bin/kafka-topics.sh --list --bootstrap-server localhost:9092<br>
<br>
## Create a Topic with Apache Kafka<br>
# Create the wcount-... topics<br>
bin/kafka-topics.sh --create --topic wcount-input-topic --bootstrap-server localhost:9092<br>
bin/kafka-topics.sh --create --topic wcount-output-topic --bootstrap-server localhost:9092<br>
<br>
# List the topics again<br>
bin/kafka-topics.sh --list --bootstrap-server localhost:9092<br>
<br>
# Run Producer.<br>
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic wcount-input-topic<br>
<br>
From root dir change dir to kstreamwordcount/<br>
<br>
Run build command<br>
sbt clean<br>
sbt assembly<br>
(create flat uber jar)<br>
<br>
# Run<br>
java -jar target/scala2.12/kstreamwordcount-assembly-0.1.0-SNAPSHOT.jar<br>
<br>
# Run Consumer.
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \<br>
--topic wcount-output-topic \<br>
--from-beginning \<br>
--formatter kafka.tools.DefaultMessageFormatter \<br>
--property print.key=true \<br>
--property print.value=true \<br>
--property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \<br>
--property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer<br>
<br>
Type sentenses in producer.<br>





