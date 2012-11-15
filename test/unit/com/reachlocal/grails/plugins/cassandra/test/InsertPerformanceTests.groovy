package com.reachlocal.grails.plugins.cassandra.test

import org.junit.Test;
import static org.junit.Assert.*
import com.reachlocal.grails.plugins.cassandra.test.orm.Visit
import com.reachlocal.grails.plugins.cassandra.test.orm.WebsiteVisit
import com.reachlocal.grails.plugins.cassandra.mapping.DataMapping

class InsertPerformanceTests extends OrmTestCase
{
	static iterations = 5
	static num = 1000

	@Test
	void testOne()
	{
		initialize()

		def visitorId = UUID.timeUUID()
		def v = new WebsiteVisit(
				gmaid:  "USA_1",
				etsSiteId:  "SITE_0001",
				visitorId:  visitorId,
				occurTime: new Date(),
				refClass:  "Organic",
				refType: "Search",
				refName: "Google",
				refKeyword: "Super duper",
				refUrl: "http://www.reachlocal.com",
				pageUrl: "http://docs.mongodb.org/manual/faq/developers/",
				userAgent: "Chrome"
		)
		v.save(nocheck: true)
	}

    @Test
    void testTwo()
    {
        initialize()
		def mapping = new DataMapping()
        for (k in 1..iterations) {
            def t0 = System.currentTimeMillis()
            for (i in 1..num) {
                def v = new Visit(
                        siteName: "SITE_01",
                        occurTime: new Date(),
                        referrerType: "Search",
                        referrerName: "Google",
                        referrerKeyword: "Super duper",
                        referrerUrl: "http://www.reachlocal.com",
                        pageUrl: "http://docs.mongodb.org/manual/faq/developers/",
                        userAgent: "Chrome"
                )
				mapping.dataProperties(v)
            }
            def elapsed = System.currentTimeMillis() - t0
            println "Converted $num records in $elapsed msec, ${(num / (elapsed / 1000.0)).toInteger()} rec/sec, ${elapsed / num} msec/rec"
        }
	}

	@Test
	void testThree()
	{
		def mapping = new DataMapping()
		def v = new Visit(
				siteName: "SITE_01",
				occurTime: new Date(),
				referrerType: "Search",
				referrerName: "Google",
				referrerKeyword: "Super duper",
				referrerUrl: "http://www.reachlocal.com",
				pageUrl: "http://docs.mongodb.org/manual/faq/developers/",
				userAgent: "Chrome"
		)
		def m1 = mapping.dataProperties(v)
		//def m2 = mapping.dataProperties2(v)
		//assertEquals m1.size(), m2.size()
		println m1.size()
		//println m2.size()
		println m1
		//println m2
	}

	@Test
	void testFour()
	{
		initialize()

        for (k in 1..iterations) {
            def num = 1
            def t0 = System.currentTimeMillis()
            for (i in 1..num) {
                def v = new Visit(
                        siteName: "SITE_01",
                        occurTime: new Date(),
                        referrerType: "Search",
                        referrerName: "Google",
                        referrerKeyword: "Super duper",
                        referrerUrl: "http://www.reachlocal.com",
                        pageUrl: "http://docs.mongodb.org/manual/faq/developers/",
                        userAgent: "Chrome"
                )
                v.save(nocheck: true)
            }
            def elapsed = System.currentTimeMillis() - t0
			println "Inserted $num records in $elapsed msec, ${(num / (elapsed / 1000.0)).toInteger()} rec/sec, ${elapsed / num} msec/rec"
        }
	}
}
