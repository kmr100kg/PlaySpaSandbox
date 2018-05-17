import Library._
import Library.{testing => test}

name := """play-spa-sandbox"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

libraryDependencies ++= backend ++ security ++ frontend ++ di ++ test