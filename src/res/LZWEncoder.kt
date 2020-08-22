package res

import common.battle.data.DataEntity
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.io.IOException
import java.io.OutputStream

internal class LZWEncoder(private val imgW: Int, private val imgH: Int, private val pixAry: ByteArray?, color_depth: Int) {
    private val initCodeSize: Int

    // GIFCOMPR.C - GIF Image compression routines
    //
    // Lempel-Ziv compression based on 'compress'. GIF modifications by
    // David Rowley (mgardi@watdcsu.waterloo.edu)
    private var remaining = 0
    private var curPixel = 0

    // GIF Image compression - modified 'compress'
    //
    // Based on: compress.c - File compression ala IEEE Computer, June 1984.
    //
    // By Authors: Spencer W. Thomas (decvax!harpo!utah-cs!utah-gr!thomas)
    // Jim McKie (decvax!mcvax!jim)
    // Steve Davies (decvax!vax135!petsd!peora!srd)
    // Ken Turkowski (decvax!decwrl!turtlevax!ken)
    // James A. Woods (decvax!ihnp4!ames!jaw)
    // Joe Orost (decvax!vax135!petsd!joe)
    var n_bits // number of bits/code
            = 0
    var maxbits = BITS // user settable max # bits/code
    var maxcode // maximum code, given n_bits
            = 0
    var maxmaxcode = 1 shl BITS // should NEVER generate this code
    var htab = IntArray(HSIZE)
    var codetab = IntArray(HSIZE)
    var hsize = HSIZE // for dynamic table sizing
    var free_ent = 0 // first unused entry

    // block compression parameters -- after all codes are used up,
    // and compression rate changes, start over.
    var clear_flg = false

    // Algorithm: use open addressing double hashing (no chaining) on the
    // prefix code / next character combination. We do a variant of Knuth's
    // algorithm D (vol. 3, sec. 6.4) along with G. Knott's relatively-prime
    // secondary probe. Here, the modular division first probe is gives way
    // to a faster exclusive-or manipulation. Also do block compression with
    // an adaptive reset, whereby the code table is cleared when the compression
    // ratio decreases, but after the table fills. The variable-length output
    // codes are re-sized at this point, and a special CLEAR code is generated
    // for the decompressor. Late addition: construct the table according to
    // file size for noticeable speed improvement on small files. Please direct
    // questions about this implementation to ames!jaw.
    var g_init_bits = 0
    var ClearCode = 0
    var EOFCode = 0

    // output
    //
    // Output the given code.
    // Inputs:
    // code: A n_bits-bit integer. If == -1, then EOF. This assumes
    // that n_bits =< wordsize - 1.
    // Outputs:
    // Outputs code to the file.
    // Assumptions:
    // Chars are 8 bits long.
    // Algorithm:
    // Maintain a BITS character long buffer (so that 8 codes will
    // fit in it exactly). Use the VAX insv instruction to insert each
    // code in turn. When the buffer fills up empty it and start over.
    var cur_accum = 0
    var cur_bits = 0
    var masks = intArrayOf(0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F, 0x007F, 0x00FF, 0x01FF, 0x03FF, 0x07FF,
            0x0FFF, 0x1FFF, 0x3FFF, 0x7FFF, 0xFFFF)

    // Number of characters so far in this 'packet'
    var a_count = 0

    // Define the storage for the packet accumulator
    var accum = ByteArray(256)

    // Add a character to the end of the current packet, and if it is 254
    // characters, flush the packet to disk.
    @Throws(IOException::class)
    fun char_out(c: Byte, outs: OutputStream?) {
        accum[a_count++] = c
        if (a_count >= 254) {
            flush_char(outs)
        }
    }

    // Clear out the hash table
    // table clear for block compress
    @Throws(IOException::class)
    fun cl_block(outs: OutputStream?) {
        cl_hash(hsize)
        free_ent = ClearCode + 2
        clear_flg = true
        output(ClearCode, outs)
    }

    // reset code table
    fun cl_hash(hsize: Int) {
        for (i in 0 until hsize) {
            htab[i] = -1
        }
    }

