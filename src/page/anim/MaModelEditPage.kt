package page.animimport

import page.Page
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.MouseEvent
import java.util.*
import java.util.function.Consumer

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class MaModelEditPage : Page, AbEditPage {
    private val back: JBTN = JBTN(0, "back")
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlp: JList<String> = JList<String>()
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jtr: JTree = JTree()
    private val jsptr: JScrollPane = JScrollPane(jtr)
    private val mmet: MaModelEditTable = MaModelEditTable(this)
    private val jspmm: JScrollPane = JScrollPane(mmet)
    private val sb: SpriteBox = SpriteBox(this)
    private val mb: ModelBox = ModelBox()
    private val revt: JBTN = JBTN(0, "revt")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val rema: JBTN = JBTN(0, "rema")
    private val sort: JBTN = JBTN(0, "sort")
    private val aep: EditHead
    private var p: Point? = null
    private var mmt: MMTree? = null

    constructor(p: Page?) : super(p) {
        aep = EditHead(this, 2)
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p) {
        aep = bar
        ini()
        resized()
    }

    override fun callBack(obj: Any?) {
        change(obj, { o: Any? ->
            if (o != null && o is IntArray) {
                val rs = o
                mmet.setRowSelectionInterval(rs[0], rs[1])
                setB(rs[0])
            }
            if (mb.getEnt() != null) mb.getEnt().organize()
            setTree(mmet.anim)
        })
    }

    override fun setSelection(anim: AnimCE?) {
        change<AnimCE>(anim, Consumer<AnimCE> { ac: AnimCE? ->
            jlu.setSelectedValue(ac, true)
            setA(ac)
        })
    }

    override fun mouseDragged(e: MouseEvent) {
        if (p == null) return
        mb.ori.x += p!!.x - e.x.toDouble()
        mb.ori.y += p!!.y - e.y.toDouble()
        p = e.point
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source !is ModelBox) return
        p = e.point
    }

    override fun mouseReleased(e: MouseEvent) {
        p = null
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source !is ModelBox) return
        val mwe: MouseWheelEvent = e as MouseWheelEvent
        val d: Double = mwe.getPreciseWheelRotation()
        mb.siz *= Math.pow(res, d)
    }

    override fun renew() {
        change(this, { page: MaModelEditPage? ->
            val da: AnimCE = jlu.getSelectedValue()
            val vec: Vector<AnimCE?> = Vector<AnimCE?>()
            if (aep.focus == null) vec.addAll(AnimCE.Companion.map().values) else vec.add(aep.focus)
            jlu.setListData(vec)
            if (da != null && vec.contains(da)) {
                val row: Int = mmet.getSelectedRow()
                setA(da)
                jlu.setSelectedValue(da, true)
                if (row >= 0 && row < mmet.mm.parts.size) {
                    setB(row)
                    mmet.setRowSelectionInterval(row, row)
                }
            } else setA(null)
            callBack(null)
        })
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(aep, x, y, 550, 0, 1750, 50)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jsptr, x, y, 0, 550, 300, 750)
        Page.Companion.set(jspmm, x, y, 300, 550, 2000, 750)
        Page.Companion.set(jspu, x, y, 0, 50, 300, 500)
        Page.Companion.set(mb, x, y, 300, 50, 700, 500)
        Page.Companion.set(jspp, x, y, 1000, 50, 300, 500)
        Page.Companion.set(sb, x, y, 1300, 50, 950, 400)
        Page.Companion.set(sort, x, y, 1300, 500, 200, 50)
        Page.Companion.set(revt, x, y, 1500, 500, 200, 50)
        Page.Companion.set(addl, x, y, 1700, 500, 200, 50)
        Page.Companion.set(reml, x, y, 1900, 500, 200, 50)
        Page.Companion.set(rema, x, y, 2100, 500, 200, 50)
        aep.componentResized(x, y)
        mmet.setRowHeight(Page.Companion.size(x, y, 50))
        sb.paint(sb.getGraphics())
        mb.paint(mb.getGraphics())
    }

    private fun addLine() {
        change(0, { o: Int? ->
            var ind: Int = mmet.getSelectedRow() + 1
            if (ind == 0) ind++
            val mm: MaModel = mmet.mm
            val inds = IntArray(mm.n)
            for (i in 0 until mm.n) inds[i] = if (i < ind) i else i + 1
            mmet.anim.reorderModel(inds)
            mm.n++
            val move = IntArray(mm.n)
            for (i in 0 until mm.n) move[i] = if (i < ind) i else i - 1
            mm.reorder(move)
            val newl = IntArray(14)
            newl[11] = 1000
            newl[9] = newl[11]
            newl[8] = newl[9]
            mm.parts.get(ind) = newl
            mmet.anim.unSave("mamodel add line")
            callBack(null)
            resized()
            mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
            val h: Int = mmet.getRowHeight()
            mmet.scrollRectToVisible(Rectangle(0, h * ind, 1, h))
        })
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlu.getValueIsAdjusting()) return
                change<AnimCE>(jlu.getSelectedValue(), Consumer<AnimCE> { `val`: AnimCE? -> setA(`val`) })
            }
        })
        jlp.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                sb.sele = jlp.getSelectedIndex()
            }
        })
        val lsm: ListSelectionModel = mmet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                change(lsm.getLeadSelectionIndex(), Consumer { ind: Int -> setB(ind) })
            }
        })
        addl.setLnr(Consumer { x: ActionEvent? -> addLine() })
        reml.setLnr(Consumer { x: ActionEvent? -> removeLine() })
        rema.setLnr(Consumer { x: ActionEvent? -> removeTree() })
    }

    private fun `addListeners$1`() {
        revt.setLnr(Consumer { x: ActionEvent? ->
            mmet.anim.revert()
            callBack(null)
        })
        jtr.addTreeSelectionListener(object : TreeSelectionListener {
            override fun valueChanged(arg0: TreeSelectionEvent?) {
                if (isAdj) return
                val o: Any = jtr.getLastSelectedPathComponent() ?: return
                val str = o.toString()
                val ind: Int = CommonStatic.parseIntN(str.split(" - ").toTypedArray()[0])
                if (ind == -1) return
                setB(ind)
            }
        })
        sort.addActionListener(object : ActionListener {
            private var p = 0
            private var move: IntArray
            private var inds: IntArray
            private var parts: Array<IntArray>
            override fun actionPerformed(arg0: ActionEvent?) {
                p = 0
                parts = mmet.mm.parts
                val n = parts.size
                move = IntArray(n)
                inds = IntArray(n)
                for (i in parts.indices) if (parts[i][0] == -1) add(i)
                mmet.anim.reorderModel(inds)
                mmet.mm.reorder(move)
                mmet.anim.unSave("sort")
            }

            private fun add(n: Int) {
                inds[n] = p
                move[p++] = n
                for (i in parts.indices) if (parts[i][0] == n) add(i)
            }
        })
    }

    private fun ini() {
        add(aep)
        add(back)
        add(jspu)
        add(jspp)
        add(jspmm)
        add(revt)
        add(addl)
        add(reml)
        add(rema)
        add(jsptr)
        add(sort)
        add(sb)
        add(mb)
        jlu.setCellRenderer(AnimLCR())
        jtr.setExpandsSelectedPaths(true)
        reml.setForeground(Color.RED)
        rema.setForeground(Color.RED)
        setA(null)
        `addListeners$0`()
        `addListeners$1`()
    }

    private fun removeLine() {
        var rows: IntArray = mmet.getSelectedRows()
        if (rows.size == 0) return
        if (rows[0] == 0) rows = Arrays.copyOfRange(rows, 1, rows.size)
        change(rows, { row: IntArray ->
            val mm: MaModel = mmet.mm
            val data: Array<IntArray?> = mm.parts
            mm.n -= row.size
            val inds = IntArray(data.size)
            val move = IntArray(mm.n)
            for (i in row.indices) data[row[i]] = null
            var ind = 0
            for (i in data.indices) if (data[i] != null) {
                move[ind] = i
                inds[i] = ind
                ind++
            } else inds[i] = -1
            mmet.anim.reorderModel(inds)
            mm.reorder(move)
            mmet.anim.unSave("mamodel remove line")
            callBack(null)
            if (ind >= mm.n) ind--
            mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
        })
    }

    private fun removeTree() {
        change(0, { o: Int? ->
            val mm: MaModel = mmet.mm
            val rows: IntArray = mmet.getSelectedRows()
            if (rows[0] == 0) return@change
            val bs = BooleanArray(mm.n)
            var total = rows.size
            for (ind in rows) bs[ind] = true
            total += mm.getChild(bs)
            mm.clearAnim(bs, mmet.anim.anims)
            val inds = IntArray(mm.n)
            val move = IntArray(mm.n - total)
            var j = 0
            for (i in 0 until mm.n) if (!bs[i]) {
                move[j] = i
                inds[i] = j
                j++
            }
            mmet.anim.reorderModel(inds)
            mm.n -= total
            mm.reorder(move)
            mmet.anim.unSave("mamodel remove tree")
            callBack(null)
            var ind = rows[0]
            if (ind >= mm.n) ind = mm.n - 1
            if (ind >= 0) mmet.setRowSelectionInterval(ind, ind)
            setB(ind)
        })
    }

    private fun setA(anim: AnimCE?) {
        aep.setAnim(anim)
        setTree(anim)
        addl.setEnabled(anim != null)
        sort.setEnabled(anim != null)
        revt.setEnabled(anim != null)
        if (anim == null) {
            mmet.setMaModel(null)
            mb.setEntity(null)
            sb.setAnim(null)
            jlp.setListData(arrayOfNulls<String>(0))
            setB(-1)
            return
        }
        mmet.setMaModel(anim)
        mb.setEntity(EAnimS(anim, anim.mamodel))
        val ic: ImgCut = anim.imgcut
        val name = arrayOfNulls<String>(ic.n)
        for (i in 0 until ic.n) name[i] = i.toString() + " " + ic.strs.get(i)
        jlp.setListData(name)
        sb.setAnim(anim)
        mmet.clearSelection()
        setB(-1)
    }

    private fun setB(ind: Int) {
        if (mb.getEnt() != null) mb.getEnt().sele = ind
        reml.setEnabled(ind != -1)
        rema.setEnabled(ind != -1)
        if (ind < 0 || mmet.mm == null) return
        if (mmet.getSelectedRow() != ind) change(ind, { i: Int? ->
            mmet.setRowSelectionInterval(i, i)
            mmet.scrollRectToVisible(mmet.getCellRect(i, 0, true))
        })
        if (mmt != null) change(ind, { i: Int -> mmt.select(i) })
        val `val`: Int = mmet.mm.parts.get(ind).get(2)
        jlp.setSelectedIndex(`val`)
        for (row in mmet.getSelectedRows()) {
            for (ints in mmet.mm.parts) if (ints[0] == row) reml.setEnabled(false)
            for (ma in mmet.anim.anims) for (p in ma.parts) if (p.ints[0] == row) reml.setEnabled(false)
        }
    }

    private fun setTree(dat: AnimCE?) {
        change<AnimCE>(dat, Consumer<AnimCE> { anim: AnimCE? ->
            if (anim == null) {
                jtr.setModel(DefaultTreeModel(null))
                mmt = null
                return@change
            }
            if (mmt == null || mmt.anim !== anim) mmt = MMTree(null, anim, jtr)
            mmt.renew()
        })
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val res = 0.95
    }
}