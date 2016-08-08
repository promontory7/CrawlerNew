package extractProcessor

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

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