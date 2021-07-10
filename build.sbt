import sbt.Keys.libraryDependencies

lazy val scalatestVersion = "3.2.9"
lazy val scalaxmlVersion = "2.0.0"
lazy val akkaVersion = "2.6.15"
lazy val logbackVersion = "1.2.3"

lazy val root = project
    .in(file("."))
    .settings(
        name := "DungS3",

        version := "0.1",

        scalaVersion := "3.0.0",

        libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % Test,
        libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % scalaxmlVersion,
        libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion,

        libraryDependencies ++= Seq(
            "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
            "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
        ).map(_.cross(CrossVersion.for3Use2_13))
    )
