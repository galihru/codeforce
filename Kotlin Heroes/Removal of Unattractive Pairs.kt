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
        val n = fs.nextInt()
        val s = fs.next()
        val cnt = IntArray(26)
        for (ch in s) cnt[ch - 'a']++

        var mx = 0
        for (v in cnt) if (v > mx) mx = v

        val ans = maxOf(2 * mx - n, n and 1)  // max(diff, parity)
        out.append(ans).append('\n')
    }
    print(out.toString())
}
