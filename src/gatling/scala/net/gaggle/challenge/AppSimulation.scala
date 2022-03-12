package net.gaggle.challenge

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class AppSimulation extends Simulation {

  private val httpProtocol = http.baseUrl("http://localhost:8080")

  private def allPeople(count: Int): ChainBuilder = {
    repeat(count) {
      exec(
        http("All_People")
          .get("/people/all"))
        .pause(50.millis)
    }
  }

  private def allCrew(count: Int): ChainBuilder = {
    repeat(count) {
      exec(
        http("All_Crew")
          .get("/crew/all"))
        .pause(50.millis)
    }
  }

  private def resume(personId: () => Int, count: Int): ChainBuilder = {
    repeat(count) {
      exec(
        http("Resume")
          .get(s"/crew/person/${personId()}"))
        .pause(50.millis)
    }
  }

  private val allPersonScenario = scenario("All people")
    .exec(allPeople(10000))

  private val allCrewScenario = scenario("All crew")
    .exec(allCrew(10000))

  private val resumeScenario = scenario("Resume")
    .exec(resume(() => 1, 10000))

  setUp(
    List(
      allPersonScenario.inject(atOnceUsers(5)),
      allCrewScenario.inject(atOnceUsers(5)),
      resumeScenario.inject(atOnceUsers(2))
    )
  ).protocols(httpProtocol)

}
