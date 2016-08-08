package pageProcessor

import extractProcessor.CommonExtractUtils
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import pageProcessor.base.BasePageProcessor

import java.util.regex.Pattern

/**
 * 采联采购
 */
class CailianPageProcessor extends BasePageProcessor {

    @Override
    def setBaseUrlAndSum() {
        ['http://www.chinapsp.cn/xinxigonggao/list.php?catid=74166&page=' + pageNumSymbol, 1, 1]
    }

    @Override
    def setTypeOfUrl() {
        ["http://www\\.chinapsp\\.cn/xinxigonggao/list\\.php\\?catid\\=74166\\&page=\\d+",
         "http://www\\.chinapsp\\.cn/xinxigonggao/show\\.php\\?itemid\\=\\d+"]
    }

    @Override
    def listPrecess() {
        LinkedHashMap  id2city=new LinkedHashMap()

        def div = document.getElementById('xxcon_main_left')
        if (div) {
            def ul = div.select('ul')
            if (ul) {
                for (Element li : ul.select('li')) {
                    Elements span = li.children()
                    if (span.size() == 4) {

                        //城市字段友js动态生成，此处先把js源码写入map然后获取所属城市
                        String regEx ="case \\d+:"
                        ArrayList arrayList= CommonExtractUtils.getLineFromStringWithRegex(document.select("script").toString(),regEx)
                        arrayList.each {
                            id2city.put(CommonExtractUtils.getValueFromStringWithRegex("$it","\\d+"),CommonExtractUtils.getValueFromStringWithRegex("$it","[\u4e00-\u9fa5]+"))
                        }
                        String cityID =CommonExtractUtils.getValueFromStringWithRegex(span.get(0).toString(),"\\d+")[1]
                        project.websiteType = id2city.get(cityID)
                        //------------------------------------------------------------

                        project.tenDerWay = CommonExtractUtils.removeSymbolFromString(span.get(1).text(), "【", "】")
                        project.url=span.get(2).attr("href").trim()
                        project.projectName = span.get(2).text()
                        project.publicStart = span.get(3).text()
                        println project
                    }
                }
            }
        }
    }

    @Override
    def detailProcess() {
        println '详情页'
    }
}
