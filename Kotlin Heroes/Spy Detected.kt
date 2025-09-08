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
        if (c == '-'.code) {
            sign = -1
            c = read()
        }
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
    val out = StringBuilder()

    repeat(t) {
        val n = fs.nextInt()
        val a = IntArray(n) { fs.nextInt() }

        // Determine the majority value from the first three elements
        val common = if (a[0] == a[1] || a[0] == a[2]) a[0] else a[1]

        // Find the 1-based index of the distinct element
        var ans = 1
        for (i in a.indices) {
            if (a[i] != common) {
                ans = i + 1
                break
            }
        }
        out.append(ans).append('\n')
    }

    print(out.toString())
}
