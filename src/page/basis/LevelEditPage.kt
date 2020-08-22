package page.basisimport

import common.util.Data
import common.util.unit.Form
import common.util.unit.Level
import page.Page
import java.awt.event.ActionEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
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