package PagePrecessor

import PagePrecessor.base.BasePageProcwssor

/**
 * 采联采购
 */
class CailianPageProcessor extends BasePageProcwssor {

    @Override
    def setBaseUrlAndSum() {
        ['http://www.chinapsp.cn/xinxigonggao/list.php?catid=74166&page=' + pageNumSymbol, 1, 20]
    }

    @Override
    def setTypeOfUrl() {
        ["http://www\\.chinapsp\\.cn/xinxigonggao/list\\.php\\?catid\\=74166\\&page=\\d+",
         "http://www\\.chinapsp\\.cn/xinxigonggao/show\\.php\\?itemid\\=\\d+"]
    }

    @Override
    def listPrecess() {
        println '列表页'
    }

    @Override
    def detailProcess() {
        println '详情页'
    }
}
