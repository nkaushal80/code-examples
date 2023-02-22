Properties props = new Properties();

props.put("bootstrap.servers", "localhost:9092");

props.put("group.id", "test-group");

props.put("enable.auto.commit", "true");

props.put("auto.commit.interval.ms", "1000");

props.put("max.poll.records", "100");

props.put("max.poll.interval.ms", "1000");

props.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RoundRobinAssignor");
