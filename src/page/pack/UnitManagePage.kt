package page.packimport

import common.pack.UserProfile
import common.util.unit.Form
import common.util.unit.Unit
import page.Page
import java.awt.event.ActionEvent
import java.util.*

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class UnitManagePage(p: Page?, pack: UserPack?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val vpack: Vector<UserPack> = Vector<UserPack>(UserProfile.Companion.getUserPacks())
    private val jlp: JList<UserPack> = JList<UserPack>(vpack)
    private val jspp: JScrollPane = JScrollPane(jlp)
    private val jlu: JList<Unit> = JList<Unit>()
    private val jspu: JScrollPane = JScrollPane(jlu)
    private val jlf: ReorderList<Form> = ReorderList<Form>()
    private val jspf: JScrollPane = JScrollPane(jlf)
    private val jld: JList<AnimCE> = JList<AnimCE>(Vector<AnimCE>(AnimCE.Companion.map().values))
    private val jspd: JScrollPane = JScrollPane(jld)
    private val jll: JList<UnitLevel> = JList<UnitLevel>()
    private val jspl: JScrollPane = JScrollPane(jll)
    private val addu: JBTN = JBTN(0, "add")
    private val remu: JBTN = JBTN(0, "rem")
    private val addf: JBTN = JBTN(0, "add")
    private val remf: JBTN = JBTN(0, "rem")
    private val addl: JBTN = JBTN(0, "add")
    private val reml: JBTN = JBTN(0, "rem")
    private val edit: JBTN = JBTN(0, "edit")
    private val vuni: JBTN = JBTN(0, "vuni")
    private val jtff: JTF = JTF()
    private val maxl: JTF = JTF()
    private val maxp: JTF = JTF()
    private val jtfl: JTF = JTF()
    private val rar: JComboBox<String> = JComboBox<String>(Interpret.RARITY)
    private val cbl: JComboBox<UnitLevel> = JComboBox<UnitLevel>()
    private val lbp: JL = JL(0, "pack")
    private val lbu: JL = JL(0, "unit")
    private val lbd: JL = JL(0, "seleanim")
    private val lbml: JL = JL(0, "maxl")
    private val lbmp: JL = JL(0, "maxp")
    private val lbf: JL = JL(1, "forms")
    private var pac: UserPack?
    private var uni: Unit? = null
    private var frm: Form? = null
    private var ul: UnitLevel? = null
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
        w += 450
        Page.Companion.set(lbu, x, y, w, 100, 300, 50)
        Page.Companion.set(jspu, x, y, w, 150, 300, 600)
        Page.Companion.set(addu, x, y, w, 800, 150, 50)
        Page.Companion.set(remu, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(vuni, x, y, w, 950, 300, 50)
        w += 300
        Page.Companion.set(lbf, x, y, w, 100, 300, 50)
        Page.Companion.set(jspf, x, y, w, 150, 300, 600)
        Page.Companion.set(jtff, x, y, w, 850, 300, 50)
        Page.Companion.set(addf, x, y, w, 800, 150, 50)
        Page.Companion.set(remf, x, y, w + dw, 800, 150, 50)
        Page.Companion.set(edit, x, y, w, 950, 300, 50)
        w += 300
        Page.Companion.set(lbd, x, y, w, 100, 300, 50)
        Page.Companion.set(jspd, x, y, w, 150, 300, 600)
        w += 350
        Page.Companion.set(lbml, x, y, w, 100, 300, 50)
        Page.Companion.set(maxl, x, y, w, 150, 300, 50)
        Page.Companion.set(lbmp, x, y, w, 200, 300, 50)
        Page.Companion.set(maxp, x, y, w, 250, 300, 50)
        Page.Companion.set(rar, x, y, w, 350, 300, 50)
        Page.Companion.set(cbl, x, y, w, 450, 300, 50)
        w += 500
        Page.Companion.set(jspl, x, y, w, 150, 300, 500)
        Page.Companion.set(jtfl, x, y, w, 700, 300, 50)
        Page.Companion.set(addl, x, y, w, 750, 150, 50)
        Page.Companion.set(reml, x, y, w + dw, 750, 150, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        jld.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (jld.getValueIsAdjusting()) return
                val edi = pac != null && pac.editable && jld.getSelectedValue() != null
                addu.setEnabled(edi)
                addf.setEnabled(edi && uni != null)
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
        jlf.list = object : ReorderListener<Form?> {
            override fun reordered(ori: Int, fin: Int) {
                val lsm: MutableList<Form> = ArrayList()
                for (sm in uni!!.forms) lsm.add(sm)
                val sm = lsm.removeAt(ori)
                lsm.add(fin, sm)
                for (i in uni!!.forms.indices) {
                    uni!!.forms[i] = lsm[i]
                    uni!!.forms[i].fid = i
                }
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
    }

    private fun `addListeners$1`() {
        jlu.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jlu.getValueIsAdjusting()) return
                changing = true
                setUnit(jlu.getSelectedValue())
                changing = false
            }
        })
        addu.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val cu = CustomUnit()
                val u = Unit(pac.getNextID<Unit, Unit>(Unit::class.java), jld.getSelectedValue(), cu)
                pac.units.add(u)
                jlu.setListData(pac.units.getList().toTypedArray())
                jlu.setSelectedValue(u, true)
                setUnit(u)
                changing = false
            }
        })
        remu.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jlu.getSelectedIndex()
                pac.units.remove(uni)
                uni!!.lv.units.remove(uni)
                jlu.setListData(pac.units.getList().toTypedArray())
                if (ind >= 0) ind--
                jlu.setSelectedIndex(ind)
                setUnit(jlu.getSelectedValue())
                changing = false
            }
        })
        maxl.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                if (changing || uni == null) return
                val lv: Int = CommonStatic.parseIntN(maxl.getText())
                if (lv > 0) uni!!.max = lv
                maxl.setText("" + uni!!.max)
            }
        })
        maxp.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                if (changing || uni == null) return
                val lv: Int = CommonStatic.parseIntN(maxp.getText())
                if (lv >= 0) uni!!.maxp = lv
                maxp.setText("" + uni!!.maxp)
            }
        })
        rar.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing) return
                uni!!.rarity = rar.getSelectedIndex()
            }
        })
        cbl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing || uni == null) return
                val sel: UnitLevel = cbl.getSelectedItem() as UnitLevel
                uni!!.lv.units.remove(uni)
                uni!!.lv = sel
                sel.units.add(uni)
                setUnit(uni)
                setLevel(ul)
            }
        })
    }

    private fun `addListeners$2`() {
        jlf.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jlf.getValueIsAdjusting()) return
                changing = true
                setForm(jlf.getSelectedValue())
                changing = false
            }
        })
        addf.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                val cu = CustomUnit()
                val ac: AnimCE = jld.getSelectedValue()
                frm = Form(uni, uni!!.forms.size, "new form", ac, cu)
                uni!!.forms = Arrays.copyOf(uni!!.forms, uni!!.forms.size + 1)
                uni!!.forms[uni!!.forms.size - 1] = frm
                setUnit(uni)
                changing = false
            }
        })
        remf.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (!Opts.conf()) return
                changing = true
                var ind: Int = jlf.getSelectedIndex()
                val fs = arrayOfNulls<Form>(uni!!.forms.size - 1)
                var x = 0
                for (i in uni!!.forms.indices) if (i != ind) fs[x++] = uni!!.forms[i]
                uni!!.forms = fs
                for (i in uni!!.forms.indices) uni!!.forms[i]!!.fid = i
                setUnit(uni)
                if (ind >= 0) ind--
                jlf.setSelectedIndex(ind)
                setForm(jlf.getSelectedValue())
                changing = false
            }
        })
        jtff.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                frm!!.name = jtff.getText().trim { it <= ' ' }
            }
        })
        edit.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                changePanel(FormEditPage(getThis(), pac, frm))
            }
        })
    }

    private fun `addListeners$3`() {
        jll.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing || jll.getValueIsAdjusting()) return
                setLevel(jll.getSelectedValue())
            }
        })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                ul = UnitLevel(pac.getNextID<UnitLevel, UnitLevel>(UnitLevel::class.java), UnitLevel.Companion.def)
                pac.unitLevels.add(ul)
                setPack(pac)
                changing = false
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changing = true
                var ind: Int = jll.getSelectedIndex()
                val ul: UnitLevel = jll.getSelectedValue()
                pac.unitLevels.remove(ul)
                setPack(pac)
                if (ind >= pac.unitLevels.size()) ind--
                jll.setSelectedIndex(ind)
                setLevel(jll.getSelectedValue())
                changing = false
            }
        })
        jtfl.addFocusListener(object : FocusAdapter() {
            override fun focusLost(fe: FocusEvent?) {
                val lvs: IntArray = CommonStatic.parseIntsN(jtfl.getText())
                for (i in lvs.indices) if (lvs[i] > 0 && (i == 0 || lvs[i] >= ul.lvs.get(i - 1))) ul.lvs.get(i) = lvs[i]
                jtfl.setText(ul.toString())
            }
        })
    }

    private fun ini() {
        add(back)
        add(jspp)
        add(jspu)
        add(jspd)
        add(addu)
        add(remu)
        add(edit)
        add(vuni)
        add(jspf)
        add(jtff)
        add(addf)
        add(remf)
        add(edit)
        add(vuni)
        add(maxl)
        add(maxp)
        add(cbl)
        add(rar)
        add(lbp)
        add(lbu)
        add(lbd)
        add(lbml)
        add(lbmp)
        add(lbf)
        add(jspl)
        add(addl)
        add(reml)
        add(jtfl)
        jlu.setCellRenderer(UnitLCR())
        jlf.setCellRenderer(AnimLCR())
        jld.setCellRenderer(AnimLCR())
        setPack(pac)
        addListeners()
        `addListeners$1`()
        `addListeners$2`()
        `addListeners$3`()
    }

    private fun setForm(f: Form?) {
        frm = f
        if (jlf.getSelectedValue() !== frm) {
            val boo = changing
            changing = true
            jlf.setSelectedValue(frm, true)
            changing = boo
        }
        val b = frm != null && pac.editable
        edit.setEnabled(frm != null && frm!!.du is CustomUnit)
        remf.setEnabled(b && frm!!.fid > 0)
        jtff.setEnabled(b)
        if (frm != null) {
            jtff.setText(f!!.name)
        } else {
            jtff.setText("")
        }
    }

    private fun setLevel(ulv: UnitLevel?) {
        ul = ulv
        if (jll.getSelectedValue() !== ul) {
            val boo = changing
            changing = true
            jll.setSelectedValue(ul, true)
            changing = boo
        }
        val b = ul != null && pac.editable
        jtfl.setEnabled(b)
        if (ul != null) jtfl.setText(ul.toString()) else jtfl.setText("")
        reml.setEnabled(b && ul.units.size == 0)
    }

    private fun setPack(pack: UserPack?) {
        pac = pack
        if (jlp.getSelectedValue() !== pack) {
            val boo = changing
            changing = true
            jlp.setSelectedValue(pac, true)
            changing = boo
        }
        val b = pac != null && pac.editable
        addu.setEnabled(b && jld.getSelectedValue() != null)
        edit.setEnabled(b)
        addl.setEnabled(b)
        vuni.setEnabled(pac != null)
        val boo = changing
        changing = true
        if (pac == null) {
            jlu.setListData(arrayOfNulls<Unit>(0))
            jll.setListData(arrayOfNulls<UnitLevel>(0))
            cbl.removeAllItems()
        } else {
            jlu.setListData(pac.units.getList().toTypedArray())
            jlu.clearSelection()
            jll.setListData(pac.unitLevels.getList().toTypedArray())
            setLevel(jll.getSelectedValue())
            val l: List<UnitLevel> = pac.unitLevels.getList()
            cbl.setModel(DefaultComboBoxModel<UnitLevel>(l.toTypedArray()))
        }
        changing = boo
        if (pac == null || !pac.units.contains(uni)) uni = null
        if (pac == null || !pac.unitLevels.contains(ul)) ul = null
        setUnit(uni)
        setLevel(ul)
    }

    private fun setUnit(unit: Unit?) {
        uni = unit
        if (jlu.getSelectedValue() !== uni) {
            val boo = changing
            changing = true
            jlu.setSelectedValue(uni, true)
            changing = boo
        }
        val b = unit != null && pac.editable
        remu.setEnabled(b)
        rar.setEnabled(b)
        cbl.setEnabled(b)
        addf.setEnabled(b && jld.getSelectedValue() != null && unit!!.forms.size < 3)
        maxl.setEditable(b)
        maxp.setEditable(b)
        val boo = changing
        changing = true
        if (unit == null) {
            jlf.setListData(arrayOfNulls<Form>(0))
            maxl.setText("")
            maxp.setText("")
            rar.setSelectedItem(null)
            cbl.setSelectedItem(null)
        } else {
            jlf.setListData(unit.forms)
            maxl.setText("" + uni!!.max)
            maxp.setText("" + uni!!.maxp)
            rar.setSelectedIndex(uni!!.rarity)
            cbl.setSelectedItem(uni!!.lv)
        }
        changing = boo
        if (frm != null && frm!!.unit !== unit) frm = null
        setForm(frm)
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        pac = pack
        ini()
    }
}