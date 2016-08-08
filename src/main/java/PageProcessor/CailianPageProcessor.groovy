package pageProcessor

import extractProcessor.CommonExtractUtils
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import pageProcessor.base.BasePageProcessor

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * 采联采购
 */
class CailianPageProcessor extends BasePageProcessor {

    @Override
    def setBaseUrlAndSum() {
        ['http://www.chinapsp.cn/xinxigonggao/list.php?catid=74166&page=' + pageNumSymbol, 1, 5]
    }

    @Override
    def setTypeOfUrl() {
        ["http://www\\.chinapsp\\.cn/xinxigonggao/list\\.php\\?catid\\=74166\\&page=\\d+",
         "http://www\\.chinapsp\\.cn/xinxigonggao/show\\.php\\?itemid\\=\\d+"]
    }

    @Override
    def listPrecess() {

        def div = document.getElementById('xxcon_main_left')
        if (div) {
            def ul = div.select('ul')
            if (ul) {
                for (Element li : ul.select('li')) {
                    Elements span = li.children()
                    if (span.size() == 4) {

//                        String scriptContent= 'id=231;  ' + CommonExtractUtils.getScriptString(document.body().select("script").toString(), 'function areaid(id){', "}")
//                        println CommonExtractUtils.runScript(scriptContent)

                        project.websiteType = span.get(0).text()
                        project.tenDerWay = CommonExtractUtils.removeSymbolFromString(span.get(1).text(), "【", "】")
                        project.projectName = span.get(2).text()
                        project.publicStart = span.get(3).text()
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
