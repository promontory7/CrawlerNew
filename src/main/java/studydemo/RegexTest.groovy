package studydemo

import extractProcessor.CommonExtractUtils


String content ="\n" +
        "\$(document).ready(function(e) {\n" +
        "   \$(\"#tab_hover\").hover(function(){\n" +
        "\$(this).find(\"#heidd\").show();\n" +
        "},function(){\n" +
        "\$(this).find(\"#heidd\").hide();\n" +
        "});\n" +
        "\n" +
        "   \$(\"#list_xxmore\").hover(function(){\n" +
        "\$(this).find(\"#list_heidd\").show();\n" +
        "},function(){\n" +
        "\$(this).find(\"#list_heidd\").hide();\n" +
        "});\n" +
        "});\n" +
        "function areaid(id){\n" +
        "var diqu;\n" +
        "switch(id){\n" +
        "case 231:{ var diqu=\"广州\"; return diqu;break;}            \n" +
        "        case 232:{ var diqu=\"韶关\"; return diqu;break;} \n" +
        "case 233:{ var diqu=\"深圳\"; return diqu;break;} \n" +
        "case 234:{ var diqu=\"珠海\"; return diqu;break;}\n" +
        "case 235:{ var diqu=\"汕头\"; return diqu;break;}\n" +
        "case 236:{ var diqu=\"佛山\"; return diqu;break;}\n" +
        "case 237:{ var diqu=\"江门\"; return diqu;break;}\n" +
        "case 238:{ var diqu=\"湛江\"; return diqu;break;}\n" +
        "case 239:{ var diqu=\"茂名\"; return diqu;break;}\n" +
        "case 240:{ var diqu=\"肇庆\"; return diqu;break;}\n" +
        "case 241:{ var diqu=\"惠州\"; return diqu;break;}\n" +
        "case 242:{ var diqu=\"梅州\"; return diqu;break;}\n" +
        "case 243:{ var diqu=\"汕尾\"; return diqu;break;} \n" +
        "case 244:{ var diqu=\"河源\"; return diqu;break;}\n" +
        "case 245:{ var diqu=\"阳江\"; return diqu;break;}\n" +
        "case 246:{ var diqu=\"清远\"; return diqu;break;}\n" +
        "case 247:{ var diqu=\"东莞\"; return diqu;break;}\n" +
        "case 248:{ var diqu=\"中山\"; return diqu;break;}\n" +
        "case 249:{ var diqu=\"潮州\"; return diqu;break;}\n" +
        "case 250:{ var diqu=\"揭阳\"; return diqu;break;}\n" +
        "case 251:{ var diqu=\"云浮\"; return diqu;break;} \n" +
        "default:{ var diqu=\"广东\"; return diqu;break;} \n" +
        "}\n" +
        "}"
println CommonExtractUtils.getLineFromStringWithRegex(content,"^case \\d+:")