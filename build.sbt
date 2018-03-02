import Dependencies._

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
    name := "africastalking-scala"
  ).aggregate(core,sms,airtime)



lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.10"

lazy val core = (project in file("core")).settings(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
    "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
    scalaTest % Test
  )
)

lazy val sms = (project in file("sms")).settings(
  libraryDependencies ++= Seq(
    scalaTest % Test
  )
).dependsOn(core)

lazy val airtime = (project in file("airtime")).settings(
  libraryDependencies ++= Seq(
    scalaTest % Test
  )
).dependsOn(core)