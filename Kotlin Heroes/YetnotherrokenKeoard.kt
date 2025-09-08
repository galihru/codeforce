import java.io.BufferedInputStream
import java.io.InputStream

private class FastScanner(private val input: InputStream = BufferedInputStream(System.`in`)) {
    private val buf = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0

    private fun read(): Int {
        if (ptr >= len) {
            len = input.read(buf)
            ptr = 0
            if (len <= 0) return -1
        }
        return buf[ptr++].toInt()
    }

    fun nextInt(): Int {
        var c = read()
        while (c <= 32) c = read()
        var sign = 1
        if (c == '-'.code) { sign = -1; c = read() }
        var x = 0
        while (c > 32) { x = x * 10 + (c - '0'.code); c = read() }
        return x * sign
    }

    fun next(): String {
        var c = read()
        while (c <= 32) c = read()
        val sb = StringBuilder()
        while (c > 32) { sb.append(c.toChar()); c = read() }
        return sb.toString()
    }
}

fun main() {
    val fs = FastScanner()
    val t = fs.nextInt()
    val out = StringBuilder()

    repeat(t) {
        val s = fs.next()
        val n = s.length

        val chars = CharArray(n)         // typed characters in order (excluding 'b'/'B')
        val alive = BooleanArray(n)      // whether that character is still present
        val lower = IntArray(n); var topL = -1
        val upper = IntArray(n); var topU = -1
        var m = 0

        for (ch in s) {
            when (ch) {
                'b' -> {
                    while (topL >= 0 && !alive[lower[topL]]) topL--
                    if (topL >= 0) { alive[lower[topL]] = false; topL-- }
                }
                'B' -> {
                    while (topU >= 0 && !alive[upper[topU]]) topU--
                    if (topU >= 0) { alive[upper[topU]] = false; topU-- }
                }
                else -> {
                    chars[m] = ch
                    alive[m] = true
                    if (ch in 'a'..'z') lower[++topL] = m else upper[++topU] = m
                    m++
                }
            }
        }

        for (i in 0 until m) if (alive[i]) out.append(chars[i])
        out.append('\n')
    }

    print(out.toString())
}
