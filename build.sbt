import sbt.Keys.{fork, libraryDependencies, scalacOptions}

lazy val scalatestVersion = "3.2.9"
lazy val scalaxmlVersion = "2.0.0"
lazy val akkaVersion = "2.6.15"
lazy val logbackVersion = "1.2.3"
lazy val scalafxVersion = "16.0.0-R24"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

lazy val root = project
    .in(file("."))
    .settings(
        name := "DungS3",

        version := "0.1",

        scalaVersion := "3.0.1",

        scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature"),

        libraryDependencies += "org.scalatest" %% "scalatest" % scalatestVersion % Test,
        libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % scalaxmlVersion,
        libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion,
        libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R24",

        libraryDependencies ++= Seq(
            "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
            "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
        ).map(_.cross(CrossVersion.for3Use2_13)),

        // Add JavaFX dependencies
        libraryDependencies ++= {
            // Determine OS version of JavaFX binaries
            lazy val osName = System.getProperty("os.name") match {
                case n if n.startsWith("Linux") => "linux"
                case n if n.startsWith("Mac") => "mac"
                case n if n.startsWith("Windows") => "win"
                case _ => throw new Exception("Unknown platform!")
            }
            Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
                .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
        }
    )
