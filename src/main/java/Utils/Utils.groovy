package Utils

import java.text.SimpleDateFormat

static def getCurrentTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(new Date());
}

static def splitStringAndGet(String raw, String split, int at) {
    String[] list = raw.split(split)
    if (at < list.size()) {
        return list[at]
    }
}