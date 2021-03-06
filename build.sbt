name := "Pusher"

organization := "net.ssanj"

version := "0.0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.2.9",
  "org.scalatest"  %% "scalatest"   % "3.0.1"  % "test",
  "org.scalacheck" %% "scalacheck"  % "1.13.4" % "test"
)

scalacOptions ++= Seq(
                      "-unchecked",
                      "-deprecation",
                      "-feature",
                      "-Xfatal-warnings",
                      "-Xlint:_",
                      "-Ywarn-dead-code",
                      "-Ywarn-inaccessible",
                      "-Ywarn-unused-import",
                      "-Ywarn-infer-any",
                      "-Ywarn-nullary-override",
                      "-Ywarn-nullary-unit"
                     )

scalacOptions in (Compile, console) ~= (_.filterNot(Seq("-Xfatal-warnings", "-Ywarn-unused-import") contains _))

scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value

initialCommands := "import net.ssanj.pusher._"

