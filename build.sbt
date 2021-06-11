
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "ScalaWebExample",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
//      "com.typesafe.akka" %% "akka-actor" % "2.6.14"
      "com.typesafe.akka" %% "akka-actor" % "2.6.14",
      "com.typesafe.akka" %% "akka-slf4j" % "2.6.14",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",
      "com.typesafe.akka" %% "akka-protobuf-v3" % "2.6.14",
      "com.typesafe.akka" %% "akka-stream" % "2.6.14",
      "com.typesafe.akka" %% "akka-serialization-jackson" % "2.6.14"


),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )

