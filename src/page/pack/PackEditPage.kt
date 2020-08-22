package page.packimport

import common.pack.UserProfile
import common.util.Data
import page.Page
import java.awt.Color
import java.awt.event.ActionEvent
import java.util.*
import java.util.function.Consumer
import java.util.function.Supplier

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class PackEditPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val vpack: Vector<UserPack?> = Vector<UserPack>(UserProfile.Companion.getUserPacks())
    private val jlp: JList<UserPack> = JList<UserPack>(vpack)
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jle: JList<Enemy> = JList<Enemy>()
    private val jspe: JScrollPane = JScrollPane(jle)
    private val jld: JList<AnimCE> = JList<AnimCE>(Vector<AnimCE>(AnimCE.Companion.map().values))
    private val jspd: JScrollPane = JScrollPane(jld)
    private val jls: ReorderList<StageMap> = ReorderList<StageMap>()
    private val jsps: JScrollPane = JScrollPane(jls)
    private val jlr: JList<UserPack> = JList<UserPack>()
    private val jspr: JScrollPane = JScrollPane(jlr)
    private val jlt: JList<UserPack> = JList<UserPack>(vpack)
    private val jspt: JScrollPane = JScrollPane(jlt)
    private val addp: JBTN = JBTN(0, "add")
    private val remp: JBTN = JBTN(0, "rem")
    private val adde: JBTN = JBTN(0, "add")
    private val reme: JBTN = JBTN(0, "rem")
    private val adds: JBTN = JBTN(0, "add")
    private val rems: JBTN = JBTN(0, "rem")
    private val addr: JBTN = JBTN(0, "add")
    private val remr: JBTN = JBTN(0, "rem")
    private val edit: JBTN = JBTN(0, "edit")
    private val sdiy: JBTN = JBTN(0, "sdiy")
    private val vene: JBTN = JBTN(0, "vene")
    private val extr: JBTN = JBTN(0, "extr")
    private val vcas: JBTN = JBTN(0, "vcas")
    private val vbgr: JBTN = JBTN(0, "vbgr")
    private val vrcg: JBTN = JBTN(0, "recg")
    private val vrlr: JBTN = JBTN(0, "relr")
    private val cunt: JBTN = JBTN(0, "cunt")
    private val ener: JBTN = JBTN(0, "ener")
    private val vmsc: JBTN = JBTN(0, "vmsc")
    private val unpk: JBTN = JBTN(0, "unpack")
    private val recd: JBTN = JBTN(0, "replay")
    private val jtfp: JTF = JTF()
    private val jtfe: JTF = JTF()
    private val jtfs: JTF = JTF()
    private val lbp: JL = JL(0, "pack")
    private val lbe: JL = JL(0, "enemy")
    private val lbd: JL = JL(0, "seleanim")
    private val lbs: JL = JL(0, "stage")
    private val lbr: JL = JL(0, "parent")
    private val lbt: JL = JL(0, "selepar")
    private var pac: UserPack? = null
    private var ene: Enemy? = null
    private var sm: StageMap? = null
    private var changing = false
    override fun renew() {
        setPack(pac)
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        var w = 50
        val dw = 150
        Page.Companion.set(lbp, x, y, w, 100, 400, 50)
        Page.Companion.set(jspp, x, y, w, 150, 400, 600)
        Page.Companion.set(addp, x, y, w, 800, 200, 50)
        Page.Companion.set(remp, x, y, w + 200, 800, 200, 50)
        Page.Companion.set(jtfp, x, y, w, 850, 400, 50)
        Page.Companion.set(extr, x, y, w, 950, 200, 50)
        Page.Companion.set(unpk, x, y, w + 200, 950, 200, 50)
        w += 450
        Page.Companion.set(lbe, x, y, w, 100, 300, 50)
        Page.Companion.set(jspe, x, y, w, 150, 300, 600)
        Page.Companion.set(adde, x, y, w, 800, 150, 50)
        Page.Companion.set(reme, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(jtfe, x, y, w, 850, 300, 50)
        Page.Companion.set(edit, x, y, w, 950, 300, 50)
        Page.Companion.set(vene, x, y, w, 1050, 300, 50)
        Page.Companion.set(ener, x, y, w, 1150, 300, 50)
        w += 300
        Page.Companion.set(lbd, x, y, w, 100, 300, 50)
        Page.Companion.set(jspd, x, y, w, 150, 300, 600)
        w += 50
        Page.Companion.set(vbgr, x, y, w, 850, 250, 50)
        Page.Companion.set(vcas, x, y, w, 950, 250, 50)
        Page.Companion.set(vrcg, x, y, w, 1050, 250, 50)
        Page.Companion.set(vrlr, x, y, w, 1150, 250, 50)
        w += 300
        Page.Companion.set(lbs, x, y, w, 100, 300, 50)
        Page.Companion.set(jsps, x, y, w, 150, 300, 600)
        Page.Companion.set(adds, x, y, w, 800, 150, 50)
        Page.Companion.set(rems, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(jtfs, x, y, w, 850, 300, 50)
        Page.Companion.set(sdiy, x, y, w, 950, 300, 50)
        Page.Companion.set(cunt, x, y, w, 1050, 300, 50)
        Page.Companion.set(vmsc, x, y, w, 1150, 300, 50)
        w += 350
        Page.Companion.set(lbr, x, y, w, 100, 350, 50)
        Page.Companion.set(jspr, x, y, w, 150, 350, 600)
        Page.Companion.set(addr, x, y, w, 800, 175, 50)
        Page.Companion.set(remr, x, y, w + 175, 800, 175, 50)
        Page.Companion.set(recd, x, y, w, 950, 300, 50)
        w += 350
        Page.Companion.set(lbt, x, y, w, 100, 350, 50)
        Page.Companion.set(jspt, x, y, w, 150, 350, 600)
    }

    private fun addListeners() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        recd.setLnr(Consumer { x: ActionEvent? -> changePanel(RecdPackPage(this, pac)) })
        vcas.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CastleEditPage(getThis(), pac)) else if (pac != null) changePanel(CastleViewPage(getThis(), pac.castles))
            }
        })
        vbgr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(BGEditPage(getThis(), pac)) else if (pac != null) changePanel(BGViewPage(getThis(), pac.getID()))
            }
        })
        vrcg.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CGLREditPage(getThis(), pac)) else changePanel(CharaGroupPage(getThis(), pac, true))
            }
        })
        vrlr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac != null && pac.editable) changePanel(CGLREditPage(getThis(), pac)) else changePanel(LvRestrictPage(getThis(), pac, true))
            }
        })
        jld.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jld.getValueIsAdjusting()) return
                adde.setEnabled(pac != null && jld.getSelectedValue() != null && pac.editable)
            }
        })
    }

    private fun `addListeners$1`() {
        addp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val str: String = Opts.read("pack ID = ") // FIXME
                pac = Data.Companion.err<UserPack>(SupExc<UserPack> { UserProfile.Companion.initJsonPack(str) })
                vpack.add(pac)
                jlp.setListData(vpack)
                jlt.setListData(vpack)
                jlp.setSelectedValue(pac, true)
                setPack(pac)
                changing = false
            }
        })
        remp.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jlp.getSelectedIndex()
                UserProfile.Companion.remove(pac)
                pac.delete()
                vpack.remove(pac)
                jlp.setListData(vpack)
                jlt.setListData(vpack)
                if (ind > 0) ind--
                jlp.setSelectedIndex(ind)
                setPack(jlp.getSelectedValue())
                changing = false
            }
        })
        jlp.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jlp.getValueIsAdjusting()) return
                changing = true
                setPack(jlp.getSelectedValue())
                changing = false
            }
        })
        jtfp.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                val str: String = jtfp.getText().trim { it <= ' ' }
                if (pac.desc.name == str) return
                pac.desc.name = str
            }
        })
        extr.setLnr(Consumer { x: ActionEvent? -> })
        unpk.setLnr(Consumer { x: ActionEvent? ->
            val str: String = Opts.read("password: ") // FIXME
            Data.Companion.err<Workspace>(SupExc<Workspace> { (pac.source as ZipSource).unzip(str) })
            unpk.setEnabled(false)
            extr.setEnabled(true)
        })
    }

    private fun `addListeners$2`() {
        jle.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jle.getValueIsAdjusting()) return
                changing = true
                setEnemy(jle.getSelectedValue())
                changing = false
            }
        })
        adde.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ce = CustomEnemy()
                val anim: AnimCE = jld.getSelectedValue()
                val e = Enemy(pac.getNextID<Enemy, AbEnemy>(Enemy::class.java), anim, ce)
                pac.enemies.add(e)
                jle.setListData(pac.enemies.getList().toTypedArray())
                jle.setSelectedValue(e, true)
                setEnemy(e)
                changing = false
            }
        })
        reme.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jle.getSelectedIndex()
                pac.enemies.remove(ene)
                jle.setListData(pac.enemies.getList().toTypedArray())
                if (ind >= 0) ind--
                jle.setSelectedIndex(ind)
                setEnemy(jle.getSelectedValue())
                changing = false
            }
        })
        edit.setLnr(Supplier<Page> { EnemyEditPage(getThis(), ene) })
        jtfe.setLnr(Consumer<FocusEvent> { e: FocusEvent? -> ene.name = jtfe.getText().trim { it <= ' ' } })
        vene.setLnr(Supplier<Page> { EnemyViewPage(getThis(), pac.getID()) })
        ener.setLnr(Supplier<Page> { EREditPage(getThis(), pac) })
    }

    private fun `addListeners$3`() {
        sdiy.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (pac.editable) changePanel(StageEditPage(getThis(), pac.mc, pac)) else {
                    val lmc: List<MapColc> = Arrays.asList(*arrayOf<MapColc>(pac.mc))
                    changePanel(StageViewPage(getThis(), lmc))
                }
            }
        })
        cunt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(UnitManagePage(getThis(), pac))
            }
        })
        vmsc.setLnr(Supplier<Page> { if (pac.editable) MusicEditPage(getThis(), pac) else MusicPage(getThis(), pac.musics.getList()) })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jls.getValueIsAdjusting()) return
                changing = true
                setMap(jls.getSelectedValue())
                changing = false
            }
        })
        jls.list = object : ReorderListener<StageMap?> {
            override fun reordered(ori: Int, fin: Int) {
                val lsm: MutableList<StageMap> = ArrayList<StageMap>()
                for (sm in pac.mc.maps) lsm.add(sm)
                val sm: StageMap = lsm.removeAt(ori)
                lsm.add(fin, sm)
                pac.mc.maps = lsm.toTypedArray()
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
        jtfs.setLnr(Consumer<FocusEvent> { x: FocusEvent? -> if (sm != null) sm.name = jtfs.getText().trim { it <= ' ' } })
        adds.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val map = StageMap(pac.mc)
                val maps: Array<StageMap> = pac.mc.maps
                pac.mc.maps = Arrays.copyOf(maps, maps.size + 1)
                pac.mc.maps.get(maps.size) = map
                jls.setListData(pac.mc.maps)
                jls.setSelectedValue(map, true)
                setMap(map)
                changing = false
            }
        })
        rems.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jls.getSelectedIndex()
                val n: Int = pac.mc.maps.size
                val maps: Array<StageMap?> = arrayOfNulls<StageMap>(n - 1)
                for (i in 0 until ind) maps[i] = pac.mc.maps.get(i)
                for (i in ind + 1 until n) maps[i - 1] = pac.mc.maps.get(i)
                pac.mc.maps = maps
                jls.setListData(maps)
                if (ind >= 0) ind--
                jls.setSelectedIndex(ind)
                setMap(jls.getSelectedValue())
                changing = false
            }
        })
    }

    private fun `addListeners$4`() {
        jlr.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setRely(jlr.getSelectedValue())
                changing = false
            }
        })
        jlt.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                checkAddr()
            }
        })
        addr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val rel: UserPack = jlt.getSelectedValue()
                pac.desc.dependency.add(rel.getID())
                for (id in rel.desc.dependency) if (!pac.desc.dependency.contains(id)) pac.desc.dependency.add(id)
                updateJlr()
                jlr.setSelectedValue(rel, true)
                setRely(rel)
                changing = false
            }
        })
        remr.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val ind: Int = jlr.getSelectedIndex() - 1
                val rel: UserPack = jlr.getSelectedValue()
                if (pac.relyOn(rel.getID())) if (Opts.conf("this action cannot be undone. Are you sure to remove "
                                + "all elements in this pack from the selected parent?")) pac.forceRemoveParent(rel.getID())
                pac.desc.dependency.remove(rel.getID())
                updateJlr()
                jlr.setSelectedIndex(ind)
                setRely(jlr.getSelectedValue())
                changing = false
            }
        })
    }

    private fun checkAddr() {
        if (pac == null) {
            addr.setEnabled(false)
            return
        }
        val rel: UserPack = jlt.getSelectedValue()
        var b: Boolean = pac.editable
        b = b and (rel != null && !pac.desc.dependency.contains(rel.getID()))
        b = b and (rel !== pac)
        if (b) for (id in rel.desc.dependency) if (id == pac.getID()) b = false
        addr.setEnabled(b)
    }

    private fun ini() {
        add(back)
        add(jspp)
        add(jspe)
        add(jspd)
        add(addp)
        add(remp)
        add(jtfp)
        add(adde)
        add(reme)
        add(jtfe)
        add(edit)
        add(sdiy)
        add(jsps)
        add(adds)
        add(rems)
        add(jtfs)
        add(extr)
        add(jspr)
        add(jspt)
        add(addr)
        add(remr)
        add(vene)
        add(lbp)
        add(lbe)
        add(lbd)
        add(lbs)
        add(lbr)
        add(lbt)
        add(cunt)
        add(vcas)
        add(vrcg)
        add(vrlr)
        add(vbgr)
        add(ener)
        add(vmsc)
        add(unpk)
        add(recd)
        jle.setCellRenderer(AnimLCR())
        jld.setCellRenderer(AnimLCR())
        setPack(null)
        addListeners()
        `addListeners$1`()
        `addListeners$2`()
        `addListeners$3`()
        `addListeners$4`()
    }

    private fun setEnemy(e: Enemy?) {
        ene = e
        val b = e != null && pac.editable
        edit.setEnabled(e != null && e.de is CustomEnemy)
        jtfe.setEnabled(b)
        reme.setEnabled(b)
        if (b) {
            jtfe.setText(e.name)
            reme.setEnabled(e.findApp(pac.mc).size == 0)
        }
    }

    private fun setMap(map: StageMap?) {
        sm = map
        rems.setEnabled(sm != null && pac.editable)
        jtfs.setEnabled(sm != null && pac.editable)
        if (sm != null) jtfs.setText(map.name)
    }

    private fun setPack(pack: UserPack?) {
        pac = pack
        val b = pac != null && pac.editable
        remp.setEnabled(pac != null)
        jtfp.setEnabled(b)
        adde.setEnabled(b && jld.getSelectedValue() != null)
        adds.setEnabled(b)
        extr.setEnabled(pac != null)
        vcas.setEnabled(pac != null)
        vbgr.setEnabled(pac != null)
        vene.setEnabled(pac != null)
        vmsc.setEnabled(pac != null)
        recd.setEnabled(pac != null)
        val canUnpack = pac != null && !pac.editable
        val canExport = pac != null && pac.editable
        unpk.setEnabled(canUnpack)
        extr.setEnabled(canExport)
        if (b) jtfp.setText(pack.desc.name)
        if (pac == null) {
            jle.setListData(arrayOfNulls<Enemy>(0))
            jlr.setListData(arrayOfNulls<UserPack>(0))
        } else {
            jle.setListData(pac.enemies.getList().toTypedArray())
            jle.clearSelection()
            updateJlr()
            jlr.clearSelection()
        }
        checkAddr()
        val b0 = pac != null
        sdiy.setEnabled(b0)
        if (b0) {
            jls.setListData(pac.mc.maps)
            jls.clearSelection()
        } else jls.setListData(arrayOfNulls<StageMap>(0))
        setRely(null)
        setMap(null)
        setEnemy(null)
    }

    private fun setRely(rel: UserPack?) {
        if (pac == null || rel == null) {
            remr.setEnabled(false)
            return
        }
        val re: Boolean = pac.relyOn(rel.getID())
        remr.setText(0, if (re) "rema" else "rem")
        remr.setForeground(if (re) Color.RED else Color.BLACK)
        remr.setEnabled(true)
    }

    private fun updateJlr() {
        val rel: Array<UserPack?> = arrayOfNulls<UserPack>(pac.desc.dependency.size)
        for (i in pac.desc.dependency.indices) rel[i] = UserProfile.Companion.getUserPack(pac.desc.dependency.get(i))
        jlr.setListData(rel)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
    }
}