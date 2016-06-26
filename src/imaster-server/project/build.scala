import com.earldouglas.xwp.JettyPlugin
import org.scalatra.sbt._
import sbt.Keys._
import sbt._

object ImagisterBuild extends Build {
  val scalatraVersion = "2.4.0-RC2-2"
  val SlickVersion = "3.1.1"
  lazy val project = Project(
    "imaster-server",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      organization := "bntu",
      name := "imaster-server",
      version := "0.1.0-SNAPSHOT",
      mainClass in Compile := Some("JettyLauncher"),
      scalaVersion := "2.11.7",
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % scalatraVersion,
        "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",

        "org.scalatra" %% "scalatra-json" % scalatraVersion,
        "org.json4s" %% "json4s-jackson" % "3.4.0",

        "org.scalatra" %% "scalatra-auth" % scalatraVersion,
        "com.jason-goodwin" %% "authentikat-jwt" % "0.4.1",

        "com.typesafe.slick" %% "slick" % SlickVersion,
        "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
        "com.h2database" % "h2" % "1.4.181",

        "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container;compile",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",

        "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime"
      ),

      javaOptions ++= Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005")
    )
  ).enablePlugins(JettyPlugin)
}
