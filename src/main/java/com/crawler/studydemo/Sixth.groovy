package com.crawler.studydemo
/**
 * 集合类
 */

/**
 * ArrayList
 */
lst = [1, 2, 3, 4, 5, 6]
println lst.getClass().name
println lst[0]             //获取第一个值
println lst[-1]            //获取倒数第一个值
println lst[2..5]          //从2开始列出四个值

lst.each { println it }      //迭代 reverseEach反向迭代

total = 0
lst.each { total += it }
println total

println lst.collect { it * 2 }          //collect在每个元素上执行操作

println lst.find { it == 4 }            //find 返回第一个等于2的元素
println lst.find { it > 4 }             //find 返回第一个大于5的元素
println lst.findAll { it > 4 }          //findAll 返回所有大于4的元素

println lst.findIndexOf { it == 3 }    //findIndexOf 返回位置

lst = ['hehehe', 'hahaha', 'xixixi']
println lst.collect { it.size() }.sum()                         //据算所有元素的长度总和
println lst.inject(0) { sum, element -> sum + element.size() }  //inject会是集合中每个元素调用闭包 element代表集合中每个元素

println lst.join(' ')                    //把集合中的元素连成一个句子
println lst - ['hehehe', 'hahaha']        //从左侧的集合中移除右侧的元素

/**
 * Map LinkedHashMap
 */
langs = ['C++': 'Great', 'Java': 'Good', 'Groovy': 'Crazy']
println langs.getClass().name
println langs['Java']
println langs.Java

langs.each { entry ->
    println "key: $entry.key  value: $entry.value"
}
langs.each { language, tip ->
    println "$language:$tip     "
}

//collect
println langs.collect { language, tip ->
    language.replaceAll("[+]", "P")
}

//find返回第一个 findAll返回所有
entry=langs.find {languague,author ->
    languague.size()>3
}
println "$entry.key :$entry.value"

selected =langs.findAll {language,anthor ->
    language.size()>3
}
selected.each {key,value ->
    println "$key :$value"
}
//如果只是想知道是否包含满足条件的元素，使用any替换find
//而every 则会判断是否所有元素都满足给定的条件
println langs.every {language,author ->
    language=~"[^A-Za-z]"
}

//grouopBy()对MAP中的元素进行分组
friends=[briang:'Brian Goert',briang:'Brian HESG',
         davidg:'David Npck',dacidg:'David HEFOG']
groupByFirsrName =friends.groupBy {it.value.split(' ')[0]}
groupByFirsrName.each {firstName,buddies ->
    println "$firstName : ${buddies.collect {key,fullname->fullname}.join(',')}"
}












