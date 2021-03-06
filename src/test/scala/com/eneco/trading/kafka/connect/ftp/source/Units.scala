package com.eneco.trading.kafka.connect.ftp.source

import com.typesafe.scalalogging.slf4j.StrictLogging
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

class MonitoredDirectoryTests extends FunSuite with Matchers with BeforeAndAfter with StrictLogging {
  test("Monitoring globs should work") {
    val m0 = new MonitoredPath("/p/my*.csv", false)
    m0.isFileRelevant("/p/my_code.csv") shouldBe true
    m0.isFileRelevant("/p/my_crap.txt") shouldBe false
    m0.isFileRelevant("/q/my_code.csv") shouldBe false

    val m1 = new MonitoredPath("/a/b/*.csv", false)
    m1.isFileRelevant("/a/b/roel.csv") shouldBe true
    m1.isFileRelevant("/a/b/roel.txt") shouldBe false
    m1.isFileRelevant("/a/roel.csv") shouldBe false

    val m2 = new MonitoredPath("/a/b/", false)
    m2.isFileRelevant("/a/b/roel.csv") shouldBe true
    m2.isFileRelevant("/a/b/roel.txt") shouldBe true
    m2.isFileRelevant("/a/b/c/roel.txt") shouldBe false
    m2.isFileRelevant("/a/roel.csv") shouldBe false

    val m3 = new MonitoredPath("/", false)
    m3.isFileRelevant("/my.csv") shouldBe true
    m3.isFileRelevant("/my.txt") shouldBe true

    val m4 = new MonitoredPath("/okay/*.{txt,csv}", false)
    m4.isFileRelevant("/my.csv") shouldBe false
    m4.isFileRelevant("/my.txt") shouldBe false
    m4.isFileRelevant("/okay/my.csv") shouldBe true
    m4.isFileRelevant("/okay/my.txt") shouldBe true
    m4.isFileRelevant("/okay/my.scala") shouldBe false

  }
}
