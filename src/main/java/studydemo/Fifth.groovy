package studydemo

/**
 * 字符串
 * “” '' 都是一个 是不可变的 GString/String
 * 可以使用“。。。”，/.../ 创建字符串 GString
 * ‘’ 是一个字面常量，里面任何表达式都不会被计算
 *  转意符 \
 */
def value = 25
println "hehe"        //String
println '${value}'    //Stirng
println "${value}"    //GString
println(/${value}/)  //GString

/**
 * GString 惰性求值问题
 * 使用无参的包修改值
 *
 */
price = 680           //1
company = 'Google'    //1
//price={->price}     //2
//company={->company} //2
quote = "Today $company stock closed at $price"
println quote
stocks = [Apple: 660, Microsoft: 39]
stocks.each {
    key, value2 ->
        company = key
        price = value2
        println quote
        //使用1输出全为Today Google stock closed at 680 使用2正常
        //可以将company和price 的引用修改为任何想指向的其它对象，但是不能修改GString实例所绑定的内容
}

/**
 * 多行字符串
 * '''......'''
 */
memo = '''wahahaha
          ooo
          hehehe'''
println memo

/**
 * 字符串便捷方法
 * -= 把左侧的字符串中与右侧字符串相匹配的部分去掉
 */
str ="It is a rainy day in Seattle"
println str-="rainy"

/**
 * 正则表达式 RegEx
 * ~用来创建 RexEx模式
 * =~ 部分匹配 返回Matcher 至少一个匹配，就会返回 true
 * ==~ 精确匹配
 */
obj = ~"hello"
println  obj.getClass().name

pattern = ~"(G|g)roovy"
text ='groovy is Hip and Groovy'
if (text=~pattern){
    println "match"
}else {
    println "no match"
}
//println (text=~pattern).replaceAll('hip')

