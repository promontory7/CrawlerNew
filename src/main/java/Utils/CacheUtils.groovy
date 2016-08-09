package Utils

import bean.Project
import org.omg.PortableServer.LIFESPAN_POLICY_ID


/**
 * 缓存的未完成project数据
 * 一般是项目信息 需要在列表页和详情页两个界面获取时使用
 */
@Singleton(lazy = true, strict = false)
class CacheUncomplateMap {
    HashMap<String, Project> CacheHashMap;

    CacheUncomplateMap() {
        CacheHashMap = new HashMap()
    }

    def saveToMap(String key, Project value) {
        if (CacheHashMap) {
            CacheHashMap.put(key, value)
        } else {
            CacheHashMap = new HashMap()
            CacheHashMap.put(key, value)
        }
    }

    def getFromMap(String key) {
        if (CacheHashMap) {
            return CacheHashMap.remove(key)
        }
        return null
    }
}

/**
 * 用于判断数据库中已经爬取的URL
 */
@Singleton(lazy = true, strict = false)
class CompleteUrlFromDB {
    ArrayList<String> completeUrl

    CompleteUrlFromDB() {
        completeUrl = new ArrayList<>()
    }

    def saveToList(List list) {
        if (completeUrl) {
            completeUrl.addAll(list)
        } else {
            completeUrl = new ArrayList<>()
            completeUrl.addAll(list)
        }
    }

    def boolean isInCompleteUrl(String url) {
        if (completeUrl) {
            return completeUrl.contains(url.trim())
        } else {
            return false
        }
    }
}