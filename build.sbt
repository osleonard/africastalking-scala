import Dependencies._

lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.10"

lazy val commonSettings = Seq(
  organization := "com.africastalking",
  organizationName := "Africas Talking Ltd",
  scalaVersion := "2.12.4",
  version      := "0.1.0-SNAPSHOT",
  startYear := Some(2018)
)


lazy val root = (project in file(".")).
  settings(
    inThisBuild(commonSettings),
    name := "africastalking-scala",
    libraryDependencies ++= Seq(
      "ch.qos.logback"             %  "logback-classic"      % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging"        % "3.8.0",
      "com.typesafe.akka"          %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-stream"          % akkaVersion,
      "com.typesafe.akka"          %% "akka-actor"           % akkaVersion,
      "com.typesafe.akka"          %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka"          %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka"          %% "akka-stream-testkit"  % akkaVersion     % Test,
      scalaTest                    % Test,
      scalaMock % Test
    )
  )