zookeeper-server-start ../config/zookeeper.properties 


kafka-topics 
          --bootstrap-server localhost:9095 \
          --replication-factor 1 \
          --partitions 1 \
          --topic streams-plaintext-input

kafka-topics --create \
          --bootstrap-server localhost:9095 \
          --replication-factor 1 \
          --partitions 1 \
          --topic streams-wordcount-output

cat /tmp/file-input.txt | kafka-console-producer --broker-list localhost:9095 --topic streams-plaintext-input

kafka-console-consumer --bootstrap-server localhost:9095 \
        --topic streams-wordcount-output \
        --from-beginning \
        --formatter kafka.tools.DefaultMessageFormatter \
        --property print.key=true \
        --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
        --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

Start the word count demo from main method

___________________________________________Inner Join Stream______
kafka-topics --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-left-stream-topic
kafka-topics --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-right-stream-topic
kafka-topics.sh --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-stream-stream-inner-join-out
kafka-console-consumer.sh --bootstrap-server localhost:9095 --topic my-kafka-stream-stream-inner-join-out --property print.key=true --property print.timestamp=true
__________________________________________Inner join table
kafka-topics --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-left-table-topic
kafka-topics --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-right-table-topic
kafka-topics.sh --create --bootstrap-server localhost:9095 --replication-factor 1 --partitions 1 --topic my-kafka-table-inner-join-out
kafka-console-consumer.sh --bootstrap-server localhost:9095 --topic my-kafka-table-inner-join-out --property print.key=true --property print.timestamp=true
