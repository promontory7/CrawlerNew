package com.crawler.studydemo

import groovy.transform.Canonical
import groovy.transform.Immutable

import static java.lang.Math.random

/**
 * 实现循环
 */
for (i in 0..2) {
    println 'hehe'
}

0.upto(2) {
    print "$it" //it 默认参数名
}

3.times { print "$it" }

0.step(10, 2) {
    print "$it"
}//0 2 4 6 8

/**
 * 在JDK 使用 java.lang.Process类与系统交互，过程麻烦
 * GDK很方便
 */
println "java -version".execute().text

/**
 * 安全导航操作符
 * 避免每次都要检查是否为空值
 */
def fool(str) {
//    if (str != null) {str.reverse()}
    str?.reverse()
}

println fool('evil')
println fool(null)

/**
 * JavaBean
 */
class Car {
    def type
    def miles = 0
    final year

    Car(theYear) {
        year = theYear
    }
}
//不需要getter setter 方法
Car car = new Car(2016)
println "Year $car.year    Miles $car.miles"
car.miles = 100
println "Year $car.year    Miles $car.miles"

//初始化
car2 = new Car(type: 2, miles: 100, year: 1996)

/**
 * 可选参数(只要给它赋一个值)
 * groovy 会把末尾的数组形参当做可选的
 */
def log(x, y = 2) {
    Math.log(x) / Math.log(y)
}

println log(4)

/**
 * 多赋值
 * 如果有多余的变量，会设置为null，如果是多余的值，会被抛弃
 */
def (firstName, laetName) = 'A B'.split(' ')
println "$firstName ,$laetName"

def name1 = 'name1', name2 = 'name2'
(name1, name2) = [name2, name1]
println "$name1 , $name2"

/**
 * 实现接口
 */
//不用使用内部类
/*
Button button
button.addActionListener(
        { JOptionPane.showMessageDialog(frame, "you clicked1") } as ActionListener)

//加入每个接口不同方法的实现不同
handleFouces = [
        focusGained: { println "..." },
        foucusLost : { println "....." }]
button.addFocusListener(handleFouces as FocusListener)

// 如果只有运行的时候才知道接口名字
events = ['WindowListener', 'ComponentListener']
handler = { println "..." }
for (event in events) {
    handleImpl = handler.adType(Class.forName("java.awt.enent.${event}"))
    frame."add${event}"(handleImpl)
}
*/

/**
 * 布尔求值
 * java要求条件必须是布尔表达式，但是groovy不用，groovy表达式为真的情况如下
 * Bollean               true
 * Collection            集合不为空
 * Character             值不为0
 * CharSequence          长度大于0
 * Iterator              hasNext 为 true
 * Number                Double值不为0
 * Map                   该映射不为空
 * Matcher               至少有一个匹配
 * Object[]              长度大于0
 * 其它类型               引用不为null
 */
def obj = "hello"
if (obj) {
    println "is true"
}

/**
 * 操作符重载
 */
for (ch in 'a'..'c') {
    println ch
}
lst = ['hello']
lst << 'there'
println lst

/**
 * 对java5 新特性的支持
 */
//for each
String[] greetings = ['hello', 'hi', 'okay']
for (String greet : greetings) {
    println greet
}
//enum
enum CoffeeSize {
    SHORT, SMALL, MEDIUM
}

def orderCoffee(size) {
    switch (size) {
        case [CoffeeSize.SHORT, CoffeeSize.SMALL]:
            println 'short small'
            break
        case CoffeeSize.MEDIUM:
            println 'dedium'
            break
    }
}

orderCoffee(CoffeeSize.SHORT)

//  以变长参数或者数组为末尾的形参的变长参数
def reveiveVarArgs(int a, int ... b) {
    println "you args $a and $b"
}

def receiveArray(int a, int[] b) {
    println "you args $a and $b"
}

reveiveVarArgs(1, 2, 34, 6)

//import static Math.random
//别名 import static Math.random as RD
double val = random()

/**
 * Groovy 代码生成变换
 * @Canonical 编写toStrring方法，可以包含或者去掉字段
 * @Delegate 委托
 * @Immutable 不可变对象 , 对象字段无法修改
 * @Lazy 把对象的构建推迟到真的需要时
 * @Newify 不使用new创建
 * @Singleton
 */

//Canonical
@Canonical(excludes = "age")
class Person {
    String firstName
    String lastName
    int age
}

def me = new Person(firstName: "zhu", lastName: "chudong", age: 22)
println me

//Delegate
class Worker {
    def work() { println 'do work' }

    def analyze() { println 'Worker do analyze' }
}

class Expert {
    def analyze() { println 'Worker do analyze' }
}

class Manager {
    @Delegate
    Expert expert = new Expert()
    @Delegate
    Worker worker = new Worker()//analyse 已经存在则不引入
}

def bernie = new Manager()
bernie.analyze()
bernie.work()

//@Immutable
@Immutable
class CreditCard {
    String cardNumber
    int creditlimit
}

println new CreditCard("44522477854554545", 8986525)

//@Lazy
class Heavy {
    def size = 10

    Heavy() { println "init heavy $size" }
}

class AsNeeded {
    def value
    @Lazy
    Heavy heavy1 = new Heavy()
    @Lazy
    Heavy heavy2 = new Heavy(size: value)

    AsNeeded() { println "create AsNeeded" }
}

def asNeeded = new AsNeeded(value: 20)
println asNeeded.heavy1.size
println asNeeded.heavy1.size
println asNeeded.heavy2.size

//@Newify
@Newify([Person, CreditCard])
def fluentCreate() {
    println Person.new(firstName: "hehe", lastName: "haha", age: 20)
    println Person(firstName: "hehe2", lastName: "haha2", age: 25)
    println CreditCard("452515155645151", 222)
}

fluentCreate()

//@Singleton
@Singleton(lazy = true, strict = false)
class TheUnique {
    TheUnique() { println "init create" }

    def hello() { println "hello" }
}

TheUnique.instance.hello()
TheUnique.instance.hello()

/**
 * 与java 区别
 * Groovy 中的==映射到java的 equals() 如果实现了Comparable接口，则映射到此方法
 * Groovy 中的is映射到java的 ==
 * 创建基本类型数组的语法不同
 */
int[] arr=[1,2,3,4,5]
def arr2=[1,2,3,4,5] as  int[]

