lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "org.count",
      scalaVersion := "2.12.18"
    )
  ),
  name := "kstreamwordcount",
  libraryDependencies := Seq(
    library.kafkaClients,
    library.kafkaStreams,
    library.log4jCore,
    library.typesafeConfig,
    library.kafkaTest,
    library.scalaTest
  ),
  assembly / mainClass := Some("org.count.kstreamwordcount.WordCountApp")
  )

lazy val library = new {

  val version = new {
    val kafkaVersion   = "2.3.0"
    val scalaTest      = "3.1.1"
    val log4jCore      = "2.11.1"
    val typesafeConfig = "1.4.0"
  }

  val kafkaClients   = "org.apache.kafka"         % "kafka-clients"        % version.kafkaVersion
  val kafkaStreams   = "org.apache.kafka"         %% "kafka-streams-scala" % version.kafkaVersion
  val log4jCore      = "org.apache.logging.log4j" % "log4j-core"           % version.log4jCore
  val typesafeConfig = "com.typesafe"             % "config"               % version.typesafeConfig
  //val slf4jLib       = "org.slf4j"                % "slf4j-api"            % version.slf4jLib
  //"org.slf4j" % "slf4j-simple" % "1," Provided
  

  val kafkaTest = "org.apache.kafka" % "kafka-streams-test-utils" % version.kafkaVersion
  val scalaTest = "org.scalatest"    %% "scalatest"               % version.scalaTest % "test"
}

assembly / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}