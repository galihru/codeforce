import java.io.BufferedInputStream
import java.io.InputStream

private class FastScanner(private val input: InputStream = BufferedInputStream(System.`in`)) {
    private val buf = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0
    private fun read(): Int {
        if (ptr >= len) {
            len = input.read(buf); ptr = 0
            if (len <= 0) return -1
        }
        return buf[ptr++].toInt()
    }
    fun nextInt(): Int {
        var c = read()
        while (c <= 32) c = read()
        var sgn = 1
        if (c == '-'.code) { sgn = -1; c = read() }
        var x = 0
        while (c > 32) { x = x * 10 + (c - '0'.code); c = read() }
        return x * sgn
    }
    fun next(): String {
        var c = read()
        while (c <= 32) c = read()
        val sb = StringBuilder()
        while (c > 32) { sb.append(c.toChar()); c = read() }
        return sb.toString()
    }
}

private class Fenwick(private val n: Int) {
    private val bit = IntArray(n + 2)
    fun add(idx0: Int, delta: Int) {
        var i = idx0
        while (i <= n) {
            bit[i] += delta
            i += i and -i
        }
    }
    fun sum(idx0: Int): Int {
        var i = idx0
        var res = 0
        while (i > 0) {
            res += bit[i]
            i -= i and -i
        }
        return res
    }
}

fun main() {
    val fs = FastScanner()
    val n = fs.nextInt()
    val m = fs.nextInt()
    val q = fs.nextInt()

    val N = n * m
    val bit = Fenwick(N)
    val has = BooleanArray(N + 1)

    var S = 0
    // Read grid and build initial state (column-major positions)
    val rows = Array(n) { fs.next() }
    for (x in 1..n) {
        val row = rows[x - 1]
        for (y in 1..m) {
            if (row[y - 1] == '*') {
                val pos = (y - 1) * n + x
                has[pos] = true
                bit.add(pos, 1)
                S++
            }
        }
    }

    val out = StringBuilder()
    repeat(q) {
        val x = fs.nextInt()
        val y = fs.nextInt()
        val pos = (y - 1) * n + x

        if (has[pos]) {
            has[pos] = false
            bit.add(pos, -1)
            S--
        } else {
            has[pos] = true
            bit.add(pos, +1)
            S++
        }

        val starsInPrefix = if (S > 0) bit.sum(S) else 0
        val moves = S - starsInPrefix
        out.append(moves).append('\n')
    }

    print(out.toString())
}
