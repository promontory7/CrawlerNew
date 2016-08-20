package com.crawler.pageProcessor

import com.crawler.MyUtils.CacheUncomplateMap
import com.crawler.MyUtils.CompleteUrlFromDB
import com.crawler.bean.Project
import com.crawler.extractProcessor.CommonExtractUtils
import com.crawler.pageProcessor.base.BasePageProcessor
import org.jsoup.nodes.Element
import us.codecraft.webmagic.Page

/**
 * 广东省政府采购网
 */
class GdGovFinancePageProcess extends BasePageProcessor {
    private URL_GUANGDONG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=&pageIndex='
    private URL_FOSHANG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0105&pageIndex='
    private URL_MEIZHOU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0108&pageIndex='
    private URL_HEYUAN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0107&pageIndex='
    private URL_YANGJIANG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0114&pageIndex='
    private URL_QINGYUAN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0118&pageIndex='
    private URL_DONGGUAN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0111&pageIndex='
    private URL_ZHONGSHAN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0112&pageIndex='
    private URL_JIEYANG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0120&pageIndex='
    private URL_YUNFU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0121&pageIndex='
    private URL_SHANTOU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0104&pageIndex='
    private URL_JIANGMEN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0113&pageIndex='
    private URL_ZHANJIANG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0115&pageIndex='
    private URL_MAOMING = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0116&pageIndex='
    private URL_ZHUHAI = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0103&pageIndex='
    private URL_GUANGHZOU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0101&pageIndex='
    private URL_ZHAOQING = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0117&pageIndex='
    private URL_CHAOZHOU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0119&pageIndex='
    private URL_SHENZHEN = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0102&pageIndex='
    private URL_SHANWEI = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0110&pageIndex='
    private URL_HUIZHOU = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0109&pageIndex='
    private URL_SHAOGUANG = 'http://www.gdgpo.gov.cn/queryMoreInfoList/channelCode/0005.html?regionIds=0106&pageIndex='

    @Override
    def setBaseUrlAndSum() {
        [[URL_GUANGDONG + pageNumSymbol, 1, 1],
         [URL_GUANGHZOU + pageNumSymbol, 1, 1],
         [URL_SHENZHEN + pageNumSymbol, 1, 1],
         [URL_ZHUHAI + pageNumSymbol, 1, 1],
         [URL_SHANTOU + pageNumSymbol, 1, 1],
         [URL_FOSHANG + pageNumSymbol, 1, 1],
         [URL_SHAOGUANG + pageNumSymbol, 1, 1],
         [URL_HEYUAN + pageNumSymbol, 1, 1],
         [URL_MEIZHOU + pageNumSymbol, 1, 1],
         [URL_HUIZHOU + pageNumSymbol, 1, 1],
         [URL_SHANWEI + pageNumSymbol, 1, 1],
         [URL_DONGGUAN + pageNumSymbol, 1, 1],
         [URL_ZHONGSHAN + pageNumSymbol, 1, 1],
         [URL_JIANGMEN + pageNumSymbol, 1, 1],
         [URL_YANGJIANG + pageNumSymbol, 1, 1],
         [URL_ZHANJIANG + pageNumSymbol, 1, 1],
         [URL_MAOMING + pageNumSymbol, 1, 1],
         [URL_ZHAOQING + pageNumSymbol, 1, 1],
         [URL_QINGYUAN + pageNumSymbol, 1, 1],
         [URL_CHAOZHOU + pageNumSymbol, 1, 1],
         [URL_JIEYANG + pageNumSymbol, 1, 1],
         [URL_YUNFU + pageNumSymbol, 1, 1]
        ]
    }

    @Override
    def setTypeOfUrl() {
        ["http://www\\.gdgpo\\.gov\\.cn/queryMoreInfoList/channelCode/0005\\.html\\.*",
         "http://www\\.gdgpo\\.gov\\.cn/showNotice/id/\\.*"]
    }

    @Override
    def listPrecess(Page page) {
        def ul = document.getElementsByClass('m_m_c_list')
        if (ul) {
            for (Element li : ul.select('li')) {

                Project project = new Project();
                project.publicStart = li.select('em').text()
                project.url = li.select('a').get(1).attr('href').trim()
                project.projectName = li.select('a').attr('title').trim()
//                project.websiteType = CommonExtractUtils.removeSymbolFromString(li.select('span').text(), '[', ']')
                project.websiteType = getWebsiteTypeByUrl(project.url)


                println(project)
                if (!CompleteUrlFromDB.instance.isInCompleteUrl(project.url)) {
                    page.addTargetRequest(project.url)
                    CacheUncomplateMap.instance.saveToMap(project.url, project)
                }
            }
        }
    }

    def String getWebsiteTypeByUrl(String s) {
        if (s.contains(URL_FOSHANG)) {

        }
    }

    @Override
    def detailProcess(Page page) {
        project = CacheUncomplateMap.instance.getFromMap(page.getUrl().toString().trim())
        println('详情页处理')
    }

    @Override
    def parseContent(String content) {
        return null
    }
}