    @Throws(IOException::class)
    fun compress(init_bits: Int, outs: OutputStream?) {
        var fcode: Int
        var i /* = 0 */: Int
        var c: Int
        var ent: Int
        var disp: Int
        val hsize_reg: Int
        var hshift: Int

        // Set up the globals: g_init_bits - initial number of bits
        g_init_bits = init_bits

        // Set up the necessary values
        clear_flg = false
        n_bits = g_init_bits
        maxcode = MAXCODE(n_bits)
        ClearCode = 1 shl init_bits - 1
        EOFCode = ClearCode + 1
        free_ent = ClearCode + 2
        a_count = 0 // clear packet
        ent = nextPixel()
        hshift = 0
        fcode = hsize
        while (fcode < 65536) {
            ++hshift
            fcode *= 2
        }
        hshift = 8 - hshift // set hash code range bound
        hsize_reg = hsize
        cl_hash(hsize_reg) // clear hash table
        output(ClearCode, outs)
        outer_loop@ while (nextPixel().also { c = it } != EOF) {
            fcode = (c shl maxbits) + ent
            i = c shl hshift xor ent // xor hashing
            if (htab[i] == fcode) {
                ent = codetab[i]
                continue
            } else if (htab[i] >= 0) // non-empty slot
            {
                disp = hsize_reg - i // secondary hash (after G. Knott)
                if (i == 0) {
                    disp = 1
                }
                do {
                    if (disp.let { i -= it; i } < 0) {
                        i += hsize_reg
                    }
                    if (htab[i] == fcode) {
                        ent = codetab[i]
                        continue@outer_loop
                    }
                } while (htab[i] >= 0)
            }
            output(ent, outs)
            ent = c
            if (free_ent < maxmaxcode) {
                codetab[i] = free_ent++ // code -> hashtable
                htab[i] = fcode
            } else {
                cl_block(outs)
            }
        }
        // Put out the final code.
        output(ent, outs)
        output(EOFCode, outs)
    }

    // ----------------------------------------------------------------------------
    @Throws(IOException::class)
    fun encode(os: OutputStream?) {
        os!!.write(initCodeSize) // write "initial code size" byte
        remaining = imgW * imgH // reset navigation variables
        curPixel = 0
        compress(initCodeSize + 1, os) // compress and write the pixel data
        os.write(0) // write block terminator
    }

    // Flush the packet to disk, and reset the accumulator
    @Throws(IOException::class)
    fun flush_char(outs: OutputStream?) {
        if (a_count > 0) {
            outs!!.write(a_count)
            outs.write(accum, 0, a_count)
            a_count = 0
        }
    }

    fun MAXCODE(n_bits: Int): Int {
        return (1 shl n_bits) - 1
    }

    @Throws(IOException::class)
    fun output(code: Int, outs: OutputStream?) {
        cur_accum = cur_accum and masks[cur_bits]
        cur_accum = if (cur_bits > 0) {
            cur_accum or (code shl cur_bits)
        } else {
            code
        }
        cur_bits += n_bits
        while (cur_bits >= 8) {
            char_out((cur_accum and 0xff).toByte(), outs)
            cur_accum = cur_accum shr 8
            cur_bits -= 8
        }

        // If the next entry is going to be too big for the code size,
        // then increase it, if possible.
        if (free_ent > maxcode || clear_flg) {
            if (clear_flg) {
                maxcode = MAXCODE(g_init_bits.also { n_bits = it })
                clear_flg = false
            } else {
                ++n_bits
                maxcode = if (n_bits == maxbits) {
                    maxmaxcode
                } else {
                    MAXCODE(n_bits)
                }
            }
        }
        if (code == EOFCode) {
            // At EOF, write the rest of the buffer.
            while (cur_bits > 0) {
                char_out((cur_accum and 0xff).toByte(), outs)
                cur_accum = cur_accum shr 8
                cur_bits -= 8
            }
            flush_char(outs)
        }
    }

    // ----------------------------------------------------------------------------
    // Return the next pixel from the image
    // ----------------------------------------------------------------------------
    private fun nextPixel(): Int {
        if (remaining == 0) {
            return EOF
        }
        --remaining
        val pix = pixAry!![curPixel++]
        return pix and 0xff
    }

    companion object {
        private const val EOF = -1

        // General DEFINEs
        const val BITS = 12
        const val HSIZE = 5003 // 80% occupancy
    }

    // ----------------------------------------------------------------------------
    init {
        initCodeSize = Math.max(2, color_depth)
    }
}
