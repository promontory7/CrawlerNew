package extractProcessor

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 移除String中的特殊字符
 * @param str 原String
 * @param symbols 特殊字符
 * @return
 */
static def removeSymbolFromString(String str, def ... symbols) {
    if (symbols.size() > 0) {
        for (String symbol : symbols) {
            str = str.replace(symbol, "")
        }
    }
    return str
}

/**
 * 从整篇内容中找到匹配行
 * @param raw
 * @param regEx
 * @return
 */
static def getLineFromStringWithRegex(String raw, String regEx) {
    BufferedReader reader = new BufferedReader(new StringReader(raw))
    ArrayList<String> arrayList = new ArrayList<String>()
    String line
    while ((line = reader.readLine()) != null) {
        if (line =~ regEx) {
            arrayList.add(line)
        }
    }
    return arrayList

}
/**
 * 从 String 中 获得匹配的数据
 * @param raw
 * @param regEx
 * @return
 */
static def getValueFromStringWithRegex(String raw, String regEx) {
    def matcher = (raw =~ regEx)
    if (matcher) {
        if (matcher.size() == 1) {
            return matcher[0]
        } else {
            return matcher
        }
    }
}


static def getProjectNoFromString(String raw) {
    return getValueFromStringWithRegex(raw, '[a-zA-Z0-9]{9,}')
}

/**
 * 从script中获得指定script脚本
 * @param raw script
 * @param startLine 开始行（不包括）
 * @param endLine 结束行（包括）
 * @return
 */
static def getScriptString(String raw, String startLine, String endLine) {
    BufferedReader reader = new BufferedReader(new StringReader(raw));
    String line
    StringBuffer content = new StringBuffer()
    boolean writing = false
    while ((line = reader.readLine()) != null) {
        if (writing) {
            content.append(line).append(" ")
            if (line == endLine) {
                break
            }
        }
        if (line == startLine) {
            writing = true
        }
    }
    return content
}

/**
 * 执行javaScript 脚本
 * @param scriptString
 * @return
 */
static def runScript(String scriptString) {

    ScriptEngineManager manager = new ScriptEngineManager()
    ScriptEngine engine = manager.getEngineByName("javascript");
    return engine.eval(scriptString);

}