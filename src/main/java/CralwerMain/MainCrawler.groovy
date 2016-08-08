package cralwerMain

import pageProcessor.CailianPageProcessor
import pageProcessor.base.BasePageProcessor
import us.codecraft.webmagic.Spider


Spider.create(new CailianPageProcessor()).thread(5).addUrl(BasePageProcessor.defaultUrl).run()