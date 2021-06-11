package utils

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.policies.ConstantReconnectionPolicy

object CassandraClient {

  private val reconnectionPolicy = new ConstantReconnectionPolicy(1000L)

  private val cluster = Cluster.builder()
    .addContactPoint("localhost") // cassandra.iviws.local
    .withReconnectionPolicy(reconnectionPolicy)
    .withCredentials("","")
    //.withPort(9042)
    .build()

  val session = cluster.connect()
}
