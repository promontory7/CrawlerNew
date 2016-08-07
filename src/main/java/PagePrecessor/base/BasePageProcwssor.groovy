package PagePrecessor.base

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.processor.PageProcessor

/**
 * 处理基础类
 */
abstract class BasePageProcwssor implements PageProcessor {
    String defaultUrl = 'http://webmagic.io/docs/zh/'
    String listUrl      //列表页
    String detailUrl    //详情页

    String pageNumSymbol = 'pagaNumber' //页码符号
    String baseUrl;//列表网址（含页码符号）
    int pageStart; //抓取的列表初始页码
    int pageEnd    //抓取的列表尾页码

    @Override
    void process(Page page) {
        //初始URL，添加所有列表url
        if (page.getUrl().toString().trim() == defaultUrl) {
            (baseUrl, pageStart, pageEnd) = setBaseUrlAndSum()
            (listUrl, detailUrl) = setTypeOfUrl()

            if (baseUrl && pageStart && pageEnd) {
                pageStart.upto(pageEnd) {
                    page.addTargetRequest(baseUrl.replace(pageNumSymbol, it + ""))
                }
            }
        } else if (page.getUrl().regex(listUrl).match()) {
            //列表页处理
            listPrecess()
        } else if (page.getUrl().regex(detailUrl).match()) {
            //详情页处理
            detailProcess()
        } else {
            println "无法处理网址类型  url $page.url"
        }
    }

    @Override
    Site getSite() {
        Site.me().setRetryTimes(3).setTimeOut(20000)
    }

    /**
     * 获得要抓取链接的列表
     * @return
     * baseUrl   列表网址（含页码符号）
     * pageStart 抓取的列表初始页码
     * pageEnd   抓取的列表尾页码
     */
    abstract def setBaseUrlAndSum()

    /**
     * 设置 列表URL 和 详情URL 的模板
     */
    abstract def setTypeOfUrl()

    /**
     * 列表页处理
     */
    abstract def listPrecess()

    /**
     * 详情页处理
     */
    abstract def detailProcess()
}