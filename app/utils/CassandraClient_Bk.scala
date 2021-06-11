//package utils
//
//import com.datastax.driver.core.Cluster
//import play.api.Play.current
//
//import scala.concurrent.ExecutionContext
//
//import com.datastax.driver.core.policies.ConstantReconnectionPolicy
//import play.api.Logger
//import play.libs.Akka
//
//object CassandraClient_Bk {
//
//  private val nodes = current.configuration.getStringSeq("CASSANDRA_ENDPOINT").getOrElse(Nil)
//
//  private val nativeProtocolVersion = current.configuration.getString("CASSANDRA_NATIVE_PROTOCOL_VERSION").map(_.trim.toUpperCase).getOrElse("V4") match {
//    case "V1" => V1
//    case "V2" => V2
//    case "V3" => V3
//    case "V4" => V4
//    case "V5" => V5
//    case _ =>
//      Logger.warn(s"native protocol version for cassandra driver is mis-configured, using V4")
//      V4
//  }
//
//  Logger.info(s"using native protocol $nativeProtocolVersion to connect to cassandra")
//
//  private val reconnectionPolicy = new ConstantReconnectionPolicy(1000L)
//
//  private val cluster = Cluster.builder
//    .addContactPoints(nodes: _*)
//    .withProtocolVersion(nativeProtocolVersion)
//    .withReconnectionPolicy(reconnectionPolicy)
//    .build
//
//  private val keySpace = current.configuration.getString("CASSANDRA_KEYSPACE").getOrElse("vcstats")
//
//  val session = cluster.connect(keySpace)
//
//  implicit val ec: ExecutionContext = Akka.system.dispatchers.lookup("contexts.cassandra-pool")
//}
