import java.io.BufferedInputStream
import java.io.InputStream

private class FastScanner(private val input: InputStream = BufferedInputStream(System.`in`)) {
    private val buffer = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0

    private fun read(): Int {
        if (ptr >= len) {
            len = input.read(buffer)
            ptr = 0
            if (len <= 0) return -1
        }
        return buffer[ptr++].toInt()
    }

    fun nextInt(): Int {
        var c = read()
        while (c <= 32) c = read()
        var sign = 1
        if (c == '-'.code) { sign = -1; c = read() }
        var res = 0
        while (c > 32) {
            res = res * 10 + (c - '0'.code)
            c = read()
        }
        return res * sign
    }
}

fun main() {
    val fs = FastScanner()
    val t = fs.nextInt()

    // Precompute first 1000 liked numbers
    val liked = IntArray(1000)
    var x = 1
    var idx = 0
    while (idx < 1000) {
        if (x % 3 != 0 && x % 10 != 3) {
            liked[idx] = x
            idx++
        }
        x++
    }

    val out = StringBuilder()
    repeat(t) {
        val k = fs.nextInt()
        out.append(liked[k - 1]).append('\n')
    }
    print(out.toString())
}
