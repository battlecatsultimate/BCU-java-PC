package page.animimport

import common.pack.UserProfile
import page.Page
import utilpc.Algorithm
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class ImgCutEditPage : Page, AbEditPage {
    private val jtf: JTF = JTF()
    private val resz: JTF = JTF("resize to: _%")
    private val back: JBTN = JBTN(0, "back")
    private val add: JBTN = JBTN(0, "add")
    private val rem: JBTN = JBTN(0, "rem")
    private val copy: JBTN = JBTN(0, "copy")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val relo: JBTN = JBTN(0, "relo")
    private val save: JBTN = JBTN(0, "saveimg")
    private val swcl: JBTN = JBTN(0, "swcl")
    private val impt: JBTN = JBTN(0, "import")
    private val expt: JBTN = JBTN(0, "export")
    private val ico: JBTN = JBTN(0, "icon")
    private val loca: JBTN = JBTN(0, "localize")
    private val merg: JBTN = JBTN(0, "merge")
    private val spri: JBTN = JBTN(0, "sprite")
    private val icon = JLabel()
    private val jlu: JList<AnimCE> = JList<AnimCE>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlf: JList<String> = JList<String>(ReColor.strs)
    private val jspf: JScrollPane = JScrollPane(jlf)
    private val jlt: JList<String> = JList<String>(ReColor.strf)
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val icet: ImgCutEditTable = ImgCutEditTable()
    private val jspic: JScrollPane = JScrollPane(icet)
    private val sb: SpriteBox = SpriteBox(this)
    private val aep: EditHead
    private var sep: SpriteEditPage? = null
    private var changing = false

    constructor(p: Page?) : super(p) {
        aep = EditHead(this, 1)
        if (aep.focus == null) jlu.setListData(Vector<AnimCE>(AnimCE.Companion.map().values)) else jlu.setListData(arrayOf<AnimCE?>(aep.focus))
        ini()
        resized()
    }

    constructor(p: Page?, bar: EditHead) : super(p) {
        aep = bar
        if (aep.focus == null) jlu.setListData(Vector<AnimCE>(Workspace.Companion.loadAnimations(null))) else jlu.setListData(arrayOf<AnimCE?>(aep.focus))
        ini()
        resized()
    }

    override fun callBack(o: Any?) {
        changing = true
        if (sb.sele >= 0) {
            icet.getSelectionModel().setSelectionInterval(sb.sele, sb.sele)
            val h: Int = icet.getRowHeight()
            icet.scrollRectToVisible(Rectangle(0, h * sb.sele, 1, h))
        } else icet.clearSelection()
        setB(sb.sele)
        changing = false
    }

    override fun setSelection(ac: AnimCE?) {
        changing = true
        jlu.setSelectedValue(ac, true)
        setA(ac)
        changing = false
    }

    override fun keyPressed(ke: KeyEvent) {
        if (ke.source === sb) sb.keyPressed(ke)
    }

    override fun keyReleased(ke: KeyEvent) {
        if (ke.source === sb) sb.keyReleased(ke)
    }

    override fun keyTyped(ke: KeyEvent) {
        if (ke.source === sb) sb.keyTyped(ke)
    }

    override fun mouseDragged(me: MouseEvent) {
        if (me.source === sb) sb.mouseDragged(me.point)
    }

    override fun mousePressed(me: MouseEvent) {
        if (me.source === sb) sb.mousePressed(me.point)
    }

    override fun mouseReleased(me: MouseEvent) {
        if (me.source === sb) sb.mouseReleased(me.point)
    }

    override fun renew() {
        if (sep != null && Opts.conf("Do you want to save edited sprite?")) try {
            icet.anim.setNum(FakeImage.Companion.read(sep.getEdit()))
            icet.anim.saveImg()
            icet.anim.reloImg()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        sep = null
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(aep, x, y, 550, 0, 1750, 50)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(relo, x, y, 250, 0, 200, 50)
        Page.Companion.set(jspu, x, y, 0, 50, 300, 500)
        Page.Companion.set(add, x, y, 350, 200, 200, 50)
        Page.Companion.set(rem, x, y, 600, 200, 200, 50)
        Page.Companion.set(impt, x, y, 350, 250, 200, 50)
        Page.Companion.set(expt, x, y, 600, 250, 200, 50)
        Page.Companion.set(resz, x, y, 350, 300, 200, 50)
        Page.Companion.set(loca, x, y, 600, 300, 200, 50)
        Page.Companion.set(merg, x, y, 350, 350, 200, 50)
        Page.Companion.set(spri, x, y, 600, 350, 200, 50)
        Page.Companion.set(jtf, x, y, 350, 100, 200, 50)
        Page.Companion.set(copy, x, y, 600, 100, 200, 50)
        Page.Companion.set(addl, x, y, 350, 500, 200, 50)
        Page.Companion.set(reml, x, y, 600, 500, 200, 50)
        Page.Companion.set(jspic, x, y, 50, 600, 800, 650)
        Page.Companion.set(sb, x, y, 900, 100, 1400, 900)
        Page.Companion.set(jspf, x, y, 900, 1050, 200, 200)
        Page.Companion.set(jspt, x, y, 1150, 1050, 200, 200)
        Page.Companion.set(swcl, x, y, 1400, 1050, 200, 50)
        Page.Companion.set(save, x, y, 1400, 1150, 200, 50)
        Page.Companion.set(ico, x, y, 1650, 1050, 200, 50)
        Page.Companion.set(icon, x, y, 1650, 1100, 400, 100)
        aep.componentResized(x, y)
        icet.setRowHeight(Page.Companion.size(x, y, 50))
        sb.paint(sb.getGraphics())
    }

    private fun `addListeners$0`() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        add.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("Add your sprite").getImg() ?: return
                changing = true
                val str: String = AnimCE.Companion.getAvailable("new anim")
                val ac = AnimCE(str)
                try {
                    ac.setNum(FakeImage.Companion.read(bimg))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                ac.saveImg()
                ac.createNew()
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        impt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("Update your sprite").getImg()
                if (bimg != null) {
                    val ac: AnimCE = icet.anim
                    try {
                        ac.setNum(FakeImage.Companion.read(bimg))
                        ac.saveImg()
                        ac.reloImg()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
        expt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                Exporter(icet.anim.getNum().bimg() as BufferedImage, Exporter.Companion.EXP_IMG)
            }
        })
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlu.getValueIsAdjusting()) return
                changing = true
                setA(jlu.getSelectedValue())
                changing = false
            }
        })
        jtf.addFocusListener(object : FocusAdapter() {
            override fun focusLost(arg0: FocusEvent?) {
                changing = true
                var str: String = jtf.getText().trim { it <= ' ' }
                str = MainBCU.validate(str)
                if (str.length == 0 || icet.anim == null || icet.anim.id.id == str) {
                    if (icet.anim != null) jtf.setText(icet.anim.id.id)
                    return
                }
                val rep: AnimCE = AnimCE.Companion.map().get(str)
                if (rep != null && Opts.conf(
                                "Do you want to replace animation? This action cannot be undone. The animation which originally keeps this name will be replaced by selected animation.")) {
                    icet.anim.renameTo(str)
                    for (pack in UserProfile.Companion.getUserPacks()) for (e in pack.enemies.getList()) if (e.anim === rep) e.anim = icet.anim
                    val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                    jlu.setListData(v)
                    jlu.setSelectedValue(rep, true)
                    setA(rep)
                } else {
                    str = AnimCE.Companion.getAvailable(str)
                    icet.anim.renameTo(str)
                    jtf.setText(str)
                }
                changing = false
            }
        })
        copy.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                var str: String = icet.anim.id.id
                str = AnimCE.Companion.getAvailable(str)
                val ac = AnimCE(str, icet.anim)
                ac.setEdi(icet.anim.getEdi())
                ac.setUni(icet.anim.getUni())
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        rem.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            changing = true
            var ind: Int = jlu.getSelectedIndex()
            val ac: AnimCE = icet.anim
            ac.delete()
            val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
            jlu.setListData(v)
            if (ind >= v.size) ind--
            jlu.setSelectedIndex(ind)
            setA(if (ind < 0) null else v[ind])
            changing = false
        }
        )
        loca.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            changing = true
            var ind: Int = jlu.getSelectedIndex()
            val ac: AnimCE = icet.anim
            ac.check()
            ac.delete()
            val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
            jlu.setListData(v)
            if (ind >= v.size) ind--
            jlu.setSelectedIndex(ind)
            setA(v[ind])
            changing = false
        }
        )
        relo.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (icet.anim == null) return
                icet.anim.reloImg()
                icet.anim.ICedited()
            }
        })
        save.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                icet.anim.saveImg()
            }
        })
        ico.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val bimg: BufferedImage = Importer("select enemy icon").getImg() ?: return
                icet.anim.setEdi(VImg(bimg))
                icet.anim.saveIcon()
                if (icet.anim.getEdi() != null) icon.icon = UtilPC.getIcon(icet.anim.getEdi())
            }
        })
    }

    private fun `addListeners$1`() {
        val lsm: ListSelectionModel = icet.getSelectionModel()
        lsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || lsm.getValueIsAdjusting()) return
                changing = true
                setB(lsm.getLeadSelectionIndex())
                changing = false
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ic: ImgCut = icet.ic
                val data: Array<IntArray> = ic.cuts
                val name: Array<String> = ic.strs
                ic.cuts = arrayOfNulls<IntArray>(++ic.n)
                ic.strs = arrayOfNulls<String>(ic.n)
                for (i in data.indices) {
                    ic.cuts.get(i) = data[i]
                    ic.strs.get(i) = name[i]
                }
                val ind: Int = icet.getSelectedRow()
                if (ind >= 0) ic.cuts.get(ic.n - 1) = ic.cuts.get(ind).clone() else ic.cuts.get(ic.n - 1) = intArrayOf(0, 0, 1, 1)
                ic.strs.get(ic.n - 1) = ""
                icet.anim.unSave("imgcut add line")
                resized()
                lsm.setSelectionInterval(ic.n - 1, ic.n - 1)
                val h: Int = icet.getRowHeight()
                icet.scrollRectToVisible(Rectangle(0, h * (ic.n - 1), 1, h))
                setB(ic.n - 1)
                changing = false
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ic: ImgCut = icet.ic
                var ind: Int = sb.sele
                val data: Array<IntArray> = ic.cuts
                val name: Array<String> = ic.strs
                ic.cuts = arrayOfNulls<IntArray>(--ic.n)
                ic.strs = arrayOfNulls<String>(ic.n)
                for (i in 0 until ind) {
                    ic.cuts.get(i) = data[i]
                    ic.strs.get(i) = name[i]
                }
                for (i in ind + 1 until data.size) {
                    ic.cuts.get(i - 1) = data[i]
                    ic.strs.get(i - 1) = name[i]
                }
                for (ints in icet.anim.mamodel.parts) if (ints[2] > ind) ints[2]--
                for (ma in icet.anim.anims) for (part in ma.parts) if (part.ints[1] == 2) for (ints in part.moves) if (ints[1] > ind) ints[1]--
                icet.anim.ICedited()
                icet.anim.unSave("imgcut remove line")
                if (ind >= ic.n) ind--
                lsm.setSelectionInterval(ind, ind)
                setB(ind)
                changing = false
            }
        })
        swcl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = sb.sele
                var data: IntArray? = null
                if (ind >= 0) {
                    val ic: ImgCut = icet.ic
                    data = ic.cuts.get(ind)
                }
                ReColor.transcolor(icet.anim.getNum().bimg() as BufferedImage, data, jlf.getSelectedIndex(),
                        jlt.getSelectedIndex())
                icet.anim.getNum().mark(Marker.RECOLORED)
                icet.anim.ICedited()
            }
        })
        jlf.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jlf.getSelectedIndex() == -1) jlf.setSelectedIndex(0)
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jlt.getSelectedIndex() == -1) jlt.setSelectedIndex(0)
            }
        })
        resz.setLnr(Consumer<FocusEvent> { x: FocusEvent? ->
            val d: Double = CommonStatic.parseIntN(resz.getText()) * 0.01
            if (Opts.conf("do you want to resize sprite to $d%?")) {
                icet.anim.resize(d)
                icet.anim.ICedited()
                icet.anim.unSave("resized")
            }
            resz.setText("resize to: _%")
        })
        merg.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                changing = true
                val str: String = AnimCE.Companion.getAvailable("merged")
                val list: Array<AnimCE> = jlu.getSelectedValuesList().toTypedArray()
                val rect = Array(list.size) { IntArray(2) }
                for (i in list.indices) {
                    rect[i][0] = list[i].getNum().getWidth()
                    rect[i][1] = list[i].getNum().getHeight()
                }
                val ans: SRResult = Algorithm.stackRect(rect)
                val cen: AnimCE = list[ans.center]
                val ac = AnimCE(str, cen)
                val bimg = BufferedImage(ans.w, ans.h, BufferedImage.TYPE_INT_ARGB)
                val g: Graphics = bimg.getGraphics()
                for (i in list.indices) {
                    val b: BufferedImage = list[i].getNum().bimg() as BufferedImage
                    val x: Int = ans.pos.get(i).get(0)
                    val y: Int = ans.pos.get(i).get(1)
                    g.drawImage(b, x, y, null)
                    if (i != ans.center) ac.merge(list[i], x, y)
                }
                try {
                    ac.setNum(FakeImage.Companion.read(bimg))
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
                ac.saveImg()
                ac.reloImg()
                ac.unSave("merge")
                val v: Vector<AnimCE> = Vector<AnimCE>(AnimCE.Companion.map().values)
                jlu.setListData(v)
                jlu.setSelectedValue(ac, true)
                setA(ac)
                changing = false
            }
        })
        spri.setLnr(Consumer { x: ActionEvent? -> changePanel(SpriteEditPage(this, icet.anim.getNum().bimg() as BufferedImage).also { sep = it }) })
    }

    private fun ini() {
        add(aep)
        add(resz)
        add(back)
        add(relo)
        add(save)
        add(swcl)
        add(jspu)
        add(jspic)
        add(add)
        add(rem)
        add(copy)
        add(addl)
        add(reml)
        add(jtf)
        add(sb)
        add(jspf)
        add(jspt)
        add(impt)
        add(expt)
        add(icon)
        add(loca)
        add(ico)
        add(merg)
        add(spri)
        add.setEnabled(aep.focus == null)
        jtf.setEnabled(aep.focus == null)
        relo.setEnabled(aep.focus == null)
        swcl.setEnabled(aep.focus == null)
        jlu.setCellRenderer(AnimLCR())
        setA(null)
        jlf.setSelectedIndex(0)
        jlt.setSelectedIndex(1)
        `addListeners$0`()
        `addListeners$1`()
    }

    private fun setA(anim: AnimCE?) {
        val boo = changing
        changing = true
        aep.setAnim(anim)
        addl.setEnabled(anim != null)
        swcl.setEnabled(anim != null)
        save.setEnabled(anim != null)
        resz.setEditable(anim != null)
        icet.setCut(anim)
        sb.setAnim(anim)
        if (sb.sele == -1) icet.clearSelection()
        jtf.setEnabled(anim != null)
        jtf.setText(if (anim == null) "" else anim.id.id)
        val del = anim != null && anim.deletable()
        rem.setEnabled(aep.focus == null && anim != null && del)
        loca.setEnabled(aep.focus == null && anim != null && !del && !anim.inPool())
        copy.setEnabled(aep.focus == null && anim != null)
        impt.setEnabled(anim != null)
        expt.setEnabled(anim != null)
        spri.setEnabled(anim != null)
        merg.setEnabled(jlu.getSelectedValuesList().size > 1)
        if (anim != null && anim.getEdi() != null) icon.icon = UtilPC.getIcon(anim.getEdi())
        setB(sb.sele)
        changing = boo
    }

    private fun setB(row: Int) {
        sb.sele = icet.getSelectedRow()
        reml.setEnabled(sb.sele != -1)
        if (sb.sele >= 0) {
            for (ints in icet.anim.mamodel.parts) if (ints[2] == sb.sele) reml.setEnabled(false)
            for (ma in icet.anim.anims) for (part in ma.parts) if (part.ints[1] == 2) for (ints in part.moves) if (ints[1] == sb.sele) reml.setEnabled(false)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}