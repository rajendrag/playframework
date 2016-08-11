/*
 * Copyright (C) 2016. envisia GmbH
 * All Rights Reserved.
 */
package play.api.data.format

import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import org.specs2.mutable.Specification

class PlayDateSpec extends Specification {

  "PlayDate.toZonedDateTime(ZoneId)" should {
    "return a valid date" in {
      val date = PlayDate.parse("2016 16:01", DateTimeFormatter.ofPattern("yyyy HH:mm"))

      date.toZonedDateTime(ZoneOffset.UTC).getHour must_=== 16
      date.toZonedDateTime(ZoneOffset.UTC).getYear must_=== 2016
    }
  }

}
