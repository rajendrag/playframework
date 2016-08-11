/*
 * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
 */
package controllers

import java.time._

import org.specs2.mutable.Specification
import java.util.Date
import java.time.format.DateTimeFormatter

class AssetsInfoSpec extends Specification {

  "AssetInfo.parseModifiedDate" should {

    def parseAndReformat(s: String): Option[String] = {
      val parsed: Option[Date] = AssetInfo.parseModifiedDate(s)
      parsed.map { date =>
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000z")
          .format(ZonedDateTime.ofInstant(date.toInstant, ZoneOffset.UTC))
      }
    }

    "parse date from RFC2616" in {
      parseAndReformat("Sat, 29 Oct 1994 19:43:31 GMT") must beSome("1994-10-29T19:43:31.000Z")
    }

    "parse non-standard date without GMT" in {
      parseAndReformat("Sat, 18 Oct 2014 20:41:26") must beSome("2014-10-18T20:41:26.000Z")
    }

    "parse date with extra length attribute (IE 9-11)" in {
      parseAndReformat("Sat, 18 Oct 2014 20:41:26; length=1323") must beSome("2014-10-18T20:41:26.000Z")
    }

    "parse non-standard date with timezone (Chrome 39/Windows 8.1)" in {
      parseAndReformat("Wed Jan 07 2015 22:54:20 GMT-0800 (Pacific Standard Time)") must beSome("2015-01-08T06:54:20.000Z")
    }

    "not parse empty date header" in {
      parseAndReformat("") must beNone
    }

  }

}
