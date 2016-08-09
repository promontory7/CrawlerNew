package com.crawler.studydemo
/**
 * GDK
 * Collect 添加了方法:each collect find findAll any every
 * object 提供dump ,inspect(创建一个类需要提供什么)
 * 上下文 with
 * sleep 给定的毫秒数时间内睡眠时，改方法会忽略中断
 * 间接访问属性
 * 间接调用方法
 */

//dump 打印改对象的所有内部信息
str='hello'
println str.dump()

//with 在这个作用域调用任何方法，都会被定向到改上下文对象
lst=[1,2]
lst.add(3)
println lst.size()

lst.with {
    add(4)
    println size()
    println contains(3)
}

//间接访问属性
class Car2{
    int miles,fuelLevel
}
car=new Car2(miles: 27,fuelLevel: 100)
properties=['miles','fuelLevel']
properties.each {
    name ->
        println "$name =${car[name]}"
}
car[properties[1]]=90
println car[properties[1]]

//间接调用方法
class Person2{
    def walk(){println "Walking..."}
    def walk(int miles){println "Walking $miles"}
    def walk(int miles,String where){println "Walking $miles to $where"}
}
peter=new Person2()
peter.invokeMethod("walk",null)
peter.invokeMethod("walk",10)
peter.invokeMethod("walk",[20,'shenzhen']as Object[])

/**
 * 数组的扩展
 */
int[] arr =[1,2,3,4,5,6]
println arr[2..5]

/**
 * java.io的扩展
 */
//读出全文
println new File('a.txt').text
//一行行读
new File('a.txt').eachLine {line ->
    println line
}
//写入
new File("output.txt").withWriter {
    file ->
        file <<"some data..."
}


