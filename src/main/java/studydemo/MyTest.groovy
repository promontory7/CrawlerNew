package studydemo

import Utils.CacheUncomplateMap
import bean.Project

0.upto(5) {
    CacheUncomplateMap.instance.saveToMap("$it", new Project())
}
0.upto(5) {
    println CacheUncomplateMap.instance.CacheHashMap.size()
    println CacheUncomplateMap.instance.getFromMap("$it")
}

