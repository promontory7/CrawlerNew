package CralwerMain

import PagePrecessor.CailianPageProcessor
import us.codecraft.webmagic.Spider


Spider.create(new CailianPageProcessor()).thread(5).addUrl('http://webmagic.io/docs/zh/').run()