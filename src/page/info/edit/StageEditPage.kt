package page.info.editimport

import common.pack.UserProfile
import common.util.stage.Stage
import page.Page
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.MouseEvent
import java.util.*
import java.util.function.Consumer

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
class StageEditPage(p: Page?, map: MapColc, pac: UserPack) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val strt: JBTN = JBTN(0, "start")
    private val veif: JBTN = JBTN(0, "veif")
    private val cpsm: JBTN = JBTN(0, "cpsm")
    private val cpst: JBTN = JBTN(0, "cpst")
    private val ptsm: JBTN = JBTN(0, "ptsm")
    private val ptst: JBTN = JBTN(0, "ptst")
    private val rmsm: JBTN = JBTN(0, "rmsm")
    private val rmst: JBTN = JBTN(0, "rmst")
    private val recd: JBTN = JBTN(0, "replay")
    private val elim: JBTN = JBTN(0, "limit")
    private val jt: StageEditTable
    private val jspjt: JScrollPane
    private val jlsm: ReorderList<StageMap> = ReorderList<StageMap>()
    private val jspsm: JScrollPane = JScrollPane(jlsm)
    private val jlst: ReorderList<Stage> = ReorderList<Stage>()
    private val jspst: JScrollPane = JScrollPane(jlst)
    private val lpsm: JList<StageMap> = JList<StageMap>(Stage.Companion.CLIPMC.maps)
    private val jlpsm: JScrollPane = JScrollPane(lpsm)
    private val lpst: JList<Stage> = JList<Stage>()
    private val jlpst: JScrollPane = JScrollPane(lpst)
    private val adds: JBTN = JBTN(0, "add")
    private val rems: JBTN = JBTN(0, "rem")
    private val addl: JBTN = JBTN(0, "addl")
    private val reml: JBTN = JBTN(0, "reml")
    private val advs: JBTN = JBTN(0, "advance")
    private val jle: JList<Enemy> = JList<Enemy>()
    private val jspe: JScrollPane = JScrollPane(jle)
    private val info: HeadEditTable
    private val mc: MapColc
    private val pack: String
    private val efp: EnemyFindPage?
    private var changing = false
    private var stage: Stage? = null
    override fun mouseClicked(e: MouseEvent) {
        if (e.source === jt && !e.isControlDown) jt.clicked(e.point)
    }

    override fun renew() {
        info.renew()
        if (efp != null && efp.getList() != null) jle.setListData(efp.getList().toTypedArray())
    }

    @Synchronized
    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(info, x, y, 900, 50, 1400, 300)
        Page.Companion.set(addl, x, y, 900, 400, 200, 50)
        Page.Companion.set(reml, x, y, 1100, 400, 200, 50)
        Page.Companion.set(elim, x, y, 1600, 400, 200, 50)
        Page.Companion.set(recd, x, y, 1850, 400, 200, 50)
        Page.Companion.set(advs, x, y, 2100, 400, 200, 50)
        Page.Companion.set(jspjt, x, y, 900, 450, 1400, 900)
        Page.Companion.set(jspsm, x, y, 0, 50, 300, 800)
        Page.Companion.set(cpsm, x, y, 0, 850, 300, 50)
        Page.Companion.set(ptsm, x, y, 0, 900, 300, 50)
        Page.Companion.set(rmsm, x, y, 0, 950, 300, 50)
        Page.Companion.set(jlpsm, x, y, 0, 1000, 300, 300)
        Page.Companion.set(strt, x, y, 300, 0, 300, 50)
        Page.Companion.set(adds, x, y, 300, 50, 150, 50)
        Page.Companion.set(rems, x, y, 450, 50, 150, 50)
        Page.Companion.set(jspst, x, y, 300, 100, 300, 750)
        Page.Companion.set(cpst, x, y, 300, 850, 300, 50)
        Page.Companion.set(ptst, x, y, 300, 900, 300, 50)
        Page.Companion.set(rmst, x, y, 300, 950, 300, 50)
        Page.Companion.set(jlpst, x, y, 300, 1000, 300, 300)
        Page.Companion.set(veif, x, y, 600, 0, 300, 50)
        Page.Companion.set(jspe, x, y, 600, 50, 300, 1250)
        jt.setRowHeight(Page.Companion.size(x, y, 50))
    }

    private fun `addListeners$0`() {
        back.setLnr(Consumer { x: ActionEvent? -> changePanel(front) })
        strt.setLnr(Consumer { x: ActionEvent? -> changePanel(BattleSetupPage(getThis(), stage)) })
        advs.setLnr(Consumer { x: ActionEvent? -> changePanel(AdvStEditPage(getThis(), stage)) })
        recd.setLnr(Consumer { x: ActionEvent? -> changePanel(StRecdPage(getThis(), stage, true)) })
        elim.setLnr(Consumer { x: ActionEvent? -> changePanel(LimitEditPage(getThis(), stage)) })
        addl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = jt.addLine(jle.getSelectedValue())
                setData(stage)
                if (ind < 0) jt.clearSelection() else jt.addRowSelectionInterval(ind, ind)
            }
        })
        reml.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = jt.remLine()
                setData(stage)
                if (ind < 0) jt.clearSelection() else jt.addRowSelectionInterval(ind, ind)
            }
        })
        veif.setLnr(Consumer { x: ActionEvent? -> changePanel(efp) })
    }

    private fun `addListeners$1`() {
        jlsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setAA(jlsm.getSelectedValue())
                changing = false
            }
        })
        jlst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setAB(jlst.getSelectedValue())
                changing = false
            }
        })
        jlsm.list = object : ReorderListener<StageMap?> {
            override fun reordered(ori: Int, fin: Int) {
                val lsm: MutableList<StageMap> = ArrayList<StageMap>()
                for (sm in mc.maps) lsm.add(sm)
                val sm: StageMap = lsm.removeAt(ori)
                lsm.add(fin, sm)
                mc.maps = lsm.toTypedArray()
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
        jlst.list = object : ReorderListener<Stage?> {
            override fun reordered(ori: Int, fin: Int) {
                changing = false
                val l = stage!!.map.list
                val sta = l.removeAt(ori)
                l.add(fin, sta)
            }

            override fun reordering() {
                changing = true
            }
        }
        lpsm.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setBA(lpsm.getSelectedValue())
                changing = false
            }
        })
        lpst.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent) {
                if (changing || arg0.getValueIsAdjusting()) return
                changing = true
                setBB(lpst.getSelectedValue())
                changing = false
            }
        })
    }

    private fun `addListeners$2`() {
        cpsm.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = jlsm.getSelectedValue()
                val col: MapColc = Stage.Companion.CLIPMC
                val copy: StageMap = sm.copy(col)
                val n: Int = col.maps.size
                col.maps = Arrays.copyOf(col.maps, n + 1)
                col.maps.get(n) = copy
                changing = true
                lpsm.setListData(col.maps)
                lpsm.setSelectedValue(copy, true)
                setBA(copy)
                changing = false
            }
        })
        cpst.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val copy = stage!!.copy(Stage.Companion.CLIPSM)
                Stage.Companion.CLIPSM.add(copy)
                changing = true
                lpst.setListData(Vector<Stage>(Stage.Companion.CLIPSM.list))
                lpst.setSelectedValue(copy, true)
                lpsm.setSelectedIndex(0)
                setBB(copy)
                changing = false
            }
        })
        ptsm.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = lpsm.getSelectedValue()
                val ni: StageMap = sm.copy(mc)
                val n: Int = mc.maps.size
                mc.maps = Arrays.copyOf(mc.maps, n + 1)
                mc.maps.get(n) = ni
                changing = true
                jlsm.setListData(mc.maps)
                jlsm.setSelectedValue(ni, true)
                setBA(ni)
                changing = false
            }
        })
        ptst.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = jlsm.getSelectedValue()
                stage = lpst.getSelectedValue().copy(sm)
                sm.add(stage)
                changing = true
                jlst.setListData(sm.list.toTypedArray())
                jlst.setSelectedValue(stage, true)
                setBB(stage)
                changing = false
            }
        })
        rmsm.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val ind: Int = lpsm.getSelectedIndex()
                val col: MapColc = Stage.Companion.CLIPMC
                val sms: Array<StageMap?> = arrayOfNulls<StageMap>(col.maps.size - 1)
                for (i in 0 until ind) sms[i] = col.maps.get(i)
                for (i in ind until col.maps.size - 1) sms[i] = col.maps.get(i + 1)
                col.maps = sms
                changing = true
                lpsm.setListData(sms)
                lpsm.setSelectedIndex(ind - 1)
                setBA(lpsm.getSelectedValue())
                changing = false
            }
        })
        rmst.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = lpsm.getSelectedValue()
                val st: Stage = lpst.getSelectedValue()
                val ind: Int = lpst.getSelectedIndex()
                sm.list.remove(st)
                changing = true
                lpst.setListData(Vector<Stage>(sm.list))
                lpst.setSelectedIndex(ind - 1)
                setBB(lpst.getSelectedValue())
                changing = false
            }
        })
        adds.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                val sm: StageMap = jlsm.getSelectedValue()
                stage = Stage(sm)
                sm.add(stage)
                changing = true
                jlst.setListData(sm.list.toTypedArray())
                jlst.setSelectedValue(stage, true)
                setAB(stage)
                changing = false
            }
        })
        rems.setLnr(Consumer { x: ActionEvent? ->
            if (!Opts.conf()) return@setLnr
            val sm: StageMap = jlsm.getSelectedValue()
            var ind: Int = jlst.getSelectedIndex() - 1
            sm.list.remove(stage)
            changing = true
            jlst.setListData(Vector<Stage>(sm.list))
            if (ind < 0) ind = -1
            if (ind < sm.list.size) jlst.setSelectedIndex(ind) else jlst.setSelectedIndex(sm.list.size - 1)
            setAB(jlst.getSelectedValue())
            changing = false
        })
    }

    private fun checkPtsm() {
        val sm: StageMap = lpsm.getSelectedValue()
        if (sm == null) {
            ptsm.setEnabled(false)
            return
        }
        var b = true
        for (st in sm.list) b = b and st.isSuitable(pack)
        ptsm.setEnabled(b)
    }

    private fun checkPtst() {
        val st: Stage = lpst.getSelectedValue()
        val sm: StageMap = jlsm.getSelectedValue()
        if (st == null || sm == null) ptst.setEnabled(false) else ptst.setEnabled(st.isSuitable(pack))
        rmst.setEnabled(st != null)
    }

    private fun ini() {
        add(back)
        add(veif)
        add(adds)
        add(rems)
        add(jspjt)
        add(info)
        add(strt)
        add(jspsm)
        add(jspst)
        add(addl)
        add(reml)
        add(jspe)
        add(cpsm)
        add(cpst)
        add(ptsm)
        add(ptst)
        add(rmsm)
        add(rmst)
        add(jlpsm)
        add(jlpst)
        add(recd)
        add(advs)
        add(elim)
        setAA(null)
        setBA(null)
        jle.setCellRenderer(AnimLCR())
        `addListeners$0`()
        `addListeners$1`()
        `addListeners$2`()
    }

    private fun setAA(sm: StageMap?) {
        if (sm == null) {
            jlst.setListData(arrayOfNulls<Stage>(0))
            setAB(null)
            cpsm.setEnabled(false)
            ptst.setEnabled(false)
            adds.setEnabled(false)
            return
        }
        jlst.setListData(Vector<Stage>(sm.list))
        if (sm.list.size == 0) {
            jlst.clearSelection()
            cpsm.setEnabled(false)
            adds.setEnabled(true)
            checkPtst()
            setAB(null)
            return
        }
        jlst.setSelectedIndex(0)
        cpsm.setEnabled(true)
        adds.setEnabled(true)
        checkPtst()
        setAB(sm.list.get(0))
    }

    private fun setAB(st: Stage?) {
        if (st == null) {
            setData(lpst.getSelectedValue())
            cpst.setEnabled(false)
            rems.setEnabled(false)
            return
        }
        cpst.setEnabled(true)
        rems.setEnabled(true)
        lpst.clearSelection()
        checkPtst()
        setData(st)
    }

    private fun setBA(sm: StageMap?) {
        if (sm == null) {
            lpst.setListData(arrayOfNulls<Stage>(0))
            ptsm.setEnabled(false)
            rmsm.setEnabled(false)
            setBB(null)
            return
        }
        lpst.setListData(Vector<Stage>(sm.list))
        rmsm.setEnabled(sm !== Stage.Companion.CLIPSM)
        if (sm.list.size == 0) {
            lpst.clearSelection()
            ptsm.setEnabled(false)
            setBB(null)
            return
        }
        lpst.setSelectedIndex(0)
        setBB(sm.list.get(0))
        checkPtsm()
    }

    private fun setBB(st: Stage?) {
        if (st == null) {
            setData(jlst.getSelectedValue())
            ptst.setEnabled(false)
            rmst.setEnabled(false)
            return
        }
        cpst.setEnabled(false)
        checkPtst()
        jlst.clearSelection()
        setData(st)
    }

    private fun setData(st: Stage?) {
        stage = st
        info.setData(st)
        jt.setData(st)
        strt.setEnabled(st != null)
        recd.setEnabled(st != null)
        advs.setEnabled(st != null)
        elim.setEnabled(st != null)
        jspjt.scrollRectToVisible(Rectangle(0, 0, 1, 1))
        resized()
    }

    companion object {
        private const val serialVersionUID = 1L
        fun redefine() {
            StageEditTable.Companion.redefine()
            LimitTable.Companion.redefine()
            SCGroupEditTable.Companion.redefine()
        }
    }

    init {
        mc = map
        pack = pac.desc.id
        jt = StageEditTable(this, pac)
        jspjt = JScrollPane(jt)
        info = HeadEditTable(this, pac)
        jlsm.setListData(mc.maps)
        jle.setListData(UserProfile.Companion.getAll<Enemy>(pack, Enemy::class.java).toTypedArray())
        efp = EnemyFindPage(getThis(), pack)
        ini()
    }
}