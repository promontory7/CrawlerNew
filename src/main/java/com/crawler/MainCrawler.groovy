package com.crawler

import com.crawler.pageProcessor.CailianPageProcessor
import com.crawler.pageProcessor.GdGovFinancePageProcess
import com.crawler.pageProcessor.base.BasePageProcessor
import us.codecraft.webmagic.Spider


//Spider.create(new CailianPageProcessor()).thread(5).addUrl(BasePageProcessor.defaultUrl).run()
Spider.create(new GdGovFinancePageProcess()).thread(5).addUrl(BasePageProcessor.defaultUrl).run()