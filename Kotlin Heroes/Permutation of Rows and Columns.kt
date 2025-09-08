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
}

fun main() {
    val fs = FastScanner()
    val t = fs.nextInt()
    val out = StringBuilder()

    repeat(t) {
        val n = fs.nextInt()
        val m = fs.nextInt()
        val total = n * m

        val ra = IntArray(total + 1)
        val ca = IntArray(total + 1)
        val rb = IntArray(total + 1)
        val cb = IntArray(total + 1)

        // Read A: store position of each value
        for (i in 0 until n) {
            for (j in 0 until m) {
                val v = fs.nextInt()
                ra[v] = i
                ca[v] = j
            }
        }

        // Read B: store position of each value
        for (i in 0 until n) {
            for (j in 0 until m) {
                val v = fs.nextInt()
                rb[v] = i
                cb[v] = j
            }
        }

        val rowMap = IntArray(n) { -1 }  // B row -> A row
        val colMap = IntArray(m) { -1 }  // B col -> A col
        var ok = true

        // Enforce consistency from positions of every value
        var v = 1
        while (v <= total && ok) {
            val iA = ra[v]; val jA = ca[v]
            val iB = rb[v]; val jB = cb[v]

            if (rowMap[iB] == -1) rowMap[iB] = iA
            else if (rowMap[iB] != iA) ok = false

            if (colMap[jB] == -1) colMap[jB] = jA
            else if (colMap[jB] != jA) ok = false

            v++
        }

        // Row/column mappings must be permutations (no duplicates, none missing)
        if (ok) {
            val seenRow = BooleanArray(n)
            for (i in 0 until n) {
                val r = rowMap[i]
                if (r !in 0 until n || seenRow[r]) { ok = false; break }
                seenRow[r] = true
            }
        }
        if (ok) {
            val seenCol = BooleanArray(m)
            for (j in 0 until m) {
                val c = colMap[j]
                if (c !in 0 until m || seenCol[c]) { ok = false; break }
                seenCol[c] = true
            }
        }

        out.append(if (ok) "YES\n" else "NO\n")
    }

    print(out.toString())
}
