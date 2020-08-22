package page.basis

import common.CommonStatic
import common.battle.BasisSet
import common.battle.LineUp
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
import common.util.Data
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.Form
import common.util.unit.Level
import common.util.unit.UnitLevel
import io.BCPlayer
import main.Opts
import page.JBTN
import page.JL
import page.JTF
import page.MainLocale
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Interpret
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class LevelEditPage(private val p: Page, private val lv: Level, private val f: Form?) : Page(p) {
    private val orbs: MutableList<IntArray> = ArrayList()
    private val bck: JBTN = JBTN(0, "back")
    private val pcoin = JLabel()
    private val levels: JTF = JTF()
    private val orbList: JList<String> = JList<String>()
    private val orbScroll: JScrollPane = JScrollPane(orbList)
    private val add: JBTN = JBTN(0, "add")
    private val rem: JBTN = JBTN(0, "rem")
    private val clear: JBTN = JBTN(0, "clear")
    private val orbb: OrbBox = OrbBox(intArrayOf())
    private val type: JComboBox<String> = JComboBox<String>()
    private val trait: JComboBox<String> = JComboBox<String>()
    private val grade: JComboBox<String> = JComboBox<String>()
    private var traitData: List<Int> = ArrayList()
    private var gradeData: List<Int> = ArrayList()
    private var updating = false
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(bck, x, y, 0, 0, 200, 50)
        Page.Companion.set(pcoin, x, y, 50, 100, 600, 50)
        Page.Companion.set(levels, x, y, 50, 150, 350, 50)
        Page.Companion.set(orbScroll, x, y, 50, 225, 350, 600)
        Page.Companion.set(add, x, y, 50, 875, 175, 50)
        Page.Companion.set(rem, x, y, 225, 875, 175, 50)
        Page.Companion.set(orbb, x, y, 450, 425, 200, 200)
        Page.Companion.set(type, x, y, 700, 425, 200, 50)
        Page.Companion.set(trait, x, y, 700, 500, 200, 50)
        Page.Companion.set(grade, x, y, 700, 575, 200, 50)
        Page.Companion.set(clear, x, y, 50, 975, 350, 50)
    }

    override fun timer(t: Int) {
        orbb.paint(orbb.getGraphics())
        super.timer(t)
    }

    private fun addListeners() {
        bck.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        levels.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                val lvs: IntArray = CommonStatic.parseIntsN(levels.getText())
                setLvOrb(lvs, generateOrb())
            }
        })
        orbList.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (updating) {
                    return
                }
                rem.setEnabled(valid())
                type.setEnabled(valid())
                trait.setEnabled(valid())
                grade.setEnabled(valid())
                if (orbList.getSelectedIndex() != -1) {
                    orbb.changeOrb(orbs[orbList.getSelectedIndex()])
                    initializeDrops(orbs[orbList.getSelectedIndex()])
                } else {
                    orbb.changeOrb(intArrayOf())
                }
            }
        })
        rem.setLnr(Consumer { x: ActionEvent? ->
            val index: Int = orbList.getSelectedIndex()
            if (index != -1 && index < orbs.size) {
                orbs.removeAt(index)
            }
            orbList.setListData(generateNames())
            setLvOrb(lv.lvs, generateOrb())
        })
        add.setLnr(Consumer { x: ActionEvent? ->
            val data = intArrayOf(0, CommonStatic.getBCAssets().DATA.get(0), 0)
            orbs.add(data)
            orbList.setListData(generateNames())
            setLvOrb(lv.lvs, generateOrb())
        })
        type.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (updating) {
                    return
                }
                if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size) {
                    var data = orbs[orbList.getSelectedIndex()]
                    if (f!!.orbs != null && f.orbs.slots != -1) {
                        if (type.getSelectedIndex() == 0) {
                            data = intArrayOf()
                        } else {
                            if (data.size == 0) {
                                data = intArrayOf(0, 0, 0)
                            }
                            data[0] = type.getSelectedIndex() - 1
                        }
                    } else {
                        data[0] = type.getSelectedIndex()
                    }
                    orbs[orbList.getSelectedIndex()] = data
                    initializeDrops(data)
                    orbb.changeOrb(data)
                    setLvOrb(lv.lvs, generateOrb())
                }
            }
        })
        trait.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (updating) {
                    return
                }
                if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size) {
                    val data = orbs[orbList.getSelectedIndex()]
                    data[1] = traitData[trait.getSelectedIndex()]
                    orbs[orbList.getSelectedIndex()] = data
                    initializeDrops(data)
                    orbb.changeOrb(data)
                    setLvOrb(lv.lvs, generateOrb())
                }
            }
        })
        grade.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (updating) {
                    return
                }
                if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size) {
                    val data = orbs[orbList.getSelectedIndex()]
                    data[2] = gradeData[grade.getSelectedIndex()]
                    orbs[orbList.getSelectedIndex()] = data
                    initializeDrops(data)
                    orbb.changeOrb(data)
                    setLvOrb(lv.lvs, generateOrb())
                }
            }
        })
        clear.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            if (f!!.orbs == null) return@setLnr
            if (f.orbs.slots != -1) {
                for (i in orbs.indices) {
                    orbs[i] = intArrayOf()
                }
            } else {
                orbs.clear()
            }
            orbb.changeOrb(intArrayOf())
            setLvOrb(lv.lvs, generateOrb())
            orbList.setListData(generateNames())
        })
    }

    private fun generateNames(): Array<String?> {
        val res = arrayOfNulls<String>(orbs.size)
        for (i in orbs.indices) {
            val o = orbs[i]
            if (o.size != 0) {
                res[i] = "Orb" + (i + 1) + " - {" + getType(o[0]) + ", " + getTrait(o[1]) + ", " + getGrade(o[2]) + "}"
            } else {
                res[i] = "Orb" + (i + 1) + " - None"
            }
        }
        return res
    }

    private fun generateOrb(): Array<IntArray?>? {
        if (orbs.isEmpty()) {
            return null
        }
        val data = arrayOfNulls<IntArray>(orbs.size)
        for (i in data.indices) {
            data[i] = orbs[i]
        }
        return data
    }

    private fun getGrade(grade: Int): String {
        return when (grade) {
            0 -> "D"
            1 -> "C"
            2 -> "B"
            3 -> "A"
            4 -> "S"
            else -> "Unknown Grade $grade"
        }
    }

    private fun getTrait(trait: Int): String {
        var res = ""
        for (i in Interpret.TRAIT.indices) {
            if (trait shr i and 1 > 0) {
                res += Interpret.TRAIT.get(i).toString() + "/ "
            }
        }
        if (res.endsWith("/ ")) res = res.substring(0, res.length - 2)
        return res
    }

    private fun getType(type: Int): String {
        return if (type == 0) {
            MainLocale.Companion.getLoc(3, "ot0")
        } else if (type == 1) {
            MainLocale.Companion.getLoc(3, "ot1")
        } else {
            "Unknown Type $type"
        }
    }

    private fun ini() {
        add(bck)
        add(pcoin)
        add(levels)
        add(orbb)
        if (f!!.orbs != null) {
            add(orbScroll)
            add(type)
            add(trait)
            add(grade)
            if (f.orbs.slots == -1) {
                add(add)
                add(rem)
            }
        }
        add(clear)
        val strs: Array<String> = UtilPC.lvText(f, lu().getLv(f.unit).getLvs())
        levels.setText(strs[0])
        pcoin.text = strs[1]
        addListeners()
        orbList.setListData(generateNames())
        orbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        rem.setEnabled(valid())
        type.setEnabled(valid())
        trait.setEnabled(valid())
        grade.setEnabled(valid())
    }

    private fun initializeDrops(data: IntArray) {
        updating = true
        if (f!!.orbs == null) {
            return
        }
        val types: Array<String>
        types = if (f.orbs.slots == -1) {
            arrayOf(MainLocale.Companion.getLoc(3, "ot0"), MainLocale.Companion.getLoc(3, "ot1"))
        } else {
            arrayOf("None", MainLocale.Companion.getLoc(3, "ot0"), MainLocale.Companion.getLoc(3, "ot1"))
        }
        if (f.orbs.slots != -1 && data.size == 0) {
            type.setModel(DefaultComboBoxModel<String>(types))
            type.setSelectedIndex(0)
            trait.setEnabled(false)
            grade.setEnabled(false)
            if (valid()) {
                val index: Int = orbList.getSelectedIndex()
                orbList.setListData(generateNames())
                orbList.setSelectedIndex(index)
            }
            updating = false
            return
        }
        trait.setEnabled(true)
        grade.setEnabled(true)
        val traits: Array<String?>
        val grades: Array<String?>
        if (data[0] == Data.Companion.ORB_ATK) {
            traitData = ArrayList<Int>(CommonStatic.getBCAssets().ATKORB.keys)
            traits = arrayOfNulls(traitData.size)
            for (i in traits.indices) {
                traits[i] = getTrait(traitData[i])
            }
            if (!traitData.contains(data[1])) {
                data[1] = traitData[0]
            }
            gradeData = CommonStatic.getBCAssets().ATKORB.get(data[1])
            grades = arrayOfNulls(gradeData.size)
            for (i in grades.indices) {
                grades[i] = getGrade(gradeData[i])
            }
            if (!gradeData.contains(data[2])) {
                data[2] = gradeData[2]
            }
        } else {
            traitData = ArrayList<Int>(CommonStatic.getBCAssets().RESORB.keys)
            traits = arrayOfNulls(traitData.size)
            for (i in traits.indices) {
                traits[i] = getTrait(traitData[i])
            }
            if (!traitData.contains(data[1])) {
                data[1] = traitData[0]
            }
            gradeData = CommonStatic.getBCAssets().RESORB.get(data[1])
            grades = arrayOfNulls(gradeData.size)
            for (i in grades.indices) {
                grades[i] = getGrade(gradeData[i])
            }
            if (!gradeData.contains(data[2])) {
                data[2] = gradeData[2]
            }
        }
        type.setModel(DefaultComboBoxModel<String>(types))
        trait.setModel(DefaultComboBoxModel<String>(traits))
        grade.setModel(DefaultComboBoxModel<String>(grades))
        if (f.orbs.slots != -1) {
            type.setSelectedIndex(data[0] + 1)
        } else {
            type.setSelectedIndex(data[0])
        }
        trait.setSelectedIndex(traitData.indexOf(data[1]))
        grade.setSelectedIndex(gradeData.indexOf(data[2]))
        if (valid()) {
            val index: Int = orbList.getSelectedIndex()
            orbs[index] = data
            orbList.setListData(generateNames())
            orbList.setSelectedIndex(index)
        }
        updating = false
    }

    private fun lu(): LineUp {
        return BasisSet.Companion.current().sele.lu
    }

    private fun setLvOrb(lvs: IntArray, orbs: Array<IntArray?>?) {
        lu().setOrb(f!!.unit, lvs, orbs)
        p.callBack(null)
    }

    private fun valid(): Boolean {
        return orbList.getSelectedIndex() != -1 && orbs.size != 0
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        if (f!!.orbs != null) {
            if (lv.orbs == null) {
                if (f.orbs.slots != -1) {
                    for (i in 0 until f.orbs.slots) {
                        orbs.add(intArrayOf())
                    }
                }
            } else {
                for (i in lv.orbs.indices) {
                    orbs.add(lv.orbs[i])
                }
            }
        }
        ini()
        resized()
    }
}
