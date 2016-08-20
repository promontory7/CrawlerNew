package com.crawler.pageProcessor

import com.crawler.MyUtils.CacheUncomplateMap
import com.crawler.MyUtils.CompleteUrlFromDB
import com.crawler.MyUtils.HibernateUtil
import com.crawler.MyUtils.Utils
import com.crawler.bean.Project
import com.crawler.extractProcessor.CommonExtractUtils
import com.crawler.pageProcessor.base.BasePageProcessor
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import us.codecraft.webmagic.Page

/**
 * 采联采购
 */
class CailianPageProcessor extends BasePageProcessor {

    @Override
    def setBaseUrlAndSum() {
        [['http://www.chinapsp.cn/xinxigonggao/list.php?catid=74166&page=' + pageNumSymbol, 1, 20]]
    }

    @Override
    def setTypeOfUrl() {
        ["http://www\\.chinapsp\\.cn/xinxigonggao/list\\.php\\?catid\\=74166\\&page=\\d+",
         "http://www\\.chinapsp\\.cn/xinxigonggao/show\\.php\\?itemid\\=\\d+"]
    }


    @Override
    def listPrecess(Page page) {


        //城市字段由js动态生成，此处先把js源码解析出id和城市后写入map，然后获取所属城市
        LinkedHashMap id2city = new LinkedHashMap()
        String regEx = "case \\d+:"
        ArrayList arrayList = CommonExtractUtils.getLineFromStringWithRegex(document.select("script").toString(), regEx)
        arrayList.each {
            id2city.put(CommonExtractUtils.getValueFromStringWithRegex("$it", "\\d+"), CommonExtractUtils.getValueFromStringWithRegex("$it", "[\u4e00-\u9fa5]+"))
        }

        def div = document.getElementById('xxcon_main_left')
        if (div) {
            def ul = div.select('ul')
            if (ul) {
                for (Element li : ul.select('li')) {
                    Elements span = li.children()
                    if (span.size() == 4) {
                        Project project=new Project()
                        String cityID = CommonExtractUtils.getValueFromStringWithRegex(span.get(0).toString(), "\\d{3}")
                        project.websiteType = id2city.get(cityID)+'市'

                        project.tenDerWay = CommonExtractUtils.removeSymbolFromString(span.get(1).text(), "【", "】")
                        project.url = span.get(2).attr("href").trim()
                        project.projectName = span.get(2).text()
                        project.publicStart = span.get(3).text()

                        if (!CompleteUrlFromDB.instance.isInCompleteUrl(project.url)) {
                            page.addTargetRequest(project.url)
                            CacheUncomplateMap.instance.saveToMap(project.url, project)
                        }
                    }
                }
            }
        }
    }

    @Override
    def detailProcess(Page page) {
         project = CacheUncomplateMap.instance.getFromMap(page.getUrl().toString().trim())

        def div = document.getElementById('content')
        if (div) {
            project.rawHtml = div.toString()

            StringBuffer content = new StringBuffer()
            if (div.children()){
                for (Element p : div.children()) {
                    if (p.nodeName()=='div'){
                        if (p.childNodeSize()>8){
                            for (Element p2:p.children()){
                                content.append(p2.text()).append('\n')
                            }
                        }else {
                            content.append(p.text()).append('\n')
                        }

                    }else {
                        content.append(p.text()).append('\n')
                    }
                }
            }

            project.attach=content.toString()
            parseContent(content.toString())
        } else {
            println '内容失踪了...'
        }

       HibernateUtil.save2Hibernate(project)
    }

    @Override
    def parseContent(String content) {

        BufferedReader bufferedReader = new BufferedReader(new StringReader(content))
        String line
        int phoneStyle = 0//联系电话和联系人匹配方式
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains('联系事项')) {
                phoneStyle = 1
            }
            if (line.contains('招标编号') || line.contains('项目编号')) {
                project.projectNo = CommonExtractUtils.getProjectNoFromString(line)

            }
            if (line.contains('联合体投标')) {
                if (line.contains('不')) {
                    project.isAcceptUnion = '否'
                }

            }
            if ((line.contains('采购单位：') || line.contains('招标人名称：')||line.contains('采购人名称'))) {
                    project.owners = Utils.splitStringAndGet(line, '：', 1)
            }
            if (line.contains('招标代理机构：') || line.contains('采购代理机构：')) {
                    project.agency = Utils.splitStringAndGet(line, '：', 1)

            }

            if (line.contains('预算金额') || line.contains('预算限额')) {
                    project.projectPrice = Utils.splitStringAndGet(line, '：', 1)
            }

            if (line.contains('联系电话：') || line.contains('联系方式：')) {
                if (phoneStyle == 1) {
                    if (project.getAgecyPhone()) {
                            project.ownerpeoplePhone = Utils.splitStringAndGet(line, '：', 1)
                    } else {
                            project.agecyPhone = Utils.splitStringAndGet(line, '：', 1)
                    }
                } else {
                    if (project.getOwnerpeoplePhone()) {
                            project.agecyPhone = Utils.splitStringAndGet(line, '：', 1)
                    } else {
                            project.ownerpeoplePhone = Utils.splitStringAndGet(line, '：', 1)
                    }
                }

            }
            if (line.contains('联系人：')) {
                if (phoneStyle == 1) {
                    if (project.getAgencyName()) {
                            project.ownerpeopleName = Utils.splitStringAndGet(line, '：', 1)
                    } else {
                            project.agencyName = Utils.splitStringAndGet(line, '：', 1)
                    }
                } else {
                    if (project.getOwnerpeopleName()) {
                            project.agencyName = Utils.splitStringAndGet(line, '：', 1)
                    } else {
                            project.ownerpeopleName = Utils.splitStringAndGet(line, '：', 1)
                    }
                }

            }
            if (line.contains('地址')) {
                project.projectAddress = Utils.splitStringAndGet(line, '：', 1)
            }
        }
        project.time = Utils.currentTime
        project.state = 1
    }
}
