import com.earldouglas.xwp.JettyPlugin
import org.scalatra.sbt._
import sbt.Keys._
import sbt._

object ImagisterBuild extends Build {
  val ScalatraVersion = "2.4.0-RC2-2"
  lazy val project = Project(
    "imaster-server",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := "bntu",
      name := "imaster-server",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := "2.11.7",
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalatest" % "2.4.0.RC1" % "test",

        "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",

        "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime"
      ),
      javaOptions ++= Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005")
    )
  ).enablePlugins(JettyPlugin)
}
