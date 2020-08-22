package page.animimport

import common.util.anim.Part
import page.Page
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.MouseEvent
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.JLabel
import javax.swing.event.ChangeListener

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class MaAnimEditPage : Page, AbEditPage {
    private val back: JBTN = JBTN(0, "back")
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlt: JList<String> = JList<String>()
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val jlp: JList<String> = JList<String>()
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jlm: JList<String> = JList<String>()
    private val jspm: JScrollPane = JScrollPane(jlm)
    private val jlv: JList<String> = JList<String>(mod)
    private val jspv: JScrollPane = JScrollPane(jlv)
    private val maet: MaAnimEditTable = MaAnimEditTable(this)
    private val jspma: JScrollPane = JScrollPane(maet)
    private val mpet: PartEditTable = PartEditTable(this)
    private val jspmp: JScrollPane = JScrollPane(mpet)
    private val jtb: JTG = JTG(0, "pause")
    private val nex: JBTN = JBTN(0, "nextf")
    private val jtl: JSlider = JSlider()
    private val sb: SpriteBox = SpriteBox(this)
    private val ab: AnimBox = AnimBox()
    private val addp: JBTN = JBTN(0, "add")
    private val remp: JBTN = JBTN(0, "rem")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val advs: JBTN = JBTN(0, "advs")
    private val sort: JBTN = JBTN(0, "sort")
    private val inft = JLabel()
    private val inff = JLabel()
    private val infv = JLabel()
    private val infm = JLabel()
    private val lmul = JLabel("</>")
    private val tmul: JTF = JTF()
    private val aep: EditHead
    private var p: Point? = null
    private var pause = false

    constructor(p: Page?) : super(p) {
        aep = EditHead(this, 3)
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p) {
        aep = bar
        ini()
        resized()
    }

    override fun callBack(o: Any?) {
        if (o != null && o is IntArray) change(o as IntArray?, { rs: IntArray ->
            if (rs[0] == 0) {
                maet.setRowSelectionInterval(rs[1], rs[2])
                setC(rs[1])
            } else {
                mpet.setRowSelectionInterval(rs[1], rs[2])
                setD(rs[1])
            }
        })
        val ind: Int = jlt.getSelectedIndex()
        val ac: AnimCE = maet.anim
        if (ind < 0 || ac == null) return
        val time = if (ab.ent == null) 0 else ab.ent.ind()
        ab.setEntity(ac.getEAnim(ac.types.get(ind)))
        ab.ent.setTime(time)
    }

    override fun setSelection(a: AnimCE?) {
        change<AnimCE>(a, Consumer<AnimCE> { ac: AnimCE? ->
            jlu.setSelectedValue(ac, true)
            setA(ac)
        })
    }

    override fun mouseDragged(e: MouseEvent) {
        if (p == null) return
        ab.ori.x += p!!.x - e.x.toDouble()
        ab.ori.y += p!!.y - e.y.toDouble()
        p = e.point
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.source !is AnimBox) return
        p = e.point
    }

    override fun mouseReleased(e: MouseEvent) {
        p = null
    }

    override fun mouseWheel(e: MouseEvent) {
        if (e.source !is AnimBox) return
        val mwe: MouseWheelEvent = e as MouseWheelEvent
        val d: Double = mwe.getPreciseWheelRotation()
        ab.siz *= Math.pow(res, d)
    }

    override fun renew() {
        val da: AnimCE = jlu.getSelectedValue()
        val ani: Int = jlt.getSelectedIndex()
        val par: Int = maet.getSelectedRow()
        val row: Int = mpet.getSelectedRow()
        val vec: Vector<AnimCE?> = Vector<AnimCE?>()
        if (aep.focus == null) vec.addAll(AnimCE.Companion.map().values) else vec.add(aep.focus)
        change(0, { x: Int? ->
            jlu.setListData(vec)
            if (da != null && vec.contains(da)) {
                setA(da)
                jlu.setSelectedValue(da, true)
                if (ani >= 0 && ani < da.anims.size) {
                    setB(da, ani)
                    if (par >= 0 && par < maet.ma.parts.size) {
                        setC(par)
                        maet.setRowSelectionInterval(par, par)
                        if (row >= 0 && row < mpet.part!!.moves.size) {
                            setD(row)
                            mpet.setRowSelectionInterval(row, row)
                        }
                    }
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
        Page.Companion.set(addp, x, y, 300, 750, 200, 50)
        Page.Companion.set(remp, x, y, 300, 800, 200, 50)
        Page.Companion.set(lmul, x, y, 300, 650, 200, 50)
        Page.Companion.set(tmul, x, y, 300, 700, 200, 50)
        Page.Companion.set(jspv, x, y, 300, 850, 200, 450)
        Page.Companion.set(jspma, x, y, 500, 650, 900, 650)
        Page.Companion.set(jspmp, x, y, 1400, 650, 900, 650)
        Page.Companion.set(jspu, x, y, 0, 50, 300, 400)
        Page.Companion.set(jspt, x, y, 0, 450, 300, 300)
        Page.Companion.set(jspm, x, y, 0, 750, 300, 550)
        Page.Companion.set(ab, x, y, 300, 50, 700, 500)
        Page.Companion.set(jspp, x, y, 1000, 50, 300, 500)
        Page.Companion.set(sb, x, y, 1300, 50, 1000, 500)
        Page.Companion.set(addl, x, y, 2100, 550, 200, 50)
        Page.Companion.set(reml, x, y, 2100, 600, 200, 50)
        Page.Companion.set(jtl, x, y, 300, 550, 900, 100)
        Page.Companion.set(jtb, x, y, 1200, 550, 200, 50)
        Page.Companion.set(nex, x, y, 1200, 600, 200, 50)
        Page.Companion.set(inft, x, y, 1400, 550, 250, 50)
        Page.Companion.set(inff, x, y, 1650, 550, 250, 50)
        Page.Companion.set(infv, x, y, 1400, 600, 250, 50)
        Page.Companion.set(infm, x, y, 1650, 600, 250, 50)
        Page.Companion.set(advs, x, y, 1900, 550, 200, 50)
        Page.Companion.set(sort, x, y, 1900, 600, 200, 50)
        aep.componentResized(x, y)
        maet.setRowHeight(Page.Companion.size(x, y, 50))
        mpet.setRowHeight(Page.Companion.size(x, y, 50))
        sb.paint(sb.getGraphics())
        ab.paint(ab.getGraphics())
    }

    override fun timer(t: Int) {
        if (!pause) eupdate()
        if (ab.ent != null && mpet.part != null) {
            val p: Part = mpet.part
            val ep: EPart = ab.ent.ent.get(p.ints[0])
            inft.text = "frame: " + ab.ent.ind()
            inff.text = "part frame: " + (p.frame - p.off)
            infv.text = "actual value: " + ep.getVal(p.ints[1])
            infm.text = "part value: " + p.vd
        } else {
            inft.text = ""
            inff.text = ""
            infv.text = ""
            infm.text = ""
        }
        resized()
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlu.getValueIsAdjusting()) return
                setA(jlu.getSelectedValue())
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlt.getValueIsAdjusting()) return
                val da: AnimCE = jlu.getSelectedValue()
                val ind: Int = jlt.getSelectedIndex()
                setB(da, ind)
            }
        })
        jlp.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlp.getValueIsAdjusting()) return
                sb.sele = jlp.getSelectedIndex()
            }
        })
        jlm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (isAdj || jlm.getValueIsAdjusting() || maet.ma == null) return
                val ind: Int = jlm.getSelectedIndex()
                for (i in 0 until maet.ma.n) if (maet.ma.parts.get(i).ints.get(0) == ind) {
                    setC(i)
                    return
                }
                setC(-1)
            }
        })
    }

    private fun `addListeners$1`() {
        val lsm: ListSelectionModel = maet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                val ind: Int = maet.getSelectedRow()
                change(ind, { i: Int -> setC(i) })
            }
        })
        addp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                change(0, { x: Int? ->
                    val ind: Int = maet.getSelectedRow() + 1
                    val ma: MaAnim = maet.ma
                    val data: Array<Part> = ma.parts
                    ma.parts = arrayOfNulls<Part>(++ma.n)
                    for (i in 0 until ind) ma.parts.get(i) = data[i]
                    for (i in ind until data.size) ma.parts.get(i + 1) = data[i]
                    val np = Part()
                    np.validate()
                    ma.parts.get(ind) = np
                    ma.validate()
                    maet.anim.unSave("maanim add part")
                    callBack(null)
                    resized()
                    lsm.setSelectionInterval(ind, ind)
                    setC(ind)
                    val h: Int = mpet.getRowHeight()
                    mpet.scrollRectToVisible(Rectangle(0, h * ind, 1, h))
                })
            }
        })
        remp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                change(0, { x: Int? ->
                    val ma: MaAnim = maet.ma
                    val rows: IntArray = maet.getSelectedRows()
                    val data: Array<Part?> = ma.parts
                    for (row in rows) data[row] = null
                    ma.n -= rows.size
                    ma.parts = arrayOfNulls<Part>(ma.n)
                    var ind = 0
                    for (i in data.indices) if (data[i] != null) ma.parts.get(ind++) = data[i]
                    ind = rows[rows.size - 1]
                    ma.validate()
                    maet.anim.unSave("maanim remove part")
                    callBack(null)
                    if (ind >= ma.n) ind = ma.n - 1
                    lsm.setSelectionInterval(ind, ind)
                    setC(ind)
                })
            }
        })
        tmul.addFocusListener(object : FocusAdapter() {
            override fun focusLost(e: FocusEvent?) {
                val d: Double = CommonStatic.parseIntN(tmul.getText()) * 0.01
                if (!Opts.conf("times animation length by $d")) return
                for (p in maet.ma.parts) {
                    for (line in p.moves) (line[0] *= d).toInt()
                    p.off *= d.toInt()
                    p.validate()
                }
                maet.ma.validate()
                maet.anim.unSave("maanim multiply")
            }
        })
    }

    private fun `addListeners$2`() {
        val lsm: ListSelectionModel = mpet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (isAdj || lsm.getValueIsAdjusting()) return
                setD(lsm.getLeadSelectionIndex())
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val p: Part = mpet.part
                val data = p.moves
                p.moves = arrayOfNulls(++p.n)
                for (i in data.indices) p.moves[i] = data[i]
                p.moves[p.n - 1] = IntArray(4)
                p.validate()
                maet.ma.validate()
                callBack(null)
                maet.anim.unSave("maanim add line")
                resized()
                change(p.n - 1, { i: Int? -> lsm.setSelectionInterval(i, i) })
                setD(p.n - 1)
                val h: Int = mpet.getRowHeight()
                mpet.scrollRectToVisible(Rectangle(0, h * (p.n - 1), 1, h))
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val inds: IntArray = mpet.getSelectedRows()
                if (inds.size == 0) return
                val p: Part = mpet.part
                val l: MutableList<IntArray> = ArrayList()
                var j = 0
                for (i in 0 until p.n) if (j >= inds.size || i != inds[j]) l.add(p.moves[i]) else j++
                p.moves = l.toTypedArray()
                p.n = l.size
                p.validate()
                maet.ma.validate()
                callBack(null)
                maet.anim.unSave("maanim remove line")
                var ind = inds[0]
                if (ind >= p.n) ind--
                change(ind, { i: Int? -> lsm.setSelectionInterval(i, i) })
                setD(ind)
            }
        })
    }

    private fun `addListeners$3`() {
        jtb.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                pause = jtb.isSelected()
                jtl.setEnabled(pause && ab.ent != null)
            }
        })
        nex.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                eupdate()
            }
        })
        jtl.addChangeListener(ChangeListener {
            if (isAdj || !pause) return@ChangeListener
            ab.ent.setTime(jtl.getValue())
        })
        advs.setLnr(Supplier<Page> { AdvAnimEditPage(this, maet.anim, maet.anim.types.get(jlt.getSelectedIndex())) })
        sort.setLnr(Consumer { x: ActionEvent? -> Arrays.sort(maet.ma.parts) })
    }

    private fun eupdate() {
        ab.update()
        if (ab.ent != null) change(0, { x: Int? -> jtl.setValue(ab.ent.ind()) })
    }

    private fun ini() {
        add(aep)
        add(back)
        add(jspu)
        add(jspp)
        add(jspt)
        add(jspm)
        add(jspv)
        add(jspma)
        add(jspmp)
        add(addp)
        add(remp)
        add(addl)
        add(reml)
        add(jtb)
        add(jtl)
        add(nex)
        add(sb)
        add(ab)
        add(inft)
        add(inff)
        add(infv)
        add(infm)
        add(lmul)
        add(tmul)
        add(advs)
        add(sort)
        jlu.setCellRenderer(AnimLCR())
        inft.border = BorderFactory.createEtchedBorder()
        inff.border = BorderFactory.createEtchedBorder()
        infv.border = BorderFactory.createEtchedBorder()
        infm.border = BorderFactory.createEtchedBorder()
        lmul.border = BorderFactory.createEtchedBorder()
        addp.setEnabled(false)
        remp.setEnabled(false)
        addl.setEnabled(false)
        reml.setEnabled(false)
        jtl.setEnabled(false)
        jtl.setPaintTicks(true)
        jtl.setPaintLabels(true)
        addListeners()
        `addListeners$1`()
        `addListeners$2`()
        `addListeners$3`()
    }

    private fun setA(dan: AnimCE?) {
        change<AnimCE>(dan, Consumer<AnimCE> { anim: AnimCE? ->
            aep.setAnim(anim)
            if (anim == null) {
                jlt.setListData(arrayOfNulls<String>(0))
                sb.setAnim(null)
                jlp.setListData(arrayOfNulls<String>(0))
                setB(null, -1)
                return@change
            }
            var ind: Int = jlt.getSelectedIndex()
            val `val`: Array<String> = anim.names()
            jlt.setListData(`val`)
            if (ind >= `val`.size) ind = `val`.size - 1
            jlt.setSelectedIndex(ind)
            setB(anim, ind)
            sb.setAnim(anim)
            val ic: ImgCut = anim.imgcut
            var name = arrayOfNulls<String>(ic.n)
            for (i in 0 until ic.n) name[i] = i.toString() + " " + ic.strs.get(i)
            jlp.setListData(name)
            val mm: MaModel = anim.mamodel
            name = arrayOfNulls(mm.n)
            for (i in 0 until mm.n) name[i] = i.toString() + " " + mm.strs0.get(i)
            jlm.setListData(name)
        })
    }

    private fun setB(ac: AnimCE?, ind: Int) {
        change(0, { x: Int? ->
            val anim: MaAnim? = if (ac == null || ind < 0) null else ac.getMaAnim(ac.types.get(ind))
            addp.setEnabled(anim != null)
            tmul.setEditable(anim != null)
            advs.setEnabled(anim != null)
            sort.setEnabled(anim != null)
            jtl.setEnabled(anim != null)
            if (ac == null || ind == -1) {
                maet.setAnim(null, null)
                ab.setEntity(null)
                setC(-1)
                return@change
            }
            var row: Int = maet.getSelectedRow()
            maet.setAnim(ac, anim)
            ab.setEntity(ac.getEAnim(ac.types.get(ind)))
            if (row >= maet.getRowCount()) {
                maet.clearSelection()
                row = -1
            }
            setC(row)
            jtl.setMinimum(0)
            jtl.setMaximum(ab.ent.len())
            jtl.setLabelTable(null)
            if (ab.ent.len() <= 50) {
                jtl.setMajorTickSpacing(5)
                jtl.setMinorTickSpacing(1)
            } else if (ab.ent.len() <= 200) {
                jtl.setMajorTickSpacing(10)
                jtl.setMinorTickSpacing(2)
            } else if (ab.ent.len() <= 1000) {
                jtl.setMajorTickSpacing(50)
                jtl.setMinorTickSpacing(10)
            } else if (ab.ent.len() <= 5000) {
                jtl.setMajorTickSpacing(250)
                jtl.setMinorTickSpacing(50)
            } else {
                jtl.setMajorTickSpacing(1000)
                jtl.setMinorTickSpacing(200)
            }
        })
    }

    private fun setC(ind: Int) {
        remp.setEnabled(ind >= 0)
        addl.setEnabled(ind >= 0)
        val p: Part? = if (ind < 0 || ind >= maet.ma.parts.size) null else maet.ma.parts.get(ind)
        change(0, { x: Int? ->
            mpet.setAnim(maet.anim, maet.ma, p)
            mpet.clearSelection()
            ab.setSele(p?.ints?.get(0) ?: -1)
            if (ind >= 0) {
                val par = p!!.ints[0]
                jlm.setSelectedIndex(par)
                jlv.setSelectedIndex(mpet.part!!.ints.get(1))
                if (maet.getSelectedRow() != ind) {
                    maet.setRowSelectionInterval(ind, ind)
                    maet.scrollRectToVisible(maet.getCellRect(ind, 0, true))
                }
                ab.setSele(par)
                val ic: Int = mpet.anim.mamodel.parts.get(par).get(2)
                jlp.setSelectedIndex(ic)
                val r: Rectangle = jlp.getCellBounds(ic, ic)
                if (r != null) jlp.scrollRectToVisible(r)
                sb.sele = jlp.getSelectedIndex()
            } else maet.clearSelection()
        })
        setD(-1)
    }

    private fun setD(ind: Int) {
        reml.setEnabled(ind >= 0)
        if (ind >= 0 && mpet.part!!.ints.get(1) == 2) {
            change(mpet.part!!.moves.get(ind).get(1), Consumer { i: Int? -> jlp.setSelectedIndex(i) })
            sb.sele = jlp.getSelectedIndex()
        }
    }

    companion object {
        private const val serialVersionUID = 1L
        private const val res = 0.95
        private val mod = arrayOf("0 parent", "1 id", "2 sprite", "3 z-order", "4 pos-x",
                "5 pos-y", "6 pivot-x", "7 pivot-y", "8 scale", "9 scale-x", "10 scale-y", "11 angle", "12 opacity",
                "13 horizontal flip", "14 vertical flip", "50 extend")
    }
}