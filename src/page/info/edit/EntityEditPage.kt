package page.info.editimport

import common.pack.PackData
import common.pack.UserProfile
import common.util.Data
import common.util.pack.Background
import common.util.unit.Form
import common.util.unit.Unit
import page.Page
import java.awt.event.ActionEvent
import java.util.*
import java.util.function.Consumer

com.google.api.client.json.jackson2.JacksonFactoryimport com.google.api.services.drive.DriveScopesimport com.google.api.client.util.store.FileDataStoreFactoryimport com.google.api.client.http.HttpTransportimport com.google.api.services.drive.Driveimport kotlin.Throwsimport java.io.IOExceptionimport io.drive.DriveUtilimport java.io.FileNotFoundExceptionimport java.io.FileInputStreamimport com.google.api.client.googleapis.auth.oauth2.GoogleClientSecretsimport java.io.InputStreamReaderimport com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlowimport com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledAppimport com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiverimport com.google.api.client.googleapis.javanet.GoogleNetHttpTransportimport kotlin.jvm.JvmStaticimport io.drive.DrvieInitimport com.google.api.client.http.javanet.NetHttpTransportimport com.google.api.services.drive.model.FileListimport java.io.BufferedInputStreamimport java.io.FileOutputStreamimport com.google.api.client.googleapis.media.MediaHttpDownloaderimport io.WebFileIOimport io.BCJSONimport page.LoadPageimport org.json.JSONObjectimport org.json.JSONArrayimport main.MainBCUimport main.Optsimport common.CommonStaticimport java.util.TreeMapimport java.util.Arraysimport java.io.BufferedReaderimport io.BCMusicimport common.util.stage.Musicimport io.BCPlayerimport java.util.HashMapimport javax.sound.sampled.Clipimport java.io.ByteArrayInputStreamimport javax.sound.sampled.AudioInputStreamimport javax.sound.sampled.AudioSystemimport javax.sound.sampled.DataLineimport javax.sound.sampled.FloatControlimport javax.sound.sampled.LineEventimport com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListenerimport com.google.api.client.googleapis.media.MediaHttpDownloader.DownloadStateimport common.io.DataIOimport io.BCUReaderimport common.io.InStreamimport com.google.gson.JsonElementimport common.io.json.JsonDecoderimport com.google.gson.JsonObjectimport page.MainFrameimport page.view.ViewBox.Confimport page.MainLocaleimport page.battle.BattleInfoPageimport page.support.Exporterimport page.support.Importerimport common.pack.Context.ErrTypeimport common.util.stage.MapColcimport common.util.stage.MapColc.DefMapColcimport common.util.lang.MultiLangContimport common.util.stage.StageMapimport common.util.unit.Enemyimport io.BCUWriterimport java.text.SimpleDateFormatimport java.io.PrintStreamimport common.io.OutStreamimport common.battle.BasisSetimport res.AnimatedGifEncoderimport java.awt.image.BufferedImageimport javax.imageio.ImageIOimport java.security.MessageDigestimport java.security.NoSuchAlgorithmExceptionimport common.io.json.JsonEncoderimport java.io.FileWriterimport com.google.api.client.http.GenericUrlimport org.apache.http.impl .client.CloseableHttpClientimport org.apache.http.impl .client.HttpClientsimport org.apache.http.client.methods.HttpPostimport org.apache.http.entity.mime.content.FileBodyimport org.apache.http.entity.mime.MultipartEntityBuilderimport org.apache.http.entity.mime.HttpMultipartModeimport org.apache.http.HttpEntityimport org.apache.http.util.EntityUtilsimport com.google.api.client.http.HttpRequestInitializerimport com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandlerimport com.google.api.client.util.ExponentialBackOffimport com.google.api.client.http.HttpBackOffIOExceptionHandlerimport res.NeuQuantimport res.LZWEncoderimport java.io.BufferedOutputStreamimport java.awt.Graphics2Dimport java.awt.image.DataBufferByteimport common.system.fake.FakeImageimport utilpc.awt.FIBIimport jogl.util.AmbImageimport common.system.files.VFileimport jogl.util.GLImageimport com.jogamp.opengl.util.texture.TextureDataimport common.system.Pimport com.jogamp.opengl.util.texture.TextureIOimport jogl.GLStaticimport com.jogamp.opengl.util.texture.awt.AWTTextureIOimport java.awt.AlphaCompositeimport common.system.fake.FakeImage.Markerimport jogl.util.GLGraphicsimport com.jogamp.opengl.GL2import jogl.util.GeoAutoimport com.jogamp.opengl.GL2ES3import com.jogamp.opengl.GLimport common.system.fake.FakeGraphicsimport common.system.fake.FakeTransformimport jogl.util.ResManagerimport jogl.util.GLGraphics.GeomGimport jogl.util.GLGraphics.GLCimport jogl.util.GLGraphics.GLTimport com.jogamp.opengl.GL2ES2import com.jogamp.opengl.util.glsl.ShaderCodeimport com.jogamp.opengl.util.glsl.ShaderProgramimport com.jogamp.opengl.GLExceptionimport jogl.StdGLCimport jogl.Tempimport common.util.anim.AnimUimport common.util.anim.EAnimUimport jogl.util.GLIBimport javax.swing.JFrameimport common.util.anim.AnimCEimport common.util.anim.AnimU.UTypeimport com.jogamp.opengl.util.FPSAnimatorimport com.jogamp.opengl.GLEventListenerimport com.jogamp.opengl.GLAutoDrawableimport page.awt.BBBuilderimport page.battle.BattleBox.OuterBoximport common.battle.SBCtrlimport page.battle.BattleBoximport jogl.GLBattleBoximport common.battle.BattleFieldimport page.anim.IconBoximport jogl.GLIconBoximport jogl.GLBBRecdimport page.awt.RecdThreadimport page.view.ViewBoximport jogl.GLViewBoximport page.view.ViewBox.Controllerimport java.awt.AWTExceptionimport page.battle.BBRecdimport jogl.GLRecorderimport com.jogamp.opengl.GLProfileimport com.jogamp.opengl.GLCapabilitiesimport page.anim.IconBox.IBCtrlimport page.anim.IconBox.IBConfimport page.view.ViewBox.VBExporterimport jogl.GLRecdBImgimport page.JTGimport jogl.GLCstdimport jogl.GLVBExporterimport common.util.anim.EAnimIimport page.RetFuncimport page.battle.BattleBox.BBPainterimport page.battle.BBCtrlimport javax.swing.JOptionPaneimport kotlin.jvm.Strictfpimport main.Invimport javax.swing.SwingUtilitiesimport java.lang.InterruptedExceptionimport utilpc.UtilPC.PCItrimport utilpc.awt.PCIBimport jogl.GLBBBimport page.awt.AWTBBBimport utilpc.Themeimport page.MainPageimport common.io.assets.AssetLoaderimport common.pack.Source.Workspaceimport common.io.PackLoader.ZipDesc.FileDescimport common.io.assets.Adminimport page.awt.BattleBoxDefimport page.awt.IconBoxDefimport page.awt.BBRecdAWTimport page.awt.ViewBoxDefimport org.jcodec.api.awt.AWTSequenceEncoderimport page.awt.RecdThread.PNGThreadimport page.awt.RecdThread.MP4Threadimport page.awt.RecdThread.GIFThreadimport java.awt.GradientPaintimport utilpc.awt.FG2Dimport page.anim.TreeContimport javax.swing.JTreeimport javax.swing.event.TreeExpansionListenerimport common.util.anim.MaModelimport javax.swing.tree.DefaultMutableTreeNodeimport javax.swing.event.TreeExpansionEventimport java.util.function.IntPredicateimport javax.swing.tree.DefaultTreeModelimport common.util.anim.EAnimDimport page.anim.AnimBoximport utilpc.PPimport common.CommonStatic.BCAuxAssetsimport common.CommonStatic.EditLinkimport page.JBTNimport page.anim.DIYViewPageimport page.anim.ImgCutEditPageimport page.anim.MaModelEditPageimport page.anim.MaAnimEditPageimport page.anim.EditHeadimport java.awt.event.ActionListenerimport page.anim.AbEditPageimport common.util.anim.EAnimSimport page.anim.ModelBoximport common.util.anim.ImgCutimport page.view.AbViewPageimport javax.swing.JListimport javax.swing.JScrollPaneimport javax.swing.JComboBoximport utilpc.UtilPCimport javax.swing.event.ListSelectionListenerimport javax.swing.event.ListSelectionEventimport common.system.VImgimport page.support.AnimLCRimport page.support.AnimTableimport common.util.anim.MaAnimimport java.util.EventObjectimport javax.swing.text.JTextComponentimport page.anim.PartEditTableimport javax.swing.ListSelectionModelimport page.support.AnimTableTHimport page.JTFimport utilpc.ReColorimport page.anim.ImgCutEditTableimport page.anim.SpriteBoximport page.anim.SpriteEditPageimport java.awt.event.FocusAdapterimport java.awt.event.FocusEventimport common.pack.PackData.UserPackimport utilpc.Algorithm.SRResultimport page.anim.MaAnimEditTableimport javax.swing.JSliderimport java.awt.event.MouseWheelEventimport common.util.anim.EPartimport javax.swing.event.ChangeEventimport page.anim.AdvAnimEditPageimport javax.swing.BorderFactoryimport page.JLimport javax.swing.ImageIconimport page.anim.MMTreeimport javax.swing.event.TreeSelectionListenerimport javax.swing.event.TreeSelectionEventimport page.support.AbJTableimport page.anim.MaModelEditTableimport page.info.edit.ProcTableimport page.info.edit.ProcTable.AtkProcTableimport page.info.edit.SwingEditorimport page.info.edit.ProcTable.MainProcTableimport page.support.ListJtfPolicyimport page.info.edit.SwingEditor.SwingEGimport common.util.Data.Procimport java.lang.Runnableimport javax.swing.JComponentimport page.info.edit.LimitTableimport page.pack.CharaGroupPageimport page.pack.LvRestrictPageimport javax.swing.SwingConstantsimport common.util.lang.Editors.EditorGroupimport common.util.lang.Editors.EdiFieldimport common.util.lang.Editorsimport common.util.lang.ProcLangimport page.info.edit.EntityEditPageimport common.util.lang.Editors.EditorSupplierimport common.util.lang.Editors.EditControlimport page.info.edit.SwingEditor.IntEditorimport page.info.edit.SwingEditor.BoolEditorimport page.info.edit.SwingEditor.IdEditorimport page.SupPageimport common.util.unit.AbEnemyimport common.pack.IndexContainer.Indexableimport common.pack.Context.SupExcimport common.battle.data .AtkDataModelimport utilpc.Interpretimport common.battle.data .CustomEntityimport page.info.filter.UnitEditBoximport common.battle.data .CustomUnitimport common.util.stage.SCGroupimport page.info.edit.SCGroupEditTableimport common.util.stage.SCDefimport page.info.filter.EnemyEditBoximport common.battle.data .CustomEnemyimport page.info.StageFilterPageimport page.view.BGViewPageimport page.view.CastleViewPageimport page.view.MusicPageimport common.util.stage.CastleImgimport common.util.stage.CastleListimport java.text.DecimalFormatimport common.util.stage.Recdimport common.util.stage.MapColc.PackMapColcimport page.info.edit.StageEditTableimport page.support.ReorderListimport page.info.edit.HeadEditTableimport page.info.filter.EnemyFindPageimport page.battle.BattleSetupPageimport page.info.edit.AdvStEditPageimport page.battle.StRecdPageimport page.info.edit.LimitEditPageimport page.support.ReorderListenerimport common.util.pack.Soulimport page.info.edit.AtkEditTableimport page.info.filter.UnitFindPageimport common.battle.Basisimport common.util.Data.Proc.IMUimport javax.swing.DefaultComboBoxModelimport common.util.Animableimport common.util.pack.Soul.SoulTypeimport page.view.UnitViewPageimport page.view.EnemyViewPageimport page.info.edit.SwingEditor.EditCtrlimport page.support.Reorderableimport page.info.EnemyInfoPageimport common.util.unit.EneRandimport page.pack.EREditPageimport page.support.InTableTHimport page.support.EnemyTCRimport javax.swing.DefaultListCellRendererimport page.info.filter.UnitListTableimport page.info.filter.UnitFilterBoximport page.info.filter.EnemyListTableimport page.info.filter.EnemyFilterBoximport page.info.filter.UFBButtonimport page.info.filter.UFBListimport common.battle.data .MaskUnitimport javax.swing.AbstractButtonimport page.support.SortTableimport page.info.UnitInfoPageimport page.support.UnitTCRimport page.info.filter.EFBButtonimport page.info.filter.EFBListimport common.util.stage.LvRestrictimport common.util.stage.CharaGroupimport page.info.StageTableimport page.info.TreaTableimport javax.swing.JPanelimport page.info.UnitInfoTableimport page.basis.BasisPageimport kotlin.jvm.JvmOverloadsimport page.info.EnemyInfoTableimport common.util.stage.RandStageimport page.info.StagePageimport page.info.StageRandPageimport common.util.unit.EFormimport page.pack.EREditTableimport common.util.EREntimport common.pack.FixIndexListimport page.support.UnitLCRimport page.pack.RecdPackPageimport page.pack.CastleEditPageimport page.pack.BGEditPageimport page.pack.CGLREditPageimport common.pack.Source.ZipSourceimport page.info.edit.EnemyEditPageimport page.info.edit.StageEditPageimport page.info.StageViewPageimport page.pack.UnitManagePageimport page.pack.MusicEditPageimport page.battle.AbRecdPageimport common.system.files.VFileRootimport java.awt.Desktopimport common.pack.PackDataimport common.util.unit.UnitLevelimport page.info.edit.FormEditPageimport common.util.anim.AnimIimport common.util.anim.AnimI.AnimTypeimport common.util.anim.AnimDimport common.battle.data .Orbimport page.basis.LineUpBoximport page.basis.LubContimport common.battle.BasisLUimport page.basis.ComboListTableimport page.basis.ComboListimport page.basis.NyCasBoximport page.basis.UnitFLUPageimport common.util.unit.Comboimport page.basis.LevelEditPageimport common.util.pack.NyCastleimport common.battle.LineUpimport common.system.SymCoordimport java.util.TreeSetimport page.basis.OrbBoximport javax.swing.table.DefaultTableCellRendererimport javax.swing.JTableimport common.CommonStatic.BattleConstimport common.battle.StageBasisimport common.util.ImgCoreimport common.battle.attack.ContAbimport common.battle.entity.EAnimContimport common.battle.entity.WaprContimport page.battle.RecdManagePageimport page.battle.ComingTableimport common.util.stage.EStageimport page.battle.EntityTableimport common.battle.data .MaskEnemyimport common.battle.SBRplyimport common.battle.entity.AbEntityimport page.battle.RecdSavePageimport page.LocCompimport page.LocSubCompimport javax.swing.table.TableModelimport page.support.TModelimport javax.swing.event.TableModelListenerimport javax.swing.table.DefaultTableColumnModelimport javax.swing.JFileChooserimport javax.swing.filechooser.FileNameExtensionFilterimport javax.swing.TransferHandlerimport java.awt.datatransfer.Transferableimport java.awt.datatransfer.DataFlavorimport javax.swing.DropModeimport javax.swing.TransferHandler.TransferSupportimport java.awt.dnd.DragSourceimport java.awt.datatransfer.UnsupportedFlavorExceptionimport common.system.Copableimport page.support.AnimTransferimport javax.swing.DefaultListModelimport page.support.InListTHimport java.awt.FocusTraversalPolicyimport javax.swing.JTextFieldimport page.CustomCompimport javax.swing.JToggleButtonimport javax.swing.JButtonimport javax.swing.ToolTipManagerimport javax.swing.JRootPaneimport javax.swing.JProgressBarimport page.ConfigPageimport page.view.EffectViewPageimport page.pack.PackEditPageimport page.pack.ResourcePageimport javax.swing.WindowConstantsimport java.awt.event.AWTEventListenerimport java.awt.AWTEventimport java.awt.event.ComponentAdapterimport java.awt.event.ComponentEventimport java.util.ConcurrentModificationExceptionimport javax.swing.plaf.FontUIResourceimport java.util.Enumerationimport javax.swing.UIManagerimport common.CommonStatic.FakeKeyimport page.LocSubComp.LocBinderimport page.LSCPopimport java.awt.BorderLayoutimport java.awt.GridLayoutimport javax.swing.JTextPaneimport page.TTTimport java.util.ResourceBundleimport java.util.MissingResourceExceptionimport java.util.Localeimport common.io.json.Test.JsonTest_2import common.pack.PackData.PackDescimport common.io.PackLoaderimport common.io.PackLoader.Preloadimport common.io.PackLoader.ZipDescimport common.io.json.Testimport common.io.json.JsonClassimport common.io.json.JsonFieldimport common.io.json.JsonField.GenTypeimport common.io.json.Test.JsonTest_0.JsonDimport common.io.json.JsonClass.RTypeimport java.util.HashSetimport common.io.json.JsonDecoder.OnInjectedimport common.io.json.JsonField.IOTypeimport common.io.json.JsonExceptionimport common.io.json.JsonClass.NoTagimport common.io.json.JsonField.SerTypeimport common.io.json.JsonClass.WTypeimport kotlin.reflect.KClassimport com.google.gson.JsonArrayimport common.io.assets.Admin.StaticPermittedimport common.io.json.JsonClass.JCGenericimport common.io.json.JsonClass.JCGetterimport com.google.gson.JsonPrimitiveimport com.google.gson.JsonNullimport common.io.json.JsonClass.JCIdentifierimport java.lang.ClassNotFoundExceptionimport common.io.assets.AssetLoader.AssetHeaderimport common.io.assets.AssetLoader.AssetHeader.AssetEntryimport common.io.InStreamDefimport common.io.BCUExceptionimport java.io.UnsupportedEncodingExceptionimport common.io.OutStreamDefimport javax.crypto.Cipherimport javax.crypto.spec.IvParameterSpecimport javax.crypto.spec.SecretKeySpecimport common.io.PackLoader.FileSaverimport common.system.files.FDByteimport common.io.json.JsonClass.JCConstructorimport common.io.PackLoader.FileLoader.FLStreamimport common.io.PackLoader.PatchFileimport java.lang.NullPointerExceptionimport java.lang.IndexOutOfBoundsExceptionimport common.io.MultiStreamimport java.io.RandomAccessFileimport common.io.MultiStream.TrueStreamimport java.lang.RuntimeExceptionimport common.pack.Source.ResourceLocationimport common.pack.Source.AnimLoaderimport common.pack.Source.SourceAnimLoaderimport common.util.anim.AnimCIimport common.system.files.FDFileimport common.pack.IndexContainerimport common.battle.data .PCoinimport common.util.pack.EffAnimimport common.battle.data .DataEnemyimport common.util.stage.Limit.DefLimitimport common.pack.IndexContainer.Reductorimport common.pack.FixIndexList.FixIndexMapimport common.pack.VerFixer.IdFixerimport common.pack.IndexContainer.IndexContimport common.pack.IndexContainer.ContGetterimport common.util.stage.CastleList.PackCasListimport common.util.Data.Proc.THEMEimport common.CommonStatic.ImgReaderimport common.pack.VerFixerimport common.pack.VerFixer.VerFixerExceptionimport java.lang.NumberFormatExceptionimport common.pack.Source.SourceAnimSaverimport common.pack.VerFixer.EnemyFixerimport common.pack.VerFixer.PackFixerimport common.pack.PackData.DefPackimport java.util.function.BiConsumerimport common.util.BattleStaticimport common.util.anim.AnimU.ImageKeeperimport common.util.anim.AnimCE.AnimCELoaderimport common.util.anim.AnimCI.AnimCIKeeperimport common.util.anim.AnimUD.DefImgLoaderimport common.util.BattleObjimport common.util.Data.Proc.ProcItemimport common.util.lang.ProcLang.ItemLangimport common.util.lang.LocaleCenter.Displayableimport common.util.lang.Editors.DispItemimport common.util.lang.LocaleCenter.ObjBinderimport common.util.lang.LocaleCenter.ObjBinder.BinderFuncimport common.util.Data.Proc.PROBimport org.jcodec.common.tools.MathUtilimport common.util.Data.Proc.PTimport common.util.Data.Proc.PTDimport common.util.Data.Proc.PMimport common.util.Data.Proc.WAVEimport common.util.Data.Proc.WEAKimport common.util.Data.Proc.STRONGimport common.util.Data.Proc.BURROWimport common.util.Data.Proc.REVIVEimport common.util.Data.Proc.SUMMONimport common.util.Data.Proc.MOVEWAVEimport common.util.Data.Proc.POISONimport common.util.Data.Proc.CRITIimport common.util.Data.Proc.VOLCimport common.util.Data.Proc.ARMORimport common.util.Data.Proc.SPEEDimport java.util.LinkedHashMapimport common.util.lang.LocaleCenter.DisplayItemimport common.util.lang.ProcLang.ProcLangStoreimport common.util.lang.Formatter.IntExpimport common.util.lang.Formatter.RefObjimport common.util.lang.Formatter.BoolExpimport common.util.lang.Formatter.BoolElemimport common.util.lang.Formatter.IElemimport common.util.lang.Formatter.Contimport common.util.lang.Formatter.Elemimport common.util.lang.Formatter.RefElemimport common.util.lang.Formatter.RefFieldimport common.util.lang.Formatter.RefFuncimport common.util.lang.Formatter.TextRefimport common.util.lang.Formatter.CodeBlockimport common.util.lang.Formatter.TextPlainimport common.util.unit.Unit.UnitInfoimport common.util.lang.MultiLangCont.MultiLangStaticsimport common.util.pack.EffAnim.EffTypeimport common.util.pack.EffAnim.ArmorEffimport common.util.pack.EffAnim.BarEneEffimport common.util.pack.EffAnim.BarrierEffimport common.util.pack.EffAnim.DefEffimport common.util.pack.EffAnim.WarpEffimport common.util.pack.EffAnim.ZombieEffimport common.util.pack.EffAnim.KBEffimport common.util.pack.EffAnim.SniperEffimport common.util.pack.EffAnim.VolcEffimport common.util.pack.EffAnim.SpeedEffimport common.util.pack.EffAnim.WeakUpEffimport common.util.pack.EffAnim.EffAnimStoreimport common.util.pack.NyCastle.NyTypeimport common.util.pack.WaveAnimimport common.util.pack.WaveAnim.WaveTypeimport common.util.pack.Background.BGWvTypeimport common.util.unit.Form.FormJsonimport common.system.BasedCopableimport common.util.anim.AnimUDimport common.battle.data .DataUnitimport common.battle.entity.EUnitimport common.battle.entity.EEnemyimport common.util.EntRandimport common.util.stage.Recd.Waitimport java.lang.CloneNotSupportedExceptionimport common.util.stage.StageMap.StageMapInfoimport common.util.stage.Stage.StageInfoimport common.util.stage.Limit.PackLimitimport common.util.stage.MapColc.ClipMapColcimport common.util.stage.CastleList.DefCasListimport common.util.stage.MapColc.StItrimport common.util.Data.Proc.IntType.BitCountimport common.util.CopRandimport common.util.LockGLimport java.lang.IllegalAccessExceptionimport common.battle.data .MaskAtkimport common.battle.data .DefaultDataimport common.battle.data .DataAtkimport common.battle.data .MaskEntityimport common.battle.data .DataEntityimport common.battle.attack.AtkModelAbimport common.battle.attack.AttackAbimport common.battle.attack.AttackSimpleimport common.battle.attack.AttackWaveimport common.battle.entity.Cannonimport common.battle.attack.AttackVolcanoimport common.battle.attack.ContWaveAbimport common.battle.attack.ContWaveDefimport common.battle.attack.AtkModelEntityimport common.battle.entity.EntContimport common.battle.attack.ContMoveimport common.battle.attack.ContVolcanoimport common.battle.attack.ContWaveCanonimport common.battle.attack.AtkModelEnemyimport common.battle.attack.AtkModelUnitimport common.battle.attack.AttackCanonimport common.battle.entity.EUnit.OrbHandlerimport common.battle.entity.Entity.AnimManagerimport common.battle.entity.Entity.AtkManagerimport common.battle.entity.Entity.ZombXimport common.battle.entity.Entity.KBManagerimport common.battle.entity.Entity.PoisonTokenimport common.battle.entity.Entity.WeakTokenimport common.battle.Treasureimport common.battle.MirrorSetimport common.battle.Releaseimport common.battle.ELineUpimport common.battle.entity.Sniperimport common.battle.entity.ECastleimport java.util.Dequeimport common.CommonStatic.Itfimport java.lang.Characterimport common.CommonStatic.ImgWriterimport utilpc.awt.FTATimport utilpc.awt.Blenderimport java.awt.RenderingHintsimport utilpc.awt.BIBuilderimport java.awt.CompositeContextimport java.awt.image.Rasterimport java.awt.image.WritableRasterimport utilpc.ColorSetimport utilpc.OggTimeReaderimport utilpc.UtilPC.PCItr.MusicReaderimport utilpc.UtilPC.PCItr.PCALimport javax.swing.UIManager.LookAndFeelInfoimport java.lang.InstantiationExceptionimport javax.swing.UnsupportedLookAndFeelExceptionimport utilpc.Algorithm.ColorShiftimport utilpc.Algorithm.StackRect
abstract class EntityEditPage(p: Page?, pac: String, e: CustomEntity, edit: Boolean, isEnemy: Boolean) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val lhp: JL = JL(1, "HP")
    private val lhb: JL = JL(1, "HB")
    private val lsp: JL = JL(1, "speed")
    private val lra: JL = JL(1, "range")
    private val lwd: JL = JL(1, "width")
    private val lsh: JL = JL(1, "shield")
    private val ltb: JL = JL(1, "TBA")
    private val lbs: JL = JL(1, "tbase")
    private val ltp: JL = JL(1, "type")
    private val lct: JL = JL(1, "count")
    private val fhp: JTF = JTF()
    private val fhb: JTF = JTF()
    private val fsp: JTF = JTF()
    private val fra: JTF = JTF()
    private val fwd: JTF = JTF()
    private val fsh: JTF = JTF()
    private val ftb: JTF = JTF()
    private val fbs: JTF = JTF()
    private val ftp: JTF = JTF()
    private val fct: JTF = JTF()
    private val jli: ReorderList<String> = ReorderList<String>()
    private val jspi: JScrollPane = JScrollPane(jli)
    private val add: JBTN = JBTN(0, "add")
    private val rem: JBTN = JBTN(0, "rem")
    private val copy: JBTN = JBTN(0, "copy")
    private val link: JBTN = JBTN(0, "link")
    private val comm: JTG = JTG(1, "common")
    private val atkn: JTF = JTF()
    private val lpst: JL = JL(1, "postaa")
    private val vpst: JL = JL()
    private val litv: JL = JL(1, "atkf")
    private val lrev: JL = JL(1, "post-HB")
    private val lres: JL = JL(1, "post-death")
    private val vrev: JL = JL()
    private val vres: JL = JL()
    private val vitv: JL = JL()
    private val jcba: JComboBox<AnimCE> = JComboBox<AnimCE>()
    private val jcbs: JComboBox<Soul> = JComboBox<Soul>()
    private val ljp: ListJtfPolicy = ListJtfPolicy()
    private val aet: AtkEditTable
    private val mpt: MainProcTable
    private val jspm: JScrollPane
    private val ce: CustomEntity
    private val pack: String
    private var changing = false
    private var efp: EnemyFindPage? = null
    private var ufp: UnitFindPage? = null
    private var sup: SupPage<out Indexable<*, *>>? = null
    private var supEditor: IdEditor<*>? = null
    protected val editable: Boolean
    protected val bas: Basis = BasisSet.Companion.current()
    override fun callBack(o: Any?) {
        if (o is IntArray) {
            val vals = o
            if (vals.size == 3) {
                ce.type = vals[0]
                ce.abi = vals[1]
                for (i in Interpret.ABIIND.indices) {
                    val id: Int = Interpret.ABIIND.get(i) - 100
                    if (vals[2] and (1 shl id - Interpret.IMUSFT) > 0) (ce.getProc().getArr(id) as IMU).mult = 100 else (ce.getProc().getArr(id) as IMU).mult = 0
                }
                ce.loop = if (ce.abi and Data.Companion.AB_GLASS > 0) 1 else -1
            }
        }
        setData(ce)
    }

    fun getBGSup(): SupPage<Background> {
        return BGViewPage(this, pack)
    }

    fun getEnemySup(): SupPage<AbEnemy> {
        return EnemyFindPage(this, pack) // FIXME
    }

    fun getUnitSup(): SupPage<Unit> {
        return UnitFindPage(this, pack)
    }

    fun <T : Indexable<*, T>?> putWait(editor: IdEditor<T>?, sup: SupPage<T>) {
        supEditor = editor
        this.sup = sup
    }

    protected open fun getAtk(): Double {
        return 1
    }

    protected open fun getDef(): Double {
        return 1
    }

    protected abstract fun getInput(jtf: JTF, v: Int)
    protected open fun ini() {
        set(lhp)
        set(lhb)
        set(lsp)
        set(lwd)
        set(lsh)
        set(lra)
        set(ltb)
        set(lbs)
        set(ltp)
        set(lct)
        set(fhp)
        set(fhb)
        set(fsp)
        set(fsh)
        set(fwd)
        set(fra)
        set(ftb)
        set(fbs)
        set(ftp)
        set(fct)
        ljp.end()
        add(jspi)
        add(aet)
        add(jspm)
        add(add)
        add(rem)
        add(copy)
        add(link)
        add(back)
        set(atkn)
        set(lpst)
        set(vpst)
        set(litv)
        set(vitv)
        set(lrev)
        set(lres)
        set(vrev)
        set(vres)
        add(comm)
        add(jcbs)
        val vec: Vector<Soul?> = Vector<Soul?>()
        vec.add(null)
        vec.addAll(UserProfile.Companion.getAll<Soul>(pack, Soul::class.java))
        jcbs.setModel(DefaultComboBoxModel<Soul>(vec))
        if (editable) {
            add(jcba)
            val vda: Vector<AnimCE> = Vector<AnimCE>()
            val ac: AnimCE = ce.getPack().anim as AnimCE
            if (!ac.inPool()) vda.add(ac)
            vda.addAll(AnimCE.Companion.map().values)
            jcba.setModel(DefaultComboBoxModel<AnimCE>(vda))
        }
        focusTraversalPolicy = ljp
        isFocusCycleRoot = true
        addListeners()
        atkn.setToolTipText("<html>use name \"revenge\" for attack during HB animation<br>"
                + "use name \"resurrection\" for attack during death animation</html>")
        ftp.setToolTipText(
                "<html>" + "+1 for normal attack<br>" + "+2 to attack kb<br>" + "+4 to attack underground<br>"
                        + "+8 to attack corpse<br>" + "+16 to attack soul<br>" + "+32 to attack ghost</html>")
        add.setEnabled(editable)
        rem.setEnabled(editable)
        copy.setEnabled(editable)
        link.setEnabled(editable)
        atkn.setEnabled(editable)
        comm.setEnabled(editable)
        jcbs.setEnabled(editable)
    }

    override fun renew() {
        if (efp != null && efp.getSelected() != null && Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
            val e: Enemy = efp.getSelected()
            ce.importData(e.de)
            setData(ce)
        }
        if (ufp != null && ufp.getForm() != null && Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
            val f: Form = ufp.getForm()
            ce.importData(f.du)
            setData(ce)
        }
        if (sup != null && supEditor != null) {
            val obj: Indexable<*, *> = sup.getSelected()
            supEditor.callback(if (obj == null) null else obj.getID())
        }
        efp = null
        ufp = null
        sup = null
        supEditor = null
    }

    override fun resized(x: Int, y: Int) {
        setSize(x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(lhp, x, y, 50, 100, 100, 50)
        Page.Companion.set(fhp, x, y, 150, 100, 200, 50)
        Page.Companion.set(lhb, x, y, 50, 150, 100, 50)
        Page.Companion.set(fhb, x, y, 150, 150, 200, 50)
        Page.Companion.set(lsp, x, y, 50, 200, 100, 50)
        Page.Companion.set(fsp, x, y, 150, 200, 200, 50)
        Page.Companion.set(lsh, x, y, 50, 250, 100, 50)
        Page.Companion.set(fsh, x, y, 150, 250, 200, 50)
        Page.Companion.set(lwd, x, y, 50, 300, 100, 50)
        Page.Companion.set(fwd, x, y, 150, 300, 200, 50)
        Page.Companion.set(jspm, x, y, 0, 450, 350, 800)
        mpt.componentResized(x, y)
        Page.Companion.set(jspi, x, y, 550, 50, 300, 350)
        Page.Companion.set(add, x, y, 550, 400, 150, 50)
        Page.Companion.set(rem, x, y, 700, 400, 150, 50)
        Page.Companion.set(copy, x, y, 550, 450, 150, 50)
        Page.Companion.set(link, x, y, 700, 450, 150, 50)
        Page.Companion.set(comm, x, y, 550, 500, 300, 50)
        Page.Companion.set(atkn, x, y, 550, 550, 300, 50)
        Page.Companion.set(lra, x, y, 550, 650, 100, 50)
        Page.Companion.set(fra, x, y, 650, 650, 200, 50)
        Page.Companion.set(ltb, x, y, 550, 700, 100, 50)
        Page.Companion.set(ftb, x, y, 650, 700, 200, 50)
        Page.Companion.set(lbs, x, y, 550, 750, 100, 50)
        Page.Companion.set(fbs, x, y, 650, 750, 200, 50)
        Page.Companion.set(ltp, x, y, 550, 800, 100, 50)
        Page.Companion.set(ftp, x, y, 650, 800, 200, 50)
        Page.Companion.set(lct, x, y, 550, 850, 100, 50)
        Page.Companion.set(fct, x, y, 650, 850, 200, 50)
        Page.Companion.set(aet, x, y, 900, 50, 1400, 1000)
        Page.Companion.set(lpst, x, y, 900, 1050, 200, 50)
        Page.Companion.set(vpst, x, y, 1100, 1050, 200, 50)
        Page.Companion.set(litv, x, y, 900, 1100, 200, 50)
        Page.Companion.set(vitv, x, y, 1100, 1100, 200, 50)
        Page.Companion.set(jcba, x, y, 900, 1150, 400, 50)
        Page.Companion.set(lrev, x, y, 1600, 1050, 200, 50)
        Page.Companion.set(vrev, x, y, 1800, 1050, 100, 50)
        Page.Companion.set(lres, x, y, 1600, 1100, 200, 50)
        Page.Companion.set(vres, x, y, 1800, 1100, 100, 50)
        Page.Companion.set(jcbs, x, y, 1600, 1150, 300, 50)
    }

    protected fun set(jl: JL) {
        jl.setHorizontalAlignment(SwingConstants.CENTER)
        add(jl)
    }

    protected fun set(jtf: JTF) {
        jtf.setEditable(editable)
        add(jtf)
        ljp.add(jtf)
        jtf.setLnr(Consumer<FocusEvent> { e: FocusEvent? ->
            input(jtf, jtf.getText().trim { it <= ' ' })
            setData(ce)
        })
    }

    protected open fun setData(data: CustomEntity) {
        changing = true
        fhp.setText("" + (ce.hp * getDef()) as Int)
        fhb.setText("" + ce.hb)
        fsp.setText("" + ce.speed)
        fra.setText("" + ce.range)
        fwd.setText("" + ce.width)
        fsh.setText("" + ce.shield)
        ftb.setText("" + ce.tba)
        fbs.setText("" + ce.base)
        vpst.setText("" + ce.getPost())
        vitv.setText("" + ce.getItv())
        ftp.setText("" + ce.touch)
        fct.setText("" + ce.loop)
        comm.setSelected(data.common)
        mpt.setData(ce.rep.proc)
        val raw: Array<IntArray> = ce.rawAtkData()
        var pre = 0
        var n: Int = ce.atks.size
        if (ce.rev != null) n++
        if (ce.res != null) n++
        val ints = arrayOfNulls<String>(n)
        for (i in ce.atks.indices) {
            ints[i] = i + 1 + " " + ce.atks.get(i).str
            pre += raw[i][1]
            if (pre >= ce.getAnimLen()) ints[i] += " (out of range)"
        }
        var ix: Int = ce.atks.size
        if (ce.rev != null) ints[ix++] = ce.rev.str
        if (ce.res != null) ints[ix++] = ce.res.str
        var ind: Int = jli.getSelectedIndex()
        jli.setListData(ints)
        if (ind < 0) ind = 0
        if (ind >= ints.size) ind = ints.size - 1
        setA(ind)
        jli.setSelectedIndex(ind)
        val ene: Animable<AnimU<*>, UType> = ce.getPack()
        if (editable) jcba.setSelectedItem(ene.anim)
        jcbs.setSelectedItem(PackData.Identifier.Companion.get<Soul>(ce.death))
        vrev.setText(if (ce.rev == null) "x" else Data.Companion.KB_TIME.get(Data.Companion.INT_HB) - ce.rev.pre.toString() + "f")
        val s: Soul = PackData.Identifier.Companion.get<Soul>(ce.death)
        vres.setText(if (ce.res == null) "x" else if (s == null) "-" else s.len(SoulType.DEF) - ce.res.pre.toString() + "f")
        changing = false
    }

    protected fun subListener(e: JBTN, u: JBTN, a: JBTN, o: Any?) {
        e.setLnr(Consumer { x: ActionEvent? -> changePanel(EnemyFindPage(getThis(), null).also { efp = it }) })
        u.setLnr(Consumer { x: ActionEvent? -> changePanel(UnitFindPage(getThis(), null).also { ufp = it }) })
        a.setLnr(Consumer { x: ActionEvent? -> if (editable) changePanel(DIYViewPage(getThis(), jcba.getSelectedItem() as AnimCE)) else if (o is Unit) changePanel(UnitViewPage(getThis(), o as Unit?)) else if (o is Enemy) changePanel(EnemyViewPage(getThis(), o as Enemy?)) })
        e.setEnabled(editable)
        u.setEnabled(editable)
    }

    private fun addListeners() {
        back.setLnr(Consumer { e: ActionEvent? -> changePanel(front) })
        comm.setLnr(Consumer { e: ActionEvent? ->
            ce.common = comm.isSelected()
            setData(ce)
        })
        jli.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(e: ListSelectionEvent?) {
                if (changing || jli.getValueIsAdjusting()) return
                changing = true
                if (jli.getSelectedIndex() == -1) jli.setSelectedIndex(0)
                setA(jli.getSelectedIndex())
                changing = false
            }
        })
        jli.list = object : ReorderListener<String?> {
            override fun reordered(ori: Int, fin: Int) {
                var fin = fin
                if (ori < ce.atks.size) {
                    if (fin >= ce.atks.size) fin = ce.atks.size - 1
                    val l: MutableList<AtkDataModel> = ArrayList<AtkDataModel>()
                    for (adm in ce.atks) l.add(adm)
                    l.add(fin, l.removeAt(ori))
                    ce.atks = l.toTypedArray()
                }
                setData(ce)
                changing = false
            }

            override fun reordering() {
                changing = true
            }
        }
        add.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            var ind: Int = jli.getSelectedIndex()
            if (ind >= ce.atks.size) ind = ce.atks.size - 1
            val datas: Array<AtkDataModel?> = arrayOfNulls<AtkDataModel>(n + 1)
            for (i in 0..ind) datas[i] = ce.atks.get(i)
            ind++
            datas[ind] = AtkDataModel(ce)
            for (i in ind until n) datas[i + 1] = ce.atks.get(i)
            ce.atks = datas
            setData(ce)
            jli.setSelectedIndex(ind)
            setA(ind)
            changing = false
        })
        rem.setLnr(Consumer { e: ActionEvent? -> remAtk(jli.getSelectedIndex()) })
        copy.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            val ind: Int = jli.getSelectedIndex()
            ce.atks = Arrays.copyOf(ce.atks, n + 1)
            ce.atks.get(n) = ce.atks.get(ind).clone()
            setData(ce)
            jli.setSelectedIndex(n)
            setA(n)
            changing = false
        })
        link.setLnr(Consumer { e: ActionEvent? ->
            changing = true
            val n: Int = ce.atks.size
            val ind: Int = jli.getSelectedIndex()
            ce.atks = Arrays.copyOf(ce.atks, n + 1)
            ce.atks.get(n) = ce.atks.get(ind)
            setData(ce)
            jli.setSelectedIndex(n)
            setA(n)
            changing = false
        })
        jcba.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing) return
                ce.getPack().anim = jcba.getSelectedItem() as AnimCE
                setData(ce)
            }
        })
        jcbs.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                if (changing) return
                ce.death = (jcbs.getSelectedItem() as Soul).getID()
                setData(ce)
            }
        })
    }

    private operator fun get(ind: Int): AtkDataModel {
        return if (ind < ce.atks.size) ce.atks.get(ind) else if (ind == ce.atks.size) if (ce.rev == null) ce.res else ce.rev else ce.res
    }

    private fun input(jtf: JTF, text: String) {
        var text = text
        if (jtf === atkn) {
            val adm: AtkDataModel = aet.adm
            if (adm == null || adm.str == text) return
            text = ce.getAvailable(text)
            adm.str = text
            if (text == "revenge") {
                remAtk(adm)
                ce.rev = adm
            }
            if (text == "resurrection") {
                remAtk(adm)
                ce.res = adm
            }
            return
        }
        if (text.length > 0) {
            var v: Int = CommonStatic.parseIntN(text)
            if (jtf === fhp) {
                v /= getDef().toInt()
                if (v <= 0) v = 1
                ce.hp = v
            }
            if (jtf === fhb) {
                if (v <= 0) v = 1
                if (v > ce.hp) v = ce.hp
                ce.hb = v
            }
            if (jtf === fsp) {
                if (v < 0) v = 0
                if (v > 150) v = 150
                ce.speed = v
            }
            if (jtf === fra) {
                if (v <= 0) v = 1
                ce.range = v
            }
            if (jtf === fwd) {
                if (v <= 0) v = 1
                ce.width = v
            }
            if (jtf === fsh) {
                if (v < 0) v = 0
                ce.shield = v
            }
            if (jtf === ftb) {
                if (v < 0) v = 0
                ce.tba = v
            }
            if (jtf === fbs) {
                if (v < 0) v = 0
                ce.base = v
            }
            if (jtf === ftp) {
                if (v < 1) v = 1
                ce.touch = v
            }
            if (jtf === fct) {
                if (v < -1) v = -1
                ce.loop = v
            }
            getInput(jtf, v)
        }
        setData(ce)
    }

    private fun remAtk(adm: AtkDataModel) {
        for (i in ce.atks.indices) if (ce.atks.get(i) === adm) remAtk(i)
    }

    private fun remAtk(ind: Int) {
        var ind = ind
        changing = true
        val n: Int = ce.atks.size
        if (ind >= n) {
            if (ind == n) if (ce.rev != null) ce.rev = null else ce.res = null else ce.res = null
        } else if (n > 1) {
            val datas: Array<AtkDataModel?> = arrayOfNulls<AtkDataModel>(n - 1)
            for (i in 0 until ind) datas[i] = ce.atks.get(i)
            for (i in ind + 1 until n) datas[i - 1] = ce.atks.get(i)
            ce.atks = datas
        }
        setData(ce)
        ind--
        if (ind < 0) ind = 0
        jli.setSelectedIndex(ind)
        setA(ind)
        changing = false
    }

    private fun setA(ind: Int) {
        val adm: AtkDataModel = get(ind)!!
        link.setEnabled(editable && ind < ce.atks.size)
        copy.setEnabled(editable && ind < ce.atks.size)
        atkn.setEnabled(editable && ind < ce.atks.size)
        atkn.setText(adm.str)
        aet.setData(adm, getAtk())
        rem.setEnabled(editable && (ce.atks.size > 1 || ind >= ce.atks.size))
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        Editors.setEditorSupplier(EditCtrl(isEnemy, this))
        pack = pac
        ce = e
        aet = AtkEditTable(this, edit, !isEnemy)
        mpt = MainProcTable(this, edit, !isEnemy)
        jspm = JScrollPane(mpt)
        editable = edit
        if (!editable) jli.setDragEnabled(false)
    }
}