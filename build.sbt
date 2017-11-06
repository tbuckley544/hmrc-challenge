
name := "hmrc"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.typesafe.akka" %% "akka-actor"  % "2.5.6",
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10" % Test,
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

enablePlugins(DockerPlugin)

mainClass in assembly := Some("hmrc.Application")

dockerfile in docker := {
  assembly.value
  val artifact = (assemblyOutputPath in assembly).value
  val targetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("openjdk:8-jre")
    add(assembly.value, targetPath)
    cmd("java", "-jar", targetPath)
  }
}

imageNames in docker := Seq(ImageName("challenge:latest"))