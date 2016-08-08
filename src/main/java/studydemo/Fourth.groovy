package studydemo

/**
 * 闭包的使用
 * 避免了代码的冗长，可以辅助创建轻量级可复用的代码片段
 */
def pickEven(n, block) {
    for (int i = 2; i <= n; i += 2) {
        block(i)
    }
    println()
}

pickEven(6, { print it })
pickEven(6) { eventNum -> print eventNum }

def totalSelectValues(n, closure) {
    total = 0
    for (i in 1..n) {
        if (closure(i)) {
            total += i
        }
    }
    total//没有显示的return 则返回最后一个
}

def isOdd = { it % 2 != 0 }
println totalSelectValues(10, isOdd)

/**
 * 向闭包传递参数
 * 对于单参数，it是该参数的默认名称
 * 如果多余一个参数，就得一一列出
 */
def tellFortune(closure) {
    closure new Date("09/20/2016"), "your day!!"
}

tellFortune() {
    date, fortune -> println "Fortune for $date id $fortune"
}

/**
 * 使用闭包进行资源清理
 * 避免忘记释放引用或者关闭，例如 close(),destroy()
 */
write = new FileWriter('output.txt').withWriter {
    write -> write.write('!!!')
}

class Resource {
    def open() { println "open" }

    def close() { println "close" }

    def read() { println "read" }

    def write() { println "write" }
}//使用完是可能会忘记close
def static use(closure) {
    def r = new Resource()
    try {
        r.open()
        closure(r)
        r.close()
    } finally {
        r.close()
    }
}

/**
 *  闭包与协程
 *  在执行主例程的时候，执行一个子例程，执行部分代码就挂起，回到主例程
 *  之后可以在子例程挂起的位置恢复该函数的执行
 *  主例程和子例程之间完全对称，可以互相调用
 *  在java中，使用wait() notify() 与多线程的集合使用，可以协助完成协程
 */

def iterate(n, closure) {
    1.upto(n) {
        println "in iterate with value n is ${it}"
        closure(it)
    }
}

total = 0
closure = {
    total += it// 每次调用闭包，都会恢复到上一次的total值
    println "in closure value total is ${total}"
}
iterate(4, closure)

/**
 * 科里化闭包
 * 在多次调用同一个闭包的时候，有一个或者多个实参是相同的，传参就会很枯燥
 * 可以预先绑定某个形参，调用闭包的时候就不必重复为这个闭包提供参数了
 */

def tellFortunes(closure) {
    Date date = new Date("09/20/2016")
//    closure date,"first"
//    closure date,"second"
    //postForturn 保存着保存着科里化后的闭包引用，已经绑定了date数据
    postFortune = closure.curry(date)
    postFortune "first"
    postFortune "second"
}

tellFortunes() {
    date, forturn ->
        println "Fortunes for ${date} is ${forturn}"
}

/**
 * 动态闭包
 *  1根据闭包是否参在
 *  2参数数目closure.maximumNumberOfParameters
 *  3参数类型closure.paremeterTypes
 */
def doSomething(closure) {
    if (closure) {
        closure()
    } else {
        println "Using default"
    }
}

doSomething()
doSomething() { println "doSomething with closure" }

/**
 * 闭包委托
 * this,owner,delegate 是闭包的三个属性，
 * 用于确定由哪个对象处理闭包内的方法调用
 */

/**
 * 使用尾递归编写程序
 * 递归程序转换成迭代过程，提高性能
 *
 * */
def factorial
factorial = { int number, int theFcatorial ->
    number == 1 ? theFcatorial : factorial.trampoline(number - 1, number * theFcatorial)
}.trampoline()

println "factorial 5 value is ${factorial(5, 1)}"



